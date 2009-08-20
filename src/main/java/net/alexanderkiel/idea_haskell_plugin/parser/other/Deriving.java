package net.alexanderkiel.idea_haskell_plugin.parser.other;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.BracketedSequenceRule;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier.parseQTypeClass;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Deriving {

    private Deriving() {
    }

    public static final Rule deriving = new Rule() {

        /**
         * deriving <- deriving (dclass | (dclass1 , ... , dclassn)) (n >= 0)
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (!parse(builder, DERIVING_ID)) {
                marker.drop();
                return false;
            }

            if (dClass.apply(builder)) {
                marker.done(DERIVING);
                return true;
            }

            if (dClasses.apply(builder)) {
                marker.done(DERIVING);
                return true;
            }

            marker.rollbackTo();
            return false;
        }
    };

    private static final Rule dClass = new Rule() {

        /**
         * dclass <- qtycls
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (parseQTypeClass(builder)) {
                marker.done(DERIVING_CLASS);
                return true;
            } else {
                marker.drop();
                return false;
            }
        }
    };

    private static final Rule dClasses = new BracketedSequenceRule(dClass, OPEN_PAR, CLOSE_PAR, COMMA, 0);
}
