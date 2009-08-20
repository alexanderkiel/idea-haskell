package net.alexanderkiel.idea_haskell_plugin.parser.other;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.Associativity.LEFT;
import static net.alexanderkiel.idea_haskell_plugin.parser.EmptyRule.empty;
import net.alexanderkiel.idea_haskell_plugin.parser.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.BracketPair.BRACES;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.parseVarOp;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.var;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.Exp.exp;
import static net.alexanderkiel.idea_haskell_plugin.parser.guard.Guard.guardRhs;
import net.alexanderkiel.idea_haskell_plugin.parser.helper.ParseRule;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Context.contextWithOp;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Ops.ops;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Type.type;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Vars.vars;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.APat.aPatPlus;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.LPat9.lPat9;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.Pat0.pat0;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.Pat10.pat10;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Declaration {

    private Declaration() {
    }

    //---------------------------------------------------------------------------------------------
    // Public Rules
    //---------------------------------------------------------------------------------------------

    /**
     * decls <- { decl1 ; ... ; decln } ( n >= 0 )
     */
    public static final GracefulRule declarations;

    /**
     * decl <- binding / gendecl
     */
    public static final GracefulRule declaration;

    /**
     * cdecls <- { cdecl1 ; ... ; cdecln } ( n >= 0 )
     */
    public static final GracefulRule classDeclarations;

    /**
     * cdecl <- ( funlhs | var ) rhs / gendecl
     */
    public static final GracefulRule classDeclaration;

    /**
     * idecls <- { idecl1 ; ... ; idecln } ( n >= 0 )
     */
    public static final GracefulRule instanceDeclarations;

    /**
     * idecl <- ( funlhs | var ) rhs / empty
     */
    public static final GracefulRule instanceDeclaration;

    /**
     * [where decls]
     */
    public static final GracefulRule whereBindings;

    //---------------------------------------------------------------------------------------------
    // Private Rules
    //---------------------------------------------------------------------------------------------

    /**
     * binding <- ( funlhs | pat(0) ) rhs
     */
    private static final GracefulRule binding = new GracefulRule() {

        /**
         * decl <- ( funlhs | pat(0) ) rhs / gendecl
         *
         * @param builder the PSI builder to use
         * @return a result (please have a look into the {@link GracefulResult result documentation})
         */
        public GracefulResult apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (funlhs.apply(builder) || pat0.apply(builder)) {

                GracefulResult result = rhs.apply(builder);
                switch (result) {
                    case MATCH:
                        marker.done(BINDING);
                        return MATCH;
                    case FAIL:
                        marker.rollbackTo();
                        return FAIL;
                    case RECOVER:
                        marker.drop();
                        return RECOVER;
                    default:
                        throw new IllegalStateException("unknown GracefulResult: " + result);
                }
            } else {
                marker.rollbackTo();
                return FAIL;
            }
        }

        @Override
        public String toString() {
            return "binding";
        }
    };

    /**
     * simplebinding <- ( funlhs | var ) rhs
     */
    private static final GracefulRule simpleBinding = new GracefulRule() {

        /**
         * ( funlhs | var ) rhs
         *
         * @param builder the PSI builder to use
         * @return a result (please have a look into the {@link GracefulResult result documentation})
         */
        public GracefulResult apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (funlhs.apply(builder) || var.apply(builder)) {

                GracefulResult result = rhs.apply(builder);
                switch (result) {
                    case MATCH:
                        marker.done(S_BINDING);
                        return MATCH;
                    case FAIL:
                        marker.rollbackTo();
                        return FAIL;
                    case RECOVER:
                        marker.drop();
                        return RECOVER;
                    default:
                        throw new IllegalStateException("unknown GracefulResult: " + result);
                }
            } else {
                marker.rollbackTo();
                return FAIL;
            }
        }

        @Override
        public String toString() {
            return "sbinding";
        }
    };

    /**
     * funlhs <- var apat+ / pat(i+1)
     */
    private static final Rule funlhs;

    private static final Rule varAPatPlus = new Rule() {

        /**
         * var apat+
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (var.apply(builder) && aPatPlus.apply(builder)) {
                marker.drop();
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }

        @Override
        public String toString() {
            return "var apat+";
        }
    };

    private static final Rule patOpPat = new Rule() {

        /**
         * pat10 varop9 pat10
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (pat10.apply(builder) && parseVarOp(builder, 9) && pat10.apply(builder)) {
                marker.drop();
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }

        @Override
        public String toString() {
            return "pat op pat";
        }
    };

    private static final Rule lPatOpPat = new Rule() {

        /**
         * pat10 varop9 pat10
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (lPat9.apply(builder) && parseVarOp(builder, LEFT, 9) && pat10.apply(builder)) {
                marker.drop();
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }

        @Override
        public String toString() {
            return "lpat op pat";
        }
    };

    private static final Rule funlhsAPatPlus = new Rule() {

        /**
         * pat10 varop9 pat10
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (parse(builder, OPEN_PAR) && funlhs.apply(builder) && parse(builder, CLOSE_PAR) &&
                    aPatPlus.apply(builder)) {
                marker.drop();
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }

        @Override
        public String toString() {
            return "( funlhs ) apat+";
        }
    };

    static {
        funlhs = new OrElseRule(FUN_LHS, varAPatPlus, patOpPat, lPatOpPat, funlhsAPatPlus);
    }

    private static final Rule typeSignature = new Rule() {

        /**
         * typesig <- vars :: [context=>] type
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (vars.apply(builder) && parse(builder, DOUBLE_COLON_OP)) {

                contextWithOp.apply(builder);

                if (type.apply(builder)) {
                    marker.done(TYPE_SIG);
                    return true;
                }
            }

            marker.rollbackTo();
            return false;
        }

        @Override
        public String toString() {
            return "typesig";
        }
    };

    private static final Rule fixityDeclaration = new Rule() {

        /**
         * fixitydecl <- fixity [integer] ops
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (parse(builder, INFIXL_ID) || parse(builder, INFIXR_ID) || parse(builder, INFIX_ID)) {
                marker.done(FIXITY);
                marker = marker.precede();
            } else {
                marker.rollbackTo();
                return false;
            }

            parse(builder, INTEGER);

            if (ops.apply(builder)) {
                marker.done(FIXITY_DECL);
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }

        @Override
        public String toString() {
            return "fixitydecl";
        }
    };

    /**
     * gendecl <- typesig / fixitydecl / empty
     */
    private static final GracefulRule genDeclaration = new GracefulAdapter(new OrElseRule(GEN_DECL
            , typeSignature
            , fixityDeclaration
            , empty
    ));

    static {
        declaration = new GracefulOrElseRule(DECL, binding, genDeclaration);
        declarations = new GracefulBracketedSequenceRule(DECLS, declaration, BRACES, SEMICOLON, 0);

        classDeclaration = new GracefulOrElseRule(C_DECL, simpleBinding, genDeclaration);
        classDeclarations = new GracefulBracketedSequenceRule(C_DECLS, classDeclaration, BRACES, SEMICOLON, 0);

        instanceDeclaration = new GracefulOrElseRule(I_DECL, simpleBinding, new GracefulAdapter(empty));
        instanceDeclarations = new GracefulBracketedSequenceRule(I_DECLS, instanceDeclaration, BRACES, SEMICOLON, 0);

        whereBindings = new GracefulOptionRule(new GracefulSequenceRule(
                new GracefulAdapter(new ParseRule(WHERE_ID)), new GracefulDecorator(declarations, "declarations expected")
        ));
    }

    /**
     * rhs <- = exp [where decls] | gdrhs [where decls]
     */
    private static final GracefulRule rhs = new GracefulOrElseRule(RHS
            , new GracefulSequenceRule(new GracefulAdapter(new ParseRule(EQUAL_OP))
                    , new GracefulDecorator(new GracefulAdapter(exp), "expression expected")
                    , whereBindings)
            , new GracefulSequenceRule(guardRhs, whereBindings)
    );
}
