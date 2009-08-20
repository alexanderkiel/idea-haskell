package net.alexanderkiel.idea_haskell_plugin.psi.impl;

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.ASTNode;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellModuleIdImpl extends HaskellElementImpl {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellModuleIdImpl(@NotNull ASTNode node) {
        super(node);
    }
}