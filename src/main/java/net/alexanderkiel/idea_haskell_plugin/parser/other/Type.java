package net.alexanderkiel.idea_haskell_plugin.parser.other;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parseAll;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Type {

    private Type() {
    }

    public static final Rule type = new Rule() {

        /**
         * type -> btype [-> type]
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (!bType.apply(builder)) {
                marker.drop();
                return false;
            }

            if (parse(builder, ARROW_OP)) {

                if (!type.apply(builder)) {
                    marker.rollbackTo();
                    return false;
                }

            }

            marker.done(TYPE);
            return true;
        }
    };

    public static final Rule simpleType = new Rule() {

        /**
         * simpletype <- tycon tyvar1 ... tyvark ( k >= 0 )
         *
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (!parseTypeCon(builder)) {
                marker.drop();
                return false;
            }

            while (parseTypeVar(builder)) {}

            marker.done(SIMPLE_TYPE);
            return true;
        }
    };

    public static final Rule gTypeCon = new Rule() {

        /**
         * gtycon <- qtycon | () | [] | (->) | (,{,})
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            //TODO: tuple constructor
            if (parseQTypeCon(builder) || parseAll(builder, UNIT_TYPE, OPEN_PAR, CLOSE_PAR) ||
                    parseAll(builder, LIST_CON, OPEN_BRACKET, CLOSE_BRACKET) ||
                    parseAll(builder, FUNCTION_CON, OPEN_PAR, ARROW_OP, CLOSE_PAR)) {
                marker.done(G_TYPE_CON);
                return true;
            } else {
                marker.drop();
                return false;
            }
        }
    };

    /**
     * atype <- gtycon / tyvar / (type) / ( type1 , ... , typek ) / [ type ] (k >= 2)
     */
    public static final Rule aType = new OrElseRule(A_TYPE
            , gTypeCon
            , typeVar
            , new ParenthesizedRule(type)
            , new TupleRule(type)
            , new ListRule(type)
    );

    /**
     * btype <- atype+
     */
    public static final Rule bType = new PlusRule(B_TYPE, aType);
}
