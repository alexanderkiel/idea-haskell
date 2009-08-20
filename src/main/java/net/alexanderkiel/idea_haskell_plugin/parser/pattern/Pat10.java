package net.alexanderkiel.idea_haskell_plugin.parser.pattern;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.PAT10;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.parseGCon;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.APat.aPat;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Pat10 implements Rule {

    public static final Rule pat10 = new Pat10();

    private Pat10() {
    }

    /**
     * pat10 <- gcon apat1 ... apatn | apat (arity gcon = k, k >= 1)
     *
     * @param builder the PSI builder to use
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        PsiBuilder.Marker before = builder.mark();
        if (parseGCon(builder) && aPat.apply(builder)) {

            while (aPat.apply(builder)) {}

            before.drop();
            marker.done(PAT10);
            return true;
        } else {
            before.rollbackTo();
        }

        if (aPat.apply(builder)) {
            marker.done(PAT10);
            return true;
        }

        marker.rollbackTo();
        return false;
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "pat10";
    }
}
