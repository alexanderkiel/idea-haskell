package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.*;
import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.util.diff.FlyweightCapableTreeStructure;

import java.util.List;
import java.util.LinkedList;

/**
 * @author Alexander Kiel
* @version $Id$
*/
class TestPsiBuilder extends SimpleUserDataHolder implements PsiBuilder {

    private final String content;
    private final Lexer lexer;

    private final ParserDefinition parserDefinition;

    private Token currentToken = NIL;

    private TestMarker rootMarker;
    private TestMarker currentMarker;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    TestPsiBuilder(ParserDefinition parserDefinition, String content) {
        this.content = content;

        lexer = parserDefinition.createLexer(null);
        this.parserDefinition = parserDefinition;
        rootMarker = new TestMarker(null, currentToken);

        lexer.start(content, 0, content.length(), 0);
        skipWhiteSpaces();
        setCurrentToken();
    }

    //---------------------------------------------------------------------------------------------
    // PsiBuilder Implementation
    //---------------------------------------------------------------------------------------------

    public CharSequence getOriginalText() {
        return content;
    }

    public void advanceLexer() {
        if (currentToken.next == NIL) {
            lexer.advance();
            skipWhiteSpaces();
            setCurrentToken();
        } else {
            currentToken = currentToken.next;
        }
    }

    public IElementType getTokenType() {
        return currentToken.tokenType;
    }

    public void setTokenTypeRemapper(ITokenTypeRemapper remapper) {
    }

    public String getTokenText() {
        return lexer.getBufferSequence().subSequence(currentToken.tokenStart, currentToken.tokenEnd).toString();
    }

    public int getCurrentOffset() {
        return currentToken.tokenStart;
    }

    public Marker mark() {
        TestMarker marker = new TestMarker(rootMarker, currentToken);
        rootMarker.childs.add(marker);
        rootMarker = marker;
        return marker;
    }

    public void error(String messageText) {
    }

    public boolean eof() {
        return currentToken.tokenType == null;
    }

    public ASTNode getTreeBuilt() {
        rootMarker.done(parserDefinition.getFileNodeType());
        return rootMarker.node;
    }

    public FlyweightCapableTreeStructure<LighterASTNode> getLightTree() {
        return null;
    }

    public void setDebugMode(boolean dbgMode) {
    }

    public void enforceCommentTokens(TokenSet tokens) {
    }

    //---------------------------------------------------------------------------------------------
    // Helper Methods
    //---------------------------------------------------------------------------------------------

    private void skipWhiteSpaces() {
        while (parserDefinition.getWhitespaceTokens().contains(lexer.getTokenType())) {
            lexer.advance();
        }
    }

    private void setCurrentToken() {
        currentToken.next = new Token(lexer.getTokenType(), lexer.getTokenStart(), lexer.getTokenEnd());
        currentToken = currentToken.next;
    }

    //---------------------------------------------------------------------------------------------
    // Marker
    //---------------------------------------------------------------------------------------------

    private class TestMarker implements Marker {

        private final Token token;
        private final List<Token> childTokens;

        private ASTNode node;

        private TestMarker parent;
        private final List<TestMarker> childs;

        private TestMarker(TestMarker parent, Token token) {
            this.parent = parent;
            this.token = token;
            childTokens = new LinkedList<Token>();
            childs = new LinkedList<TestMarker>();
        }

        public Marker precede() {
            return null;
        }

        public void drop() {

            // Lift all my childs up to my parent
            for (TestMarker child : childs) {
                assert child.parent == this;
                child.parent = parent;
                parent.childs.add(child);
            }
        }

        public void rollbackTo() {
            currentToken = token;
            parent.childs.remove(this);
        }

        public void done(IElementType type) {
            if (!childs.isEmpty()) {
                //throw new
            }
            node.addChild(new CompositeElement(type));
        }

        public void doneBefore(IElementType type, Marker before) {
        }

        public void doneBefore(IElementType type, Marker before, String errorMessage) {
        }

        public void error(String message) {
        }
    }

    //---------------------------------------------------------------------------------------------
    // Token
    //---------------------------------------------------------------------------------------------

    private final static Token NIL = new Token(null, 0, 0);

    private static class Token {

        private final IElementType tokenType;
        private final int tokenStart;
        private final int tokenEnd;
        private Token next = NIL;

        private Token(IElementType tokenType, int tokenStart, int tokenEnd) {
            this.tokenType = tokenType;
            this.tokenStart = tokenStart;
            this.tokenEnd = tokenEnd;
        }
    }
}
