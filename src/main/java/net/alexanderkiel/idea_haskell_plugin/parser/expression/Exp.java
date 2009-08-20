package net.alexanderkiel.idea_haskell_plugin.parser.expression;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.DOUBLE_COLON_OP;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.EXP;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.Exp0.exp0;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Context.contextWithOp;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Type.type;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Exp implements Rule {

    public static final Rule exp = new Exp();

    private Exp() {
    }

    /**
     * exp <- exp(0) ( :: [context =>] type )?
     *
     * @param builder the PSI builder to use
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (!exp0.apply(builder)) {
            marker.drop();
            return false;
        }

        PsiBuilder.Marker before = builder.mark();
        if (parse(builder, DOUBLE_COLON_OP)) {

            contextWithOp.apply(builder);

            if (type.apply(builder)) {
                before.drop();
            } else {
                before.rollbackTo();
            }
        } else {
            before.rollbackTo();    
        }

        marker.done(EXP);
        return true;
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "exp";
    }
}