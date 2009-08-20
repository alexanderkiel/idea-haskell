package net.alexanderkiel.idea_haskell_plugin.parser.expression;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.LEXP9;
import static net.alexanderkiel.idea_haskell_plugin.parser.Associativity.LEFT;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.FAIL;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.parseQOp;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.Exp10.exp10;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class LExp9 implements Rule {

    public static final Rule lExp9 = new LExp9();

    private LExp9() {
    }

    /**
     * lexp9 <- exp10 ( qconop(l,9) exp10 )*
     *
     * @param builder the PSI builder to use
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (exp10.apply(builder) == FAIL) {
            marker.drop();
            return false;
        }

        while (parseQOp(builder, LEFT, 9)) {
            if (exp10.apply(builder) != FAIL) {
                marker.done(LEXP9);
                marker = marker.precede();
            } else {
                marker.rollbackTo();
                return false;
            }
        }

        marker.drop();
        return true;
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "lexp9";
    }
}