package net.alexanderkiel.idea_haskell_plugin.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import net.alexanderkiel.idea_haskell_plugin.psi.HaskellElement;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
abstract class HaskellElementImpl extends ASTWrapperPsiElement implements HaskellElement {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return getNode().getElementType() + " (" + getNode().getText() + ")";
    }
}
