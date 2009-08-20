package net.alexanderkiel.idea_haskell_plugin.parser.expression;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.*;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulRule;
import net.alexanderkiel.idea_haskell_plugin.parser.PlusRule;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.AExp.aExp;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.DoExp.doExp;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.LetExp.letExp;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.CaseExp.caseExp;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.IfExp.ifExp;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Declaration.declarations;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.APat.aPatPlus;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Exp10 implements GracefulRule {

    public final static GracefulRule exp10 = new Exp10();

    private Exp10() {
    }

    /**
     * <pre>
     * exp(10) <- \apat1 ... apatn -> exp      (lambda abstraction, n >= 1)
     *          / let decls in exp             (let expression)
     *          / if exp than exp else exp     (conditional)
     *          / case exp of { alts }         (case expression)
     *          / do { stmts }
     *          / fexp
     * </pre>
     *
     * @return a result (please have a look into the {@link GracefulResult result documentation})
     */
    public GracefulResult apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        // \apat1 ... apatn -> exp
        if (parse(builder, BACKSLASH_OP)) {

            if (!aPatPlus.apply(builder)) {
                builder.error("pattern expected");
                marker.done(EXP10);
                return RECOVER;
            }

            if (!parse(builder, ARROW_OP)) {
                builder.error("'->' expected");
                marker.done(EXP10);
                return RECOVER;
            }

            if (!Exp.exp.apply(builder)) {
                builder.error("expression expected");
                marker.done(EXP10);
                return RECOVER;
            }

            marker.done(EXP10);
            return MATCH;
        }

        // let decls in exp
        GracefulResult letResult = letExp.apply(builder);
        if (letResult != FAIL) {
            marker.done(EXP10);
            return letResult;
        }
        
        // if exp than exp else exp
        GracefulResult ifResult = ifExp.apply(builder);
        if (ifResult != FAIL) {
            marker.done(EXP10);
            return ifResult;
        }

        // case exp of { alts }
        GracefulResult caseResult = caseExp.apply(builder);
        if (caseResult != FAIL) {
            marker.done(EXP10);
            return caseResult;
        }

        // TODO: I put the unary minus here like in Language.Haskell.
        if (unaryMinus.apply(builder)) {
            marker.done(EXP10);
            return MATCH;
        }

        // do { stmts }
        GracefulResult doResult = doExp.apply(builder);
        if (doResult != FAIL) {
            marker.done(EXP10);
            return doResult;
        }

        // fexp
        if (fExp.apply(builder)) {
            marker.done(EXP10);
            return MATCH;
        }

        marker.rollbackTo();
        return FAIL;
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "exp10";
    }

    private static final Rule unaryMinus = new Rule() {

        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (parse(builder, NEGATE_OP, VAR_SYM, "-") && fExp.apply(builder)) {
                marker.done(NEGATE_EXP);
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }
    };

    /**
     * fexp <- aexp+
     */
    private static final Rule fExp = new PlusRule(FUNCTION_EXP, aExp);
}
