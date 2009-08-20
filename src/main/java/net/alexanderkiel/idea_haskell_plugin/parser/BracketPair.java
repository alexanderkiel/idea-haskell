package net.alexanderkiel.idea_haskell_plugin.parser;

import net.alexanderkiel.idea_haskell_plugin.HaskellElementType;
import net.alexanderkiel.idea_haskell_plugin.HaskellBracketElementType;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public enum BracketPair {

    PARS(OPEN_PAR, CLOSE_PAR), BRACKET(OPEN_BRACKET, CLOSE_BRACKET), BRACES(OPEN_BRACE, CLOSE_BRACE);

    //---------------------------------------------------------------------------------------------
    // Fields
    //---------------------------------------------------------------------------------------------

    @NotNull
    private final HaskellBracketElementType openBracket;

    @NotNull
    private final HaskellBracketElementType closeBracket;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    BracketPair(@NotNull HaskellBracketElementType openBracket, @NotNull HaskellBracketElementType closeBracket) {
        this.openBracket = openBracket;
        this.closeBracket = closeBracket;
    }

    //---------------------------------------------------------------------------------------------
    //
    //---------------------------------------------------------------------------------------------

    @NotNull
    public HaskellBracketElementType getOpenBracket() {
        return openBracket;
    }

    @NotNull
    public HaskellBracketElementType getCloseBracket() {
        return closeBracket;
    }
}
