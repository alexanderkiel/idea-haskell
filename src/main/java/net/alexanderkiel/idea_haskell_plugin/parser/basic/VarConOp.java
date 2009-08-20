package net.alexanderkiel.idea_haskell_plugin.parser.basic;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier.parseQConId;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier.parseQVarId;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Symbol.parseQConSym;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Symbol.parseQVarSym;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parseAll;
import net.alexanderkiel.idea_haskell_plugin.parser.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.Associativity.LEFT;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class VarConOp {

    /**
     * gcon -> () | [] | (,{,}) | qcon
     *
     * @param builder PSI builder to use
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    public static boolean parseGCon(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        //TODO: UNIT_TYPE as name ok?
        if (parseAll(builder, UNIT_TYPE, OPEN_PAR, CLOSE_PAR) ||
                parseAll(builder, LIST_CON, OPEN_BRACKET, CLOSE_BRACKET) ||
                parseQCon(builder)) {

            marker.done(G_CON);
            return true;
        } else {
            marker.drop();
            return false;
        }
    }

    public static boolean parseVar(@NotNull PsiBuilder builder) {
        return parse(builder, VAR, VAR_ID) || parseAll(builder, VAR, OPEN_PAR, VAR_SYM, CLOSE_PAR);
    }

    public static final Rule var = new Rule() {
        public boolean apply(@NotNull PsiBuilder builder) {
            return parseVar(builder);
        }
    };

    public static boolean parseQVar(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        if (parseQVarId(builder) ||
                parse(builder, OPEN_PAR) && parseQVarSym(builder) && parse(builder, CLOSE_PAR)) {
            marker.done(Q_VAR);
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
    }

    public static final Rule qVar = new Rule() {
        public boolean apply(@NotNull PsiBuilder builder) {
            return parseQVar(builder);
        }
    };

    public static boolean parseCon(@NotNull PsiBuilder builder) {
        return parse(builder, CON, CON_ID) || parseAll(builder, CON, OPEN_PAR, CON_SYM, CLOSE_PAR);
    }

    public static final Rule con = new Rule() {
        public boolean apply(@NotNull PsiBuilder builder) {
            return parseCon(builder);
        }
    };

    public static boolean parseQCon(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        if (parseQConId(builder) ||
                parse(builder, OPEN_PAR) && parseGConSym(builder) && parse(builder, CLOSE_PAR)) {
            marker.done(Q_CON);
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
    }
    
    public static final Rule qCon = new Rule() {
        public boolean apply(@NotNull PsiBuilder builder) {
            return parseQCon(builder);
        }
    };

    public static boolean parseVarOp(@NotNull PsiBuilder builder) {
        return parse(builder, VAR_OP, VAR_SYM) || parseAll(builder, VAR_OP, BACKQUOTE, VAR_ID, BACKQUOTE);
    }

    public static boolean parseVarOp(@NotNull PsiBuilder builder, int level) {
        //TODO: handle precedence level
        return level == 9 && parseVarOp(builder);
    }

    public static boolean parseVarOp(@NotNull PsiBuilder builder, @NotNull Associativity a, int level) {
        //TODO: handle associativity and precedence level
        return level == 9 && a == LEFT && parseVarOp(builder);
    }

    public static boolean parseQVarOp(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        if (parseQVarSym(builder) ||
                parse(builder, BACKQUOTE) && parseQVarId(builder) && parse(builder, BACKQUOTE)) {
            marker.done(Q_VAR_OP);
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
    }

    public static boolean parseConOp(@NotNull PsiBuilder builder) {
        return parse(builder, CON_OP, CON_SYM) || parseAll(builder, CON_OP, BACKQUOTE, CON_ID, BACKQUOTE);
    }

    public static boolean parseQConOp(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();
        if (parseGConSym(builder) || parse(builder, BACKQUOTE) && parseQConId(builder) && parse(builder, BACKQUOTE)) {
            marker.done(Q_CON_OP);
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
    }

    public static boolean parseQConOp(@NotNull PsiBuilder builder, int level) {
        //TODO: handle precedence level
        return level == 9 && parseQConOp(builder);
    }

    public static boolean parseQConOp(@NotNull PsiBuilder builder, @NotNull Associativity a, int level) {
        //TODO: handle associativity and precedence level
        return level == 9 && a == LEFT && parseQConOp(builder);
    }

    public static boolean parseOp(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (parseVarOp(builder) || parseConOp(builder)) {
            marker.done(OP);
            return true;
        } else {
            marker.drop();
            return false;
        }
    }
    
    public static final Rule op = new Rule() {
        public boolean apply(@NotNull PsiBuilder builder) {
            return parseOp(builder);
        }
    };

    public static boolean parseQOp(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (parseQVarOp(builder) || parseQConOp(builder)) {
            marker.done(Q_OP);
            return true;
        } else {
            marker.drop();
            return false;
        }
    }

    public static boolean parseQOp(@NotNull PsiBuilder builder, int level) {
        //TODO: handle precedence level
        return level == 9 && parseQOp(builder);
    }

    public static boolean parseQOp(@NotNull PsiBuilder builder, @NotNull Associativity a, int level) {
        //TODO: handle associativity and precedence level
        return level == 9 && a == LEFT && parseQOp(builder);
    }

    public static boolean parseGConSym(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (parse(builder, COLON_OP) || parseQConSym(builder)) {
            marker.done(G_CON_SYM);
            return true;
        } else {
            marker.drop();
            return false;
        }
    }
}
