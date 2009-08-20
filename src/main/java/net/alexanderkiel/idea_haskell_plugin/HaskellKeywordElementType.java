package net.alexanderkiel.idea_haskell_plugin;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.NonNls;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellKeywordElementType extends HaskellElementType {

    private final String keywordName;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellKeywordElementType(@NotNull @NonNls String debugName, @NotNull String keywordName) {
        super(debugName);
        this.keywordName = keywordName;
    }

    //---------------------------------------------------------------------------------------------
    //
    //---------------------------------------------------------------------------------------------

    public String getKeywordName() {
        return keywordName;
    }
}
