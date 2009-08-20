package net.alexanderkiel.idea_haskell_plugin.parser.top;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulRule;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import net.alexanderkiel.idea_haskell_plugin.parser.helper.Skipping;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Skipping.skipUntil;
import static net.alexanderkiel.idea_haskell_plugin.parser.top.Import.importDeclarations;
import static net.alexanderkiel.idea_haskell_plugin.parser.top.TopDeclaration.topDeclarations;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Body {

    private Body() {
    }

    public static final GracefulRule body = new GracefulRule() {

        /**
         * body <- { impdecls? ;? topdecls? }
         *
         * @param builder the PSI builder to use
         * @return a result (please have a look into the {@link GracefulResult result documentation})
         */
        public GracefulResult apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (!parse(builder, OPEN_BRACE)) {
                marker.drop();
                return FAIL;
            }

            importDeclarations.apply(builder);

            parse(builder, SEMICOLON);

            GracefulResult topDeclsResult = topDeclarations.apply(builder);
            if (topDeclsResult == RECOVER) {
                skipUntil(builder, CLOSE_BRACE);
            }

            if (!parse(builder, CLOSE_BRACE)) {
                builder.error("'}' expected");
                marker.drop();
                return RECOVER;
            }

            marker.done(BODY);
            return MATCH;
        }
    };
}
