package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.FAIL;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class GracefulOrElseRule extends AbstractGracefulRule {

    private final GracefulRule firstRule;
    private final GracefulRule secondRule;
    private final GracefulRule[] otherRules;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public GracefulOrElseRule(@NotNull GracefulRule firstRule, @NotNull GracefulRule secondRule,
                              @NotNull GracefulRule... otherRules) {
        super(null);
        this.firstRule = firstRule;
        this.secondRule = secondRule;
        this.otherRules = otherRules;
    }

    public GracefulOrElseRule(@NotNull IElementType resultType, @NotNull GracefulRule firstRule,
                              @NotNull GracefulRule secondRule, @NotNull GracefulRule... otherRules) {
        super(resultType);
        this.firstRule = firstRule;
        this.secondRule = secondRule;
        this.otherRules = otherRules;
    }

    public GracefulResult apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        GracefulResult result = firstRule.apply(builder);
        if (result != FAIL) {
            finishMarker(marker);
            return result;
        }

        result = secondRule.apply(builder);
        if (result != FAIL) {
            finishMarker(marker);
            return result;
        }

        if (otherRules != null) {
            for (GracefulRule rule : otherRules) {
                result = rule.apply(builder);
                if (result != FAIL) {
                    finishMarker(marker);
                    return result;
                }
            }
        }

        marker.drop();
        return FAIL;
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(firstRule);
        sb.append(" / ");
        sb.append(secondRule);
        for (GracefulRule rule : otherRules) {
            sb.append(" / ");
            sb.append(rule);
        }
        sb.append(')');        
        return sb.toString();
    }
}