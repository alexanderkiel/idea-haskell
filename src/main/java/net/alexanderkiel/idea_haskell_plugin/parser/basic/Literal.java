package net.alexanderkiel.idea_haskell_plugin.parser.basic;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.LITERAL;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.LITERALS;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Literal {

    public static boolean parseLiteral(@NotNull PsiBuilder builder) {
        return parse(builder, LITERAL, LITERALS);
    }
}
