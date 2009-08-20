package net.alexanderkiel.idea_haskell_plugin.parser.top;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulErrorRecovery;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulRule;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.FAIL;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.RECOVER;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.MATCH;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Context.contextWithOp;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Declaration.classDeclarations;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Type.type;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class ClassDeclaration {

    private ClassDeclaration() {
    }

    static final GracefulRule classDeclaration = new GracefulRule() {

        /**
         * class [ scontext => ] tycls tyvar [ where cdecls ]
         *
         * @param builder the PSI builder to use
         * @return a result (please have a look into the {@link GracefulResult result documentation})
         */
        public GracefulResult apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            // Return if we don't see an "class" keyword here
            if (!parse(builder, CLASS_ID)) {
                marker.drop();
                return FAIL;
            }

            //TODO: Simple context is Haskell 98
            //simpleContextWithOp.apply(builder);

            //TODO: Context is used in Language.Haskell
            contextWithOp.apply(builder);

            //TODO: This is Haskell 98
            /*if (!parseTypeClass(builder)) {
                builder.error("class name expected");
                marker.drop();
                return false;
            }

            if (!parseTypeVar(builder)) {
                builder.error("type variable expected");
                marker.drop();
                return false;
            }*/

            //TODO: Type is used in Language.Haskell
            if (!type.apply(builder)) {
                builder.error("type expected");
                marker.drop();
                return RECOVER;
            }

            if (parse(builder, WHERE_ID)) {

                GracefulResult classDeclsResult = classDeclarations.apply(builder);
                if (classDeclsResult == FAIL) {
                    builder.error("declarations expected");
                    marker.drop();
                    return RECOVER;
                } else {
                    marker.done(CLASS_DECL);
                    return classDeclsResult;
                }
            }

            marker.done(CLASS_DECL);
            return MATCH;
        }
    };
}
