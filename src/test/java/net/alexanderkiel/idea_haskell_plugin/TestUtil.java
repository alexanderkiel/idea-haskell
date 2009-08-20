package net.alexanderkiel.idea_haskell_plugin;

import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.LocalTimeCounter;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class TestUtil {

    public static final String TEMP_FILE = "temp.hs";

    public static PsiFile createPseudoPhysicalFanFile(final Project project, final String text)
            throws IncorrectOperationException {
        return createPseudoHaskellFile(project, TEMP_FILE, text);
    }

    public static PsiFile createPseudoHaskellFile(final Project project, final String fileName,
                                                  final String text) throws IncorrectOperationException {
        
        return PsiFileFactory.getInstance(project).createFileFromText(
                fileName,
                FileTypeManager.getInstance().getFileTypeByFileName(fileName),
                text,
                LocalTimeCounter.currentTime(),
                true);
    }

}
