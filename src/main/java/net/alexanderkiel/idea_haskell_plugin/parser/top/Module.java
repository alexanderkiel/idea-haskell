package net.alexanderkiel.idea_haskell_plugin.parser.top;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import net.alexanderkiel.idea_haskell_plugin.parser.helper.Skipping;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Skipping.skipUntil;
import net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier.parseModId;
import static net.alexanderkiel.idea_haskell_plugin.parser.top.Body.body;
import static net.alexanderkiel.idea_haskell_plugin.parser.top.Export.exports;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.*;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Module {

    private Module() {
    }

    public static void parseModule(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (!parse(builder, MODULE_ID)) {
            builder.error("'module' expected");
            marker.drop();
            return;
        }

        if (!parseModId(builder)) {
            builder.error("module name expected");
            skipUntil(builder, OPEN_PAR, WHERE_ID);
        }

        GracefulResult result = exports.apply(builder);
        if (result == RECOVER) {
            skipUntil(builder, WHERE_ID);
        }

        if (!parse(builder, WHERE_ID)) {
            builder.error("'where' expected");
            skipUntil(builder, OPEN_BRACE);
        }

        if (body.apply(builder) == FAIL) {
            marker.rollbackTo();
            return;
        }

        marker.done(MODULE);
    }
}
