package net.alexanderkiel.idea_haskell_plugin.parser.expression;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulBracketedSequenceRule;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.*;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulRule;
import net.alexanderkiel.idea_haskell_plugin.parser.BracketPair;
import static net.alexanderkiel.idea_haskell_plugin.parser.BracketPair.BRACES;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.Exp.exp;
import static net.alexanderkiel.idea_haskell_plugin.parser.guard.Guard.guardPat;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Declaration.whereBindings;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.Pat.pat;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class CaseExp {

    private CaseExp() {
    }

    //---------------------------------------------------------------------------------------------
    // Public Rules
    //---------------------------------------------------------------------------------------------

    public static final GracefulRule caseExp = new GracefulRule() {

        /**
         * case exp of { alts }
         *
         * @param builder the PSI builder to use
         * @return a result (please have a look into the {@link net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult result documentation})
         */
        public GracefulResult apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (!parse(builder, CASE_ID)) {
                marker.drop();
                return FAIL;
            }

            if (!exp.apply(builder)) {
                builder.error("expression expected");
                marker.drop();
                return RECOVER;
            }

            if (!parse(builder, OF_ID)) {
                builder.error("'of' expected");
                marker.drop();
                return RECOVER;
            }

            GracefulResult result = alts.apply(builder);
            if (result == FAIL) {
                builder.error("alternatives expected");
                marker.drop();
                return RECOVER;
            } else if (result == RECOVER) {
                marker.drop();
                return RECOVER;
            }

            marker.done(CASE_EXP);
            return MATCH;
        }
    };

    //---------------------------------------------------------------------------------------------
    // Private Rules
    //---------------------------------------------------------------------------------------------

    private static final GracefulRule alt = new GracefulRule() {

        /**
         * alt <- pat -> exp (where decls)?
         *      / pat gdpat (where decls)?
         *      / empty
         *
         * @param builder the PSI builder to use
         * @return a result (please have a look into the {@link net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult result documentation})
         */
        public GracefulResult apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            // Leave if there is no pattern. But return MATCH because of the empty alternative.
            if (!pat.apply(builder)) {
                marker.drop();
                return MATCH;
            }

            if (parse(builder, ARROW_OP)) {

                if (!exp.apply(builder)) {
                    builder.error("expression expected");
                    marker.drop();
                    return RECOVER;
                }

                if (whereBindings.apply(builder) == RECOVER) {
                    marker.drop();
                    return RECOVER;
                }
            } else {

                GracefulResult result = guardPat.apply(builder);
                if (result == FAIL) {
                    builder.error("match expected");
                    marker.drop();
                    return RECOVER;
                } else if (result == RECOVER) {
                    marker.drop();
                    return RECOVER;
                }

                if (whereBindings.apply(builder) == RECOVER) {
                    marker.drop();
                    return RECOVER;
                }
            }

            marker.done(ALT);
            return MATCH;
        }
    };

    private static final GracefulRule alts = new GracefulBracketedSequenceRule(ALTS, alt, BRACES, SEMICOLON, 1);
}