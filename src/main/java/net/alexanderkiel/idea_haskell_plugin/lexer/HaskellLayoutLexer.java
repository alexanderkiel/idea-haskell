package net.alexanderkiel.idea_haskell_plugin.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.text.CharArrayCharSequence;
import com.intellij.util.text.CharArrayUtil;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import org.jetbrains.annotations.NotNull;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellLayoutLexer extends LexerBase {

    private final static TokenSet CONTEXT_CREATING_KEYWORDS = TokenSet.create(LET_ID, WHERE_ID, DO_ID, OF_ID);
    private final static TokenSet WHITE_SPACE_TOKENS = TokenSet.create(WHITE_SPACE, TAB, COMMENT, NCOMMENT);
    private final static int TAB_STOP_GAP = 8;

    private final Lexer lexer;

    private enum State {

        START, NORMAL, OPEN_CONTEXT, RETURN_PENDING
    }

    private State state;

    private Token pendingToken;
    private final Deque<Integer> indentStack = new LinkedList<Integer>();

    private Token currentToken;

    private int firstColumnOffset;
    private int additionalTabIndent;
    private boolean beginOfLine;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellLayoutLexer(@NotNull Lexer lexer) {
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
        if (startOffset != 0) {
            throw new IllegalArgumentException("do not support incremental lexing: startOffset == 0");
        }
        if (initialState != 0) {
            throw new IllegalArgumentException("do not support incremental lexing: initialState == 0");
        }

        // Reset own state
        state = State.START;
        indentStack.clear();
        firstColumnOffset = 0;
        additionalTabIndent = 0;
        beginOfLine = true;

        // Start the incremental lexer
        lexer.start(buffer, startOffset, endOffset, initialState);

        // Advance one step in order to determine our first token
        advance();
    }

    public int getState() {
        return lexer.getState(); //TODO
    }

    public IElementType getTokenType() {
        return currentToken.tokenType;
    }

    public int getTokenStart() {
        return currentToken.tokenStart;
    }

    public int getTokenEnd() {
        return currentToken.tokenEnd;
    }

    public void advance() {
        Token nextToken = null;
        boolean advance = true;

        final Token token;
        if (state == State.RETURN_PENDING) {
            if (pendingToken == null) {
                throw new IllegalStateException("no pending token to return");
            }

            token = pendingToken;
            pendingToken = null;
            state = State.NORMAL;
            advance = false;
        } else {
            token = getTokenFromLexer();
        }

        if (token.tokenType == null) {

            // Close all open contexts at the end of the file
            if (!indentStack.isEmpty()) {
                indentStack.pop();
                nextToken = buildCloseBraceToken();
            } else {
                nextToken = token;
            }

            advance = false;

        } else if (state == State.START) {

            // Skip white spaces
            if (WHITE_SPACE_TOKENS.contains(token.tokenType)) {
                nextToken = token;
            } else {

                // Open initial context if first lexeme isn't "{" or "module"
                if (token.tokenType != OPEN_BRACE && token.tokenType != MODULE_ID) {
                    indentStack.push(getIndent(token));
                    pendingToken = token;
                    nextToken = new Token(OPEN_BRACE, 0, 0);
                    state = State.RETURN_PENDING;
                } else {
                    state = State.NORMAL;
                    nextToken = token;
                }
            }

        } else if (state == State.NORMAL) {

            if (beginOfLine && !WHITE_SPACE_TOKENS.contains(token.tokenType)) {

                int indent = getIndent(token);
                if (!indentStack.isEmpty()) {
                    if (indentStack.peek() == indent) {

                        pendingToken = token;
                        nextToken = buildSemicolonToken();
                        state = State.RETURN_PENDING;

                    } else if (indentStack.peek() > indent) {

                        indentStack.pop();
                        pendingToken = token;
                        nextToken = buildCloseBraceToken();
                        state = State.RETURN_PENDING;

                    }
                }

            }

            if (nextToken == null) {
                if (CONTEXT_CREATING_KEYWORDS.contains(token.tokenType)) {

                    // We have to open a new context here
                    // Output the current token and go into the OPEN_CONTEXT state
                    nextToken = token;
                    state = State.OPEN_CONTEXT;

                } else {
                    nextToken = token;
                }
            }

        } else if (state == State.OPEN_CONTEXT) {

            if (WHITE_SPACE_TOKENS.contains(token.tokenType)) {

                // Skip over white spaces
                nextToken = token;

            } else if (token.tokenType == OPEN_BRACE) {

                // The first lexeme after a layout keyword is already a open brace. So we have to do nothing
                // special. Just put this token into the pending queue and go into the RETURN_PENDING state.
                nextToken = token;
                state = State.NORMAL;

            } else if (token.tokenType == null) {

                //TODO: This state need further inspection
                nextToken = token;
                state = State.NORMAL;

            } else {

                // Okay. There is a non open brace lexeme after a layout keyword. We have to insert an open
                // brace ourself. First put the indent of the token onto the indent stack, than add the token
                // to the pending queue and output the open brace.
                indentStack.push(getIndent(token));
                pendingToken = token;
                nextToken = buildOpenBraceToken();
                state = State.RETURN_PENDING;
            }

        } else {
            throw new IllegalStateException("unknown state");
        }

        // Unset clean line if something other than a white space or a implicit close brace is output
        if (!WHITE_SPACE_TOKENS.contains(nextToken.tokenType) && (nextToken.tokenType != CLOSE_BRACE ||
                nextToken.tokenEnd != nextToken.tokenStart)) {
            beginOfLine = false;
        }

        currentToken = nextToken;

        if (advance) {
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

    //---------------------------------------------------------------------------------------------
    // Helper Methods
    //---------------------------------------------------------------------------------------------

    @NotNull
    private Token getTokenFromLexer() {
        IElementType tokenType = lexer.getTokenType();
        int tokenStart = lexer.getTokenStart();
        int tokenEnd = lexer.getTokenEnd();

        if (tokenType == NEWLINE || tokenType == COMMENT || tokenType == PREPROCESSOR) {
            firstColumnOffset = tokenEnd;
            additionalTabIndent = 0;
            beginOfLine = true;
            return new Token(tokenType == NEWLINE ? WHITE_SPACE : tokenType, tokenStart, tokenEnd);
        } else if (beginOfLine && tokenType == TAB) {

            // We have tabs at the beginning of line.
            // Lets correct the indent calculation
            Token token = new Token(tokenType, tokenStart, tokenEnd);

            // Increment the additional indent which comes from tabs.
            additionalTabIndent += TAB_STOP_GAP - ((getIndent(token) - 1) % TAB_STOP_GAP) - 1;

            return token;
        } else {
            return new Token(tokenType, tokenStart, tokenEnd);
        }
    }

    @NotNull
    private Token buildOpenBraceToken() {
        return new Token(OPEN_BRACE, currentToken.tokenStart, currentToken.tokenStart);
    }

    @NotNull
    private Token buildCloseBraceToken() {
        return new Token(CLOSE_BRACE, currentToken.tokenStart, currentToken.tokenStart);
    }

    @NotNull
    private Token buildSemicolonToken() {
        return new Token(SEMICOLON, currentToken.tokenStart, currentToken.tokenStart);
    }

    private int getIndent(Token token) {
        return token.tokenStart - firstColumnOffset + 1 + additionalTabIndent;
    }
}
