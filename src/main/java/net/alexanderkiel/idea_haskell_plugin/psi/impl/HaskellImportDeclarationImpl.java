package net.alexanderkiel.idea_haskell_plugin.psi.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.lang.ASTNode;
import net.alexanderkiel.idea_haskell_plugin.psi.HaskellImportDeclaration;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellImportDeclarationImpl extends HaskellElementImpl implements HaskellImportDeclaration {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellImportDeclarationImpl(@NotNull ASTNode node) {
        super(node);
    }

    //---------------------------------------------------------------------------------------------
    // HaskellImportDeclaration Implementation
    //---------------------------------------------------------------------------------------------

    @NotNull
    public String getModuleName() {
        throw new UnsupportedOperationException();
    }

    public boolean isQualified() {
        return false;
    }

    @Nullable
    public String getAliasName() {
        return null;
    }
}
