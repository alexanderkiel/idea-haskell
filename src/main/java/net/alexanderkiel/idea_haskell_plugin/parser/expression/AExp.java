package net.alexanderkiel.idea_haskell_plugin.parser.expression;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.Associativity.LEFT;
import net.alexanderkiel.idea_haskell_plugin.parser.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.BracketPair.BRACES;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Literal.parseLiteral;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.Exp.exp;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.Exp10.exp10;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.LExp9.lExp9;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Declaration.declarations;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.Pat.pat;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class AExp {

    private AExp() {
    }

    public static final Rule aExp = new Rule() {

        /**
         * Tries to apply this rule to the token stream.
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            // aexp1 { fbind1 , ... , fbindn }        (labeled construction or update)
            if (labeledConstructionOrUpdate.apply(builder) == MATCH) {
                marker.done(A_EXP);
                return true;
            } else if (aExp1.apply(builder)) {
                marker.done(A_EXP);
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }
    };

    private static final Rule aExp1 = new Rule() {

        /**
         * <pre>
         * aexp -> qvar                                  (variable)
         *       / literal
         *       | ( exp )                               (parenthesized expression)
         *       | ( exp1 , ... , expk )                 (tuple, k >= 2)
         *       | [ exp1 , ... , expk ]                 (list, k >= 1)
         *       | [ exp1 [, exp2] .. [exp3] ]           (arithmetic sequence)
         *       | [ exp | qual1, ... , qualn ]          (list comprehension, n >= 1)
         *       | ( exp(i+1) qop(a,i) )                 (left section)
         *       | ( lexp(i) qop(l,i) )                  (left section)
         *       | ( qop(a,i)<-> exp(i+1) )              (right section)
         *       | ( qop(r,i)<-> rexp(i) )               (right section)
         *       | qcon { fbind1 , ... , fbindn }        (labeled construction, n >= 0)
         *       / gcon                                  (general constructor)
         *       | aexp<qcon> { fbind1 , ... , fbindn }  (labeled update, n >= 1)
         * </pre>
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            // qvar
            if (parseQVar(builder)) {
                marker.drop();
                return true;
            }

            // literal
            if (parseLiteral(builder)) {
                marker.drop();
                return true;
            }

            // ( exp )
            if (expParenthesized.apply(builder)) {
                marker.drop();
                return true;
            }

            // ( exp1 , ... , expk )
            if (expTuple.apply(builder)) {
                marker.drop();
                return true;
            }

            // [ exp1 , ... , expk ]
            if (expList.apply(builder)) {
                marker.drop();
                return true;
            }

            // [ exp | qual1, ... , qualn ]          (list comprehension, n >= 1)
            if (listComprehension.apply(builder)) {
                marker.drop();
                return true;
            }

            // [ exp1 [, exp2] .. [exp3] ]           (arithmetic sequence)
            if (arithmeticSequence.apply(builder)) {
                marker.drop();
                return true;
            }

            // ( exp(i+1) qop(a,i) )                 (left section)
            // ( lexp(i) qop(l,i) )                  (left section)
            if (leftSection.apply(builder)) {
                marker.drop();
                return true;
            }

            // ( qop(a,i)<-> exp(i+1) )              (right section)
            // ( qop(r,i)<-> rexp(i) )               (right section)
            if (rightSection.apply(builder)) {
                marker.drop();
                return true;
            }

            // gcon
            if (parseGCon(builder)) {
                marker.drop();
                return true;
            }

            marker.rollbackTo();
            return false;
        }
    };

    private static final ParenthesizedRule expParenthesized = new ParenthesizedRule(exp);
    private static final TupleRule expTuple = new TupleRule(exp);
    private static final ListRule expList = new ListRule(exp);

    private static final Rule listComprehension = new Rule() {

        /**
         * [ exp | qual1, ... , qualn ]          (list comprehension, n >= 1)
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (!parse(builder, OPEN_BRACKET)) {
                marker.drop();
                return false;
            }

            if (!exp.apply(builder)) {
                marker.rollbackTo();
                return false;
            }

            if (!parse(builder, BAR_OP)) {
                marker.rollbackTo();
                return false;
            }

            if (!qualifiers.apply(builder)) {
                marker.rollbackTo();
                return false;
            }

            if (!parse(builder, CLOSE_BRACKET)) {
                marker.rollbackTo();
                return false;
            }

            marker.drop();
            return true;
        }
    };

    private static final Rule generator = new Rule() {

        /**
         * genstmt <- pat <- exp
         *
         * @param builder the PSI builder to use
         * @return always {@code true}
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (pat.apply(builder) && parse(builder, GENERATOR_OP) && exp.apply(builder)) {
                marker.drop();
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }
    };

    private static final Rule localDeclaration = new Rule() {

        /**
         * letstmt <- let decls
         *
         * @param builder the PSI builder to use
         * @return always {@code true}
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (parse(builder, LET_ID) && declarations.apply(builder) == MATCH) {
                marker.drop();
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }
    };

    private static final Rule qualifier = new OrElseRule(QUAL, generator, localDeclaration, exp);

    private static final Rule qualifiers = new SeparatedSequenceRule(qualifier, COMMA, 1);

    private static final Rule arithmeticSequence = new Rule() {

        /**
         * [ exp1 [, exp2] .. [exp3] ]           (arithmetic sequence)
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (!parse(builder, OPEN_BRACKET)) {
                marker.drop();
                return false;
            }

            if (!exp.apply(builder)) {
                marker.rollbackTo();
                return false;
            }

            if (parse(builder, COMMA) && !exp.apply(builder)) {
                marker.rollbackTo();
                return false;
            }

            if (!parse(builder, DOUBLE_DOT_OP)) {
                marker.rollbackTo();
                return false;
            }

            exp.apply(builder);

            if (!parse(builder, CLOSE_BRACKET)) {
                marker.rollbackTo();
                return false;
            }

            marker.drop();
            return true;
        }
    };

    private static final Rule leftSection1 = new Rule() {
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (lExp9.apply(builder) && parseQOp(builder, LEFT, 9)) {
                marker.drop();
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }
    };

    private static final Rule leftSection2 = new Rule() {
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (exp10.apply(builder) == MATCH && parseQOp(builder, 9)) {
                marker.drop();
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }
    };

    private static final Rule leftSection = new OrElseRule(LEFT_SECTION
            , new ParenthesizedRule(leftSection1)
            , new ParenthesizedRule(leftSection2)
    );

    private static final Rule rightSection = new ParenthesizedRule(RIGHT_SECTION, new Rule() {
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (!parse(builder, VAR_SYM, VAR_SYM, "-") && parseQOp(builder, 9) && exp10.apply(builder) == MATCH) {
                marker.drop();
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }
    });

    private static final GracefulRule fBind = new GracefulRule() {

        /**
         * Tries to apply this rule to the token stream.
         *
         * @param builder the PSI builder to use
         * @return a result (please have a look into the {@link GracefulResult result documentation})
         */
        public GracefulResult apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (!parseQVar(builder)) {
                marker.drop();
                return FAIL;
            }

            if (!parse(builder, EQUAL_OP)) {
                builder.error("'=' expected");
                marker.drop();
                return RECOVER;
            }

            if (!exp.apply(builder)) {
                builder.error("expression expected");
                marker.drop();
                return RECOVER;
            }

            marker.done(FBIND);
            return MATCH;
        }
    };

    private static final GracefulRule labeledConstructionOrUpdate = new GracefulSequenceRule(new GracefulAdapter(aExp1)
            , new GracefulBracketedSequenceRule(fBind, BRACES, COMMA, 0)
    );
}
