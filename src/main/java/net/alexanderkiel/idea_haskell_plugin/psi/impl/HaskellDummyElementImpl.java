package net.alexanderkiel.idea_haskell_plugin.psi.impl;

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.ASTNode;
import net.alexanderkiel.idea_haskell_plugin.psi.HaskellDummyElement;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellDummyElementImpl extends HaskellElementImpl implements HaskellDummyElement {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellDummyElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
