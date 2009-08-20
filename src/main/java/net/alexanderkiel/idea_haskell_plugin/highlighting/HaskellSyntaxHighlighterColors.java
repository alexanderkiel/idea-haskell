package net.alexanderkiel.idea_haskell_plugin.highlighting;

import com.intellij.lang.Language;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;

import java.awt.*;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public interface HaskellSyntaxHighlighterColors extends HighlighterColors {

    TextAttributesKey INTEGER = SyntaxHighlighterColors.NUMBER;
    TextAttributesKey FLOAT = SyntaxHighlighterColors.NUMBER;
    TextAttributesKey CHAR = SyntaxHighlighterColors.STRING;
    TextAttributesKey STRING = SyntaxHighlighterColors.STRING;

    //TextAttributesKey SPECIAL = new TextAttributesKey("SPECIAL", Language.ANY);

    TextAttributesKey COMMENT = SyntaxHighlighterColors.JAVA_BLOCK_COMMENT;

    TextAttributesKey GLOBAL_FUNCTION_IDENTIFIER = TextAttributesKey.createTextAttributesKey(
            "GLOBAL_FUNCTION_IDENTIFIER", new TextAttributes(new Color(102, 14, 122), null, null, null, Font.BOLD) 
    );

    TextAttributesKey PREPROCESSOR = TextAttributesKey.createTextAttributesKey(
            "PREPROCESSOR", new TextAttributes(new Color(128, 128, 0), null, null, null, Font.PLAIN)
    );

    TextAttributesKey RESERVED_ID = SyntaxHighlighterColors.KEYWORD;

    //TextAttributesKey VAR_SYM = new TextAttributesKey("VAR_SYM", Language.ANY);
    //TextAttributesKey CON_SYM = new TextAttributesKey("CON_SYM", Language.ANY);
    //TextAttributesKey RESERVED_OP = new TextAttributesKey("RESERVED_OP", Language.ANY);

    //TextAttributesKey Q_VAR_ID = new TextAttributesKey("Q_VAR_ID", Language.ANY);
    TextAttributesKey Q_CON_ID = TextAttributesKey.createTextAttributesKey(
            "Q_CON_ID", new TextAttributes(Color.orange.darker(), null, null, null, Font.BOLD)
    );
    //TextAttributesKey QUALIFIED_VAR_SYM = new TextAttributesKey("QUALIFIED_VAR_SYM", Language.ANY);
    //TextAttributesKey QUALIFIED_CON_SYMBOL = new TextAttributesKey("QUALIFIED_CON_SYMBOL", Language.ANY);
}