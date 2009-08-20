package net.alexanderkiel.idea_haskell_plugin.parser.layout;

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.PsiBuilder;
import com.intellij.openapi.util.Key;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Layout {

    private static final Key<Integer> FIRST_COLUMN_OFFSET_KEY = Key.create("FIRST_COLUMN_OFFSET_KEY");

    private Layout() {
    }

    //---------------------------------------------------------------------------------------------
    // Public Methods
    //---------------------------------------------------------------------------------------------

    public static int getCurrentIndent(@NotNull PsiBuilder builder) {
        return builder.getCurrentOffset() - getFirstColumnOffset(builder) + 1;
    }

    //---------------------------------------------------------------------------------------------
    // Helper Methods
    //---------------------------------------------------------------------------------------------

    private static int getFirstColumnOffset(@NotNull PsiBuilder builder) {
        Integer offset = builder.getUserData(FIRST_COLUMN_OFFSET_KEY);
        return offset == null ? 0 : offset;
    }

    static void setFirstColumnOffset(@NotNull PsiBuilder builder, int offset) {
        builder.putUserData(FIRST_COLUMN_OFFSET_KEY, offset);
    }
}
