package net.alexanderkiel.idea_haskell_plugin.parser.expression;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Declaration.declarations;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.Exp.exp;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.Pat.pat;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class DoExp {

    private DoExp() {
    }

    //---------------------------------------------------------------------------------------------
    // Public Rules
    //---------------------------------------------------------------------------------------------

    public static final GracefulRule doExp = new GracefulRule() {

        /**
         * do { stmts }
         * stmts <- stmt1 ... stmtn exp [;] ( n >= 0 )
         *
         * @param builder the PSI builder to use
         * @return a result (please have a look into the {@link GracefulResult result documentation})
         */
        public GracefulResult apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker doMarker = builder.mark();

            // Return if we don't see a "do" keyword here
            if (!parse(builder, DO_ID)) {
                doMarker.drop();
                return FAIL;
            }

            // If we don't see an open brace here - we give up and let the outer context skip over bad tokens
            if (!parse(builder, OPEN_BRACE)) {
                builder.error("'{' expected");
                doMarker.drop();
                return RECOVER;
            }

            PsiBuilder.Marker stmtsMarker = builder.mark();

            boolean lastStatementWasAnExpression = false;
            while (!parse(builder, CLOSE_BRACE)) {
                PsiBuilder.Marker stmtMarker = builder.mark();

                // Generator statement
                if (generatorStatement.apply(builder)) {

                    if (!parse(builder, SEMICOLON)) {
                        builder.error("';' expected");
                    }

                    stmtMarker.done(STATEMENT);
                    lastStatementWasAnExpression = false;
                    continue;
                }

                // Let statement
                if (letStatement.apply(builder)) {

                    if (!parse(builder, SEMICOLON)) {
                        builder.error("';' expected");
                    }

                    stmtMarker.done(STATEMENT);
                    lastStatementWasAnExpression = false;
                    continue;
                }

                // Expression statement
                if (exp.apply(builder)) {
                    parse(builder, SEMICOLON);
                    stmtMarker.done(STATEMENT);
                    lastStatementWasAnExpression = true;
                    continue;
                }

                // Empty statement
                if (parse(builder, SEMICOLON)) {
                    stmtMarker.done(STATEMENT);
                    continue;
                }

                stmtMarker.drop();
                builder.error("statement expected");
                builder.advanceLexer();
            }

            if (lastStatementWasAnExpression) {
                stmtsMarker.done(STATEMENTS);
                doMarker.done(DO_EXP);
                return MATCH;
            } else {
                builder.error("last statement has to be an expression");
                stmtsMarker.done(STATEMENTS);
                doMarker.done(DO_EXP);
                return RECOVER;
            }
        }
    };


    //---------------------------------------------------------------------------------------------
    // Private Rules
    //---------------------------------------------------------------------------------------------

    private static final Rule generatorStatement = new Rule() {

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

    private static final Rule letStatement = new Rule() {

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
}