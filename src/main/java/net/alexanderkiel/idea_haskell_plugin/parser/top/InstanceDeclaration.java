package net.alexanderkiel.idea_haskell_plugin.parser.top;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier.typeVar;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Brackets.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Ops.arrowOp;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Context.contextWithOp;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Declaration.instanceDeclarations;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Type.gTypeCon;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Type.type;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class InstanceDeclaration {

    private InstanceDeclaration() {
    }

    static final GracefulRule instanceDeclaration = new GracefulRule() {

        /**
         * instance [ scontext => ] qtycls inst [ where idecls ]
         *
         * @param builder the PSI builder to use
         * @return a result (please have a look into the {@link GracefulResult result documentation})
         */
        public GracefulResult apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            // Return if we don't see an "instance" keyword here
            if (!parse(builder, INSTANCE_ID)) {
                marker.drop();
                return FAIL;
            }

            //TODO: Simple context is Haskell 98
            //simpleContextWithOp.apply(builder);

            //TODO: Context is used in Language.Haskell
            contextWithOp.apply(builder);

            //TODO: This is Haskell 98
            /*if (!parseQTypeClass(builder)) {
                builder.error("class name expected");
                marker.drop();
                return false;
            }

            if (!inst.apply(builder)) {
                builder.error("type expected");
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

                GracefulResult instDeclsResult = instanceDeclarations.apply(builder);
                if (instDeclsResult == FAIL) {
                    builder.error("declarations expected");
                    marker.drop();
                    return RECOVER;
                } else {
                    marker.done(INSTANCE_DECL);
                    return instDeclsResult;
                }
            }

            marker.done(INSTANCE_DECL);
            return MATCH;
        }
    };


    private static final Rule con = new SequenceRule(openPar, gTypeCon, new StarRule(typeVar), closePar);
    private static final Rule typeVarTuple = new TupleRule(typeVar);
    private static final Rule listTypeVar = new SequenceRule(openBracket, typeVar, closeBracket);
    private static final Rule functionTypeVar = new SequenceRule(openPar, typeVar, arrowOp, typeVar, closePar);

    /**
     * <pre>
     * inst <- gtycon
     *       / ( gtycon tyvar1 ... tyvarn )   ( n >= 0 )
     *       / ( tyvar1 , ... , tyvarn )      ( n >= 2 )
     *       / [ tyvar ]
     *       / ( tyvar1 -> tyvar2 )
     * </pre>
     */
    private static final Rule inst = new OrElseRule(INST
            , gTypeCon
            , con
            , typeVarTuple
            , listTypeVar
            , functionTypeVar
    );
}
