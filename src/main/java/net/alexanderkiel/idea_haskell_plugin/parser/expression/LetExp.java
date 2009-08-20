package net.alexanderkiel.idea_haskell_plugin.parser.expression;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.*;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulRule;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.Exp.exp;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import net.alexanderkiel.idea_haskell_plugin.parser.helper.Skipping;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Skipping.skipUntil;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Declaration.declarations;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class LetExp {

    private LetExp() {
    }

    //---------------------------------------------------------------------------------------------
    // Public Rules
    //---------------------------------------------------------------------------------------------

    public static final GracefulRule letExp = new GracefulRule() {

        /**
         * let decls in exp
         *
         * @param builder the PSI builder to use
         * @return a result (please have a look into the {@link GracefulResult result documentation})
         */
        public GracefulResult apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (!parse(builder, LET_ID)) {
                marker.drop();
                return FAIL;
            }

            GracefulResult result = declarations.apply(builder);
            if (result == FAIL) {
                builder.error("'{' expected");
                skipUntil(builder, IN_ID);
            } else if (result == RECOVER) {
                skipUntil(builder, IN_ID);
            }

            if (!parse(builder, IN_ID)) {
                builder.error("'in' expected");
                marker.drop();
                return RECOVER;
            }

            if (!exp.apply(builder)) {
                builder.error("expression expected");
                marker.drop();
                return RECOVER;
            }

            marker.done(LET_EXP);
            return MATCH;
        }
    };
}
