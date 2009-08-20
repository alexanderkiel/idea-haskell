package net.alexanderkiel.idea_haskell_plugin.parser.layout;

import com.intellij.lang.ITokenTypeRemapper;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class NewLineSniffer implements ITokenTypeRemapper {

    @NotNull
    private final PsiBuilder builder;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public NewLineSniffer(@NotNull PsiBuilder builder) {
        this.builder = builder;
    }

    //---------------------------------------------------------------------------------------------
    // ITokenTypeRemapper Implementation
    //---------------------------------------------------------------------------------------------

    public IElementType filter(IElementType source, int start, int end, CharSequence text) {
        if (source == NEWLINE || source == COMMENT || source == PREPROCESSOR) {
            Layout.setFirstColumnOffset(builder, end);
            return source == NEWLINE ? WHITE_SPACE : source;
        } else {
            return source;
        }
    }
}
