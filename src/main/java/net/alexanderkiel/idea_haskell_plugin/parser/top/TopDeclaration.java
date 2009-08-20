package net.alexanderkiel.idea_haskell_plugin.parser.top;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Constructor.constructors;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Constructor.newConstructor;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Context.contextWithOp;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Declaration.declaration;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Deriving.deriving;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Type.simpleType;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Type.type;
import static net.alexanderkiel.idea_haskell_plugin.parser.top.InstanceDeclaration.instanceDeclaration;
import static net.alexanderkiel.idea_haskell_plugin.parser.top.ClassDeclaration.classDeclaration;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class TopDeclaration {

    private TopDeclaration() {
    }

    private static final Rule typeDeclaration = new Rule() {

        /**
         * type simpletype = type
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (parse(builder, TYPE_ID) && simpleType.apply(builder) && parse(builder, EQUAL_OP) &&
                    type.apply(builder)) {
                marker.done(TYPE_DECL);
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }
    };

    private static final Rule dataDeclaration = new Rule() {

        /**
         * datadecl <- data [context =>] simpletype = constrs [deriving]
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (parse(builder, DATA_ID)) {

                contextWithOp.apply(builder);

                if (simpleType.apply(builder) && parse(builder, EQUAL_OP) && constructors.apply(builder)) {

                    deriving.apply(builder);

                    marker.done(DATA_DECL);
                    return true;
                }
            }

            marker.rollbackTo();
            return false;
        }
    };

    private static final Rule newTypeDeclaration = new Rule() {

        /**
         * newtype [context =>] simpletype = newconstr [deriving]
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (parse(builder, NEWTYPE_ID) && contextWithOp.apply(builder) && simpleType.apply(builder) &&
                    parse(builder, EQUAL_OP) && newConstructor.apply(builder)) {

                deriving.apply(builder);

                marker.done(NEWTYPE_DECL);
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }
    };

    private static final Rule defaultDeclaration = new Rule() {

        /**
         *
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (parse(builder, DEFAULT_ID)) {

                //TODO: implement
                throw new UnsupportedOperationException("TODO: implement");

            } else {
                marker.rollbackTo();
                return false;
            }
        }
    };

    /**
     * <pre>
     * topdecl -> type simpletype = type
     *          | data [context =>] simpletype = constrs [deriving]
     *          | newtype [context =>] simpletype = newconstr [deriving]
     *          | class [scontext =>] tycls tyvar [where cdecls]
     *          | instance [scontext =>] qtycls inst [where idecls]
     *          | default ( type1 , ... , typen )                           (n >= 0)
     *          | decl
     * </pre>
     */
    private static final GracefulRule topDeclaration = new GracefulOrElseRule(TOP_DECL
            , new GracefulAdapter(typeDeclaration)
            , new GracefulAdapter(dataDeclaration)
            , new GracefulAdapter(newTypeDeclaration)
            , classDeclaration
            , instanceDeclaration
            , new GracefulAdapter(defaultDeclaration)
            , declaration
    );

    /**
     * topdecls <- topdecl1 ; ... ; topdecln (n >= 0)
     */
    public static final GracefulRule topDeclarations = new GracefulSeparatedSequenceRule(TOP_DECLS
            , topDeclaration
            , SEMICOLON
            , 0
    );
}
