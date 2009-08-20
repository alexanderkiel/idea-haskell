package net.alexanderkiel.idea_haskell_plugin;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellElementType extends IElementType {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellElementType(@NotNull @NonNls String debugName) {
        super(debugName, HaskellFileTypeManager.FILE_TYPE.getLanguage());
    }
}
