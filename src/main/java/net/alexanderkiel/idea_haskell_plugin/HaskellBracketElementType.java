package net.alexanderkiel.idea_haskell_plugin;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.NonNls;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellBracketElementType extends HaskellElementType {

    private final String bracketSymbol;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellBracketElementType(@NotNull @NonNls String debugName, @NotNull String bracketSymbol) {
        super(debugName);
        this.bracketSymbol = bracketSymbol;
    }

    //---------------------------------------------------------------------------------------------
    //
    //---------------------------------------------------------------------------------------------

    public String getBracketSymbol() {
        return bracketSymbol;
    }
}