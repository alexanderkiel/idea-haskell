package net.alexanderkiel.idea_haskell_plugin.parser.basic;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Qualified.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Symbol.parseDotSym;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Identifier {

    public static boolean parseTypeVar(@NotNull PsiBuilder builder) {
        return parse(builder, TYPE_VAR, VAR_ID);
    }

    public static final Rule typeVar = new Rule() {
        public boolean apply(@NotNull PsiBuilder builder) {
            return parseTypeVar(builder);
        }
    };

    public static boolean parseTypeCon(@NotNull PsiBuilder builder) {
        return parse(builder, TYPE_CON, CON_ID);
    }

    public static boolean parseTypeClass(@NotNull PsiBuilder builder) {
        return parse(builder, TYPE_CLASS, CON_ID);
    }

    public static boolean parseTypeConOrClass(@NotNull PsiBuilder builder) {
        return parse(builder, TYPE_CON_OR_CLASS, CON_ID);
    }

    public static boolean parseModId(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker start = builder.mark();

        //noinspection StatementWithEmptyBody
        while (consumeConIdDotSymPair(builder)) ;

        if (parse(builder, CON_ID)) {
            start.done(MOD_ID);
            return true;
        } else {
            start.rollbackTo();
            return false;
        }
    }

    public static final Rule modId = new Rule() {
        public boolean apply(@NotNull PsiBuilder builder) {
            return parseModId(builder);
        }
    };

    public static boolean parseQVarId(@NotNull PsiBuilder builder) {
        return parseQualified(builder, Q_VAR_ID, VAR_ID);
    }

    public static boolean parseQConId(@NotNull PsiBuilder builder) {
        return parseQualified(builder, Q_CON_ID, CON_ID);
    }

    public static boolean parseQTypeCon(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker start = builder.mark();
        if (parseReferencingModId(builder) && parseDotSym(builder) && parseTypeCon(builder) ||
                parseTypeCon(builder)) {
            start.done(Q_TYPE_CON);
            return true;
        } else {
            start.rollbackTo();
            return false;
        }
    }

    public static boolean parseQTypeClass(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker start = builder.mark();
        if (parseReferencingModId(builder) && parseDotSym(builder) && parseTypeClass(builder) ||
                parseTypeClass(builder)) {
            start.done(Q_TYPE_CLASS);
            return true;
        } else {
            start.rollbackTo();
            return false;
        }
    }

    public static final Rule qTypeConOrClass = new Rule() {
        public boolean apply(@NotNull PsiBuilder builder) {
            PsiBuilder.Marker start = builder.mark();
            if (parseReferencingModId(builder) && parseDotSym(builder) && parseTypeConOrClass(builder) ||
                    parseTypeConOrClass(builder)) {
                start.done(Q_TYPE_CON_OR_CLASS);
                return true;
            } else {
                start.rollbackTo();
                return false;
            }
        }
    };
}
