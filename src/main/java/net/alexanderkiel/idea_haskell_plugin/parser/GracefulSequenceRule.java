package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Tries to apply the given rules in the given sequence.
 * <p/>
 * If one rule {@link GracefulResult#FAIL fails} this rule fails itself. If one rule requests to {@link
 * GracefulResult#RECOVER recover} this rule requests itself a recovery. If all rules {@link GracefulResult#MATCH match}
 * this rules matches itself.
 *
 * @author Alexander Kiel
 * @version $Id$
 */
public class GracefulSequenceRule implements GracefulRule {

    @Nullable
    private final IElementType resultType;

    @NotNull
    private final GracefulRule[] rules;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public GracefulSequenceRule(@NotNull GracefulRule... rules) {
        this.resultType = null;
        this.rules = rules;
    }

    public GracefulSequenceRule(@NotNull IElementType resultType, @NotNull GracefulRule... rules) {
        this.resultType = resultType;
        this.rules = rules;
    }

    public GracefulResult apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        for (GracefulRule rule : rules) {
            GracefulResult result = rule.apply(builder);
            if (result == FAIL) {
                marker.rollbackTo();
                return FAIL;
            } else if (result == RECOVER) {
                marker.drop();
                return RECOVER;
            }
        }

        if (resultType != null) {
            marker.done(resultType);
        } else {
            marker.drop();
        }
        return MATCH;
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        for (int i = 0; i < rules.length; i++) {
            if (i != 0) {
                sb.append(" ");
            }
            sb.append(rules[i]);
        }
        sb.append(')');
        return sb.toString();
    }
}