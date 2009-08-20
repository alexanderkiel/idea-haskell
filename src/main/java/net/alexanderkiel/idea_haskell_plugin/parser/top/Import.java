package net.alexanderkiel.idea_haskell_plugin.parser.top;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import net.alexanderkiel.idea_haskell_plugin.parser.SeparatedSequenceRule;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier.parseModId;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier.parseTypeCon;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.parseVar;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Import {

    private Import() {
    }

    private static final Rule importDeclaration = new Rule() {

        /**
         * impdecl <- import qualified? modid (as modid)? impspec? / empty
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (!parse(builder, IMPORT_ID)) {
                marker.drop();
                return true;
            }

            parse(builder, QUALIFIED_ID, VAR_ID, "qualified");

            if (!parseModId(builder)) {
                builder.error("module identifier expected");
                marker.rollbackTo();
                return false;
            }

            if (parse(builder, AS_ID, VAR_ID, "as")) {
                if (!parseModId(builder)) {
                    builder.error("module identifier expected");
                    marker.rollbackTo();
                    return false;
                }
            }

            importSpec.apply(builder);

            marker.done(IMP_DECL);
            return true;
        }
    };

    public static final Rule importDeclarations = new SeparatedSequenceRule(IMP_DECLS, importDeclaration,
            SEMICOLON, 1);

    private static final Rule importSpec = new Rule() {

        /**
         * impspec <- hiding ( import1 , ... , importn [,] ) / ( import1 , ... , importn [,] )
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            parse(builder, HIDING_ID, VAR_ID, "hiding");

            if (!parse(builder, OPEN_PAR)) {
                marker.rollbackTo();
                return false;
            }

            while (importRule.apply(builder)) {
                if (!parse(builder, COMMA)) {
                    break;
                }
            }

            if (!parse(builder, CLOSE_PAR)) {
                marker.rollbackTo();
                return false;
            }

            marker.done(IMP_SPEC);
            return true;
        }
    };

    private static final Rule importRule = new Rule() {

        /**
         * import <- var / tycon / tycls
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            //TODO: how to distinguish between tycon and tycls?

            if (parseVar(builder)) {

            } else if (parseTypeCon(builder)) {

            } else {
                marker.rollbackTo();
                return false;
            }

            marker.done(IMPORT);
            return true;
        }
    };
}
