package net.alexanderkiel.idea_haskell_plugin.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.lexer.HaskellIncrementalLexer;
import net.alexanderkiel.idea_haskell_plugin.highlighting.HaskellSyntaxHighlighterColors;


/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellSyntaxHighlighter extends SyntaxHighlighterBase {

    private final Lexer lexer;

    private final Map<IElementType, TextAttributesKey> colors = new HashMap<IElementType, TextAttributesKey>();

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellSyntaxHighlighter() {

        lexer = new HaskellIncrementalLexer();

        colors.put(INTEGER, HaskellSyntaxHighlighterColors.INTEGER);
        colors.put(FLOAT, HaskellSyntaxHighlighterColors.FLOAT);
        colors.put(CHAR, HaskellSyntaxHighlighterColors.CHAR);
        colors.put(STRING, HaskellSyntaxHighlighterColors.STRING);

        colors.put(COMMENT, HaskellSyntaxHighlighterColors.COMMENT);
        colors.put(NCOMMENT, HaskellSyntaxHighlighterColors.COMMENT);
        colors.put(PREPROCESSOR, HaskellSyntaxHighlighterColors.PREPROCESSOR);

        colors.put(VAR_ID, HaskellSyntaxHighlighterColors.GLOBAL_FUNCTION_IDENTIFIER);        

        colors.put(BAD_CHARACTER, HaskellSyntaxHighlighterColors.BAD_CHARACTER);
    }

    //---------------------------------------------------------------------------------------------
    // SyntaxHighlighter Implementation
    //---------------------------------------------------------------------------------------------

    @NotNull
    public Lexer getHighlightingLexer() {
        return lexer;
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return RESERVED_IDS.contains(tokenType)
                ? pack(HaskellSyntaxHighlighterColors.RESERVED_ID)
                : pack(colors.get(tokenType));
    }
}
