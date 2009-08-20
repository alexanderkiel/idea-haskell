package net.alexanderkiel.idea_haskell_plugin.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.psi.FileViewProvider;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileType;
import net.alexanderkiel.idea_haskell_plugin.psi.HaskellPsiFile;
import net.alexanderkiel.idea_haskell_plugin.HaskellLanguage;
import net.alexanderkiel.idea_haskell_plugin.HaskellFileType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellPsiFileImpl extends PsiFileBase implements HaskellPsiFile {

    private final HaskellFileType fileType;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellPsiFileImpl(FileViewProvider viewProvider, HaskellFileType fileType) {
        super(viewProvider, Language.findInstance(HaskellLanguage.class));
        this.fileType = fileType;
    }

    //---------------------------------------------------------------------------------------------
    //
    //---------------------------------------------------------------------------------------------

    @NotNull
    public FileType getFileType() {
        return fileType;
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "HaskellPsiFile[name = \"" + getName() + "\"]";
    }
}
