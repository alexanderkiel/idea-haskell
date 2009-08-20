package net.alexanderkiel.idea_haskell_plugin.parser.pattern;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Literal.parseLiteral;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Symbol.parseAtSym;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.FPat.fPat;
import static net.alexanderkiel.idea_haskell_plugin.parser.pattern.Pat.pat;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class APat {

    private APat() {
    }

    public final static Rule aPat = new Rule() {

        /**
         * <pre>
         * apat -> var [@apat]                  (as pattern)
         *      | gcon                          (arity gcon = 0)
         *      | qcon { fpat1, ... , fpatk }   (labeled pattern, k >= 0)
         *      | literal
         *      | _                             (wildcard)
         *      | ( pat )                       (parenthesized pattern)
         *      | ( pat1 , ... , patk )         (tuple pattern, k >= 2)
         *      | [ pat1 , ... , patk ]         (list pattern, k >= 1)
         *      | ~ apat                        (irrefutable pattern)
         * </pre>
         *
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            // var [@apat]
            if (parseVar(builder)) {

                PsiBuilder.Marker beforeOp = builder.mark();
                if (parseAtSym(builder) && aPat.apply(builder)) {
                    beforeOp.drop();
                } else {
                    beforeOp.rollbackTo();
                }

                marker.done(A_PAT);
                return true;
            }

            // qcon { fpat1, ... , fpatk }
            PsiBuilder.Marker beforeQCon = builder.mark();
            if (parseQCon(builder) && fPatSequence.apply(builder)) {
                beforeQCon.drop();
                marker.done(A_PAT);
                return true;
            } else {
                beforeQCon.rollbackTo();
            }

            // gcon
            if (parseGCon(builder)) {
                marker.done(A_PAT);
                return true;
            }

            // literal
            if (parseLiteral(builder)) {
                marker.done(A_PAT);
                return true;
            }

            // _
            if (parse(builder, WILDCARD_ID)) {
                marker.done(A_PAT);
                return true;
            }

            // ( pat )
            if (patParenthesized.apply(builder)) {
                marker.done(A_PAT);
                return true;
            }

            // ( pat1 , ... , patk )
            if (patTuple.apply(builder)) {
                marker.done(A_PAT);
                return true;
            }

            // [ pat1 , ... , patk ]
            if (patList.apply(builder)) {
                marker.done(A_PAT);
                return true;
            }

            // ~ apat
            if (parse(builder, TILDE_OP) && aPat.apply(builder)) {
                marker.done(A_PAT);
                return true;
            }

            marker.rollbackTo();
            return false;
        }

        @Override
        public String toString() {
            return "apat";
        }
    };

    public static final Rule aPatPlus = new PlusRule(aPat);

    private final static BracketedSequenceRule fPatSequence = new BracketedSequenceRule(fPat, OPEN_BRACE,
            CLOSE_BRACE, COMMA, 0);
    private final static ParenthesizedRule patParenthesized = new ParenthesizedRule(pat);
    private final static TupleRule patTuple = new TupleRule(pat);
    private final static ListRule patList = new ListRule(pat);
}
