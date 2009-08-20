package net.alexanderkiel.idea_haskell_plugin.parser.pattern;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.EQUAL_OP;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.F_PAT;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.parseQVar;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.Pat.pat;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class FPat implements Rule {

    public final static Rule fPat = new FPat();

    private FPat() {
    }

    /**
     * fpat -> qvar = pat
     *
     * @param builder the PSI builder to use
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (parseQVar(builder) && parse(builder, EQUAL_OP) && pat.apply(builder)) {
            marker.done(F_PAT);
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "fpat";
    }
}
