package net.alexanderkiel.idea_haskell_plugin.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.text.CharArrayCharSequence;
import com.intellij.util.text.CharArrayUtil;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.NCOMMENT;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellCommentLexer extends LexerBase {

    private final Lexer lexer;

    private IElementType currentTokenType;
    private int currentTokenStart;
    private int currentTokenEnd;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellCommentLexer(@NotNull Lexer lexer) {
        this.lexer = lexer;
    }

    //---------------------------------------------------------------------------------------------
    // Lexer Implementation
    //---------------------------------------------------------------------------------------------

    @Deprecated
    public void start(char[] buffer, int startOffset, int endOffset, int initialState) {
        final CharArrayCharSequence arrayCharSequence = new CharArrayCharSequence(buffer);
        start(arrayCharSequence, startOffset, endOffset, initialState);
    }

    @Override
    public void start(CharSequence buffer, int startOffset, int endOffset, int initialState) {

        // Start the incremental lexer
        lexer.start(buffer, startOffset, endOffset, initialState);

        // Advance one step in order to determine our first token
        advance();
    }

    public int getState() {
        return lexer.getState(); //TODO
    }

    public IElementType getTokenType() {
        return currentTokenType;
    }

    public int getTokenStart() {
        return currentTokenStart;
    }

    public int getTokenEnd() {
        return currentTokenEnd;
    }

    public void advance() {
        IElementType tokenType = lexer.getTokenType();

        if (tokenType == NCOMMENT) {
            int tokenStart = lexer.getTokenStart();
            while (tokenType == NCOMMENT) {
                lexer.advance();
                tokenType = lexer.getTokenType();
            }
            currentTokenType = NCOMMENT;
            currentTokenStart = tokenStart;
            currentTokenEnd = lexer.getTokenStart();
        } else {
            currentTokenType = tokenType;
            currentTokenStart = lexer.getTokenStart();
            currentTokenEnd = lexer.getTokenEnd();
            lexer.advance();
        }
    }

    @Deprecated
    public char[] getBuffer() {
        return CharArrayUtil.fromSequence(getBufferSequence());
    }

    @Override
    public CharSequence getBufferSequence() {
        return lexer.getBufferSequence();
    }

    public int getBufferEnd() {
        return lexer.getBufferEnd();
    }
}