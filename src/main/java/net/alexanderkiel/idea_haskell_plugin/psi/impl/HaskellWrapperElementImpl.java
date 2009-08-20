package net.alexanderkiel.idea_haskell_plugin.psi.impl;

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.ASTNode;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellWrapperElementImpl extends HaskellElementImpl {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellWrapperElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
