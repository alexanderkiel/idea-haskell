package net.alexanderkiel.idea_haskell_plugin.parser.basic;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Qualified.parseQualified;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Symbol {

    public static boolean parseDotSym(@NotNull PsiBuilder builder) {
        return parse(builder, DOT_SYM, VAR_SYM, ".");
    }

    public static boolean parseAtSym(@NotNull PsiBuilder builder) {
        return parse(builder, AT_OP, AT_OP, "@");
    }

    public static boolean parseStrictSym(@NotNull PsiBuilder builder) {
        return parse(builder, STRICT_SYM, VAR_SYM, "!");
    }

    public static boolean parseQVarSym(@NotNull PsiBuilder builder) {
        return parseQualified(builder, Q_VAR_SYM, VAR_SYM);
    }

    public static boolean parseQConSym(@NotNull PsiBuilder builder) {
        return parseQualified(builder, Q_CON_SYM, CON_SYM);
    }
}
