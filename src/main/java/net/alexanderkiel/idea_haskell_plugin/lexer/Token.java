package net.alexanderkiel.idea_haskell_plugin.lexer;

import com.intellij.psi.tree.IElementType;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
class Token {

    final IElementType tokenType;
    final int tokenStart;
    final int tokenEnd;

    Token(IElementType tokenType, int tokenStart, int tokenEnd) {
        this.tokenType = tokenType;
        this.tokenStart = tokenStart;
        this.tokenEnd = tokenEnd;
    }

    @Override
    public String toString() {
        return tokenType + " (" + tokenStart + ", " + tokenEnd + ")";
    }
}
