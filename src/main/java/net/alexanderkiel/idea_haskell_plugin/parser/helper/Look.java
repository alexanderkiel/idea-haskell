package net.alexanderkiel.idea_haskell_plugin.parser.helper;

import com.intellij.psi.tree.IElementType;
import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.RESERVED_IDS;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Look {

    public static boolean lookFor(@NotNull PsiBuilder builder, @NotNull IElementType tokenType) {
        return builder.getTokenType() == tokenType;
    }

    public static boolean lookForAnyKeyword(@NotNull PsiBuilder builder) {
        return RESERVED_IDS.contains(builder.getTokenType());
    }
}
