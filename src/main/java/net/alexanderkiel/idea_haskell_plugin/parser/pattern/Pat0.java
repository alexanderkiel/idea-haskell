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
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.LPat9.lPat9;
import net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Pat0 implements Rule {

    public static final Rule pat0 = new Pat0();

    private Pat0() {
    }

    /**
     * pat0 <- lpat9
     *
     * @param builder the PSI builder to use
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    public boolean apply(@NotNull PsiBuilder builder) {
        return lPat9.apply(builder);
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "pat0";
    }
}