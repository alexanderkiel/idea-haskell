package net.alexanderkiel.idea_haskell_plugin.psi.impl;

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.ASTNode;
import net.alexanderkiel.idea_haskell_plugin.psi.HaskellModule;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellModuleImpl extends HaskellElementImpl implements HaskellModule {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellModuleImpl(@NotNull ASTNode node) {
        super(node);
    }
}