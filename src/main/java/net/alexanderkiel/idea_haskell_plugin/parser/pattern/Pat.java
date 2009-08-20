package net.alexanderkiel.idea_haskell_plugin.parser.pattern;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.Pat0.pat0;
import net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.parseVar;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Pat implements Rule {

    public static final Rule pat = new Pat();

    private Pat() {
    }

    /**
     * pat <- var + integer / pat(0)
     *
     * @param builder the PSI builder tp use
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        PsiBuilder.Marker before = builder.mark();
        if (parseVar(builder) && parse(builder, VAR_SYM, VAR_SYM, "+") && parse(builder, INTEGER)) {
            before.drop();
            marker.done(PAT);
            return true;
        } else {
            before.rollbackTo();
        }

        if (pat0.apply(builder)) {
            marker.done(PAT);
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
        return "pat";
    }
}
