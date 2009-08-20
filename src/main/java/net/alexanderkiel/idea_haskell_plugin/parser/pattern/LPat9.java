package net.alexanderkiel.idea_haskell_plugin.parser.pattern;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.PAT10;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.LPAT9;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.LEFT_EXP;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import net.alexanderkiel.idea_haskell_plugin.parser.Associativity;
import static net.alexanderkiel.idea_haskell_plugin.parser.Associativity.LEFT;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.parseGCon;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.parseQConOp;
import net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.APat.aPat;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.Pat10.pat10;
import net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class LPat9 implements Rule {

    public static final Rule lPat9 = new LPat9();

    private LPat9() {
    }

    /**
     * lpat9 <- pat10 ( qconop(l,9) pat10 )*
     *
     * @param builder the PSI builder to use
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (!pat10.apply(builder)) {
            marker.drop();
            return false;
        }

        while (parseQConOp(builder, LEFT, 9)) {
            if (pat10.apply(builder)) {
                marker.done(LPAT9);
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
        return "lpat9";
    }
}