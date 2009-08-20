package net.alexanderkiel.idea_haskell_plugin.parser.other;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.BracketedSequenceRule;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import net.alexanderkiel.idea_haskell_plugin.parser.SeparatedSequenceRule;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Symbol.parseStrictSym;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.parseCon;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.parseVar;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Type.aType;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Type.type;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Vars.vars;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Constructor {

    private Constructor() {
    }

    public static final Rule constructor = new Rule() {

        /**
         * <pre>
         * constr -> con [!] atype1 ... [!] atypek                  ( k >= 0 )
         *         | ( btype | ! atype ) conop ( btype | ! atype )  ( infix conop )
         *         | con { fielddecl1 , ... , fielddecln }          ( n >= 0 )
         * <pre>
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            // con { fielddecl1 , ... , fielddecln } / con [!] atype1 ... [!] atypek
            if (parseCon(builder)) {

                if (fieldDeclarations.apply(builder)) {

                    // con { fielddecl1 , ... , fielddecln }
                    marker.done(CONSTRUCTOR);
                    return true;

                } else {

                    // con [!] atype1 ... [!] atypek
                    while (true) {
                        PsiBuilder.Marker before = builder.mark();
                        parseStrictSym(builder);
                        if (aType.apply(builder)) {
                            before.drop();
                        } else {
                            before.rollbackTo();
                            break;
                        }
                    }
                }
            }

            // TODO ( btype | ! atype ) conop ( btype | ! atype )

            marker.done(CONSTRUCTOR);
            return true;
        }
    };

    public static final Rule constructors = new SeparatedSequenceRule(constructor, BAR_OP, 1);

    public static final Rule newConstructor = new Rule() {

        /**
         * newconstr <- con { var :: type } / con atype
         *
         * @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            // con { var :: type } / con atype
            if (parseCon(builder)) {

                if (parse(builder, OPEN_BRACE) && parseVar(builder) && parse(builder, DOUBLE_COLON_OP) &&
                        type.apply(builder) && parse(builder, CLOSE_BRACE)) {

                    marker.done(NEW_CONSTRUCTOR);
                    return true;

                } else if (aType.apply(builder)) {

                    marker.done(NEW_CONSTRUCTOR);
                    return true;
                    
                }
            }

            marker.rollbackTo();
            return false;
        }
    };

    private static final Rule fieldDeclaration = new Rule() {

        /**
         * fielddecl -> vars :: ( type | ! atype )
         *
         *    @param builder the PSI builder to use
         * @return {@code true} if the rule could be parsed; {@code false} otherwise
         */
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker marker = builder.mark();

            if (vars.apply(builder) && parse(builder, DOUBLE_COLON_OP) && (type.apply(builder) ||
                    parseStrictSym(builder) && aType.apply(builder))) {
                marker.done(FIELD_DECLARATION);
                return true;
            } else {
                marker.rollbackTo();
                return false;
            }
        }
    };

    private static final Rule fieldDeclarations = new BracketedSequenceRule(fieldDeclaration, OPEN_BRACE,
            CLOSE_BRACE, COMMA, 0);
}
