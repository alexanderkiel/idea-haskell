package net.alexanderkiel.idea_haskell_plugin;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileTypes.FileTypeManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellFileTypeManager implements ApplicationComponent {

    public static final HaskellFileType FILE_TYPE = new HaskellFileType();

    @NotNull
    public String getComponentName() {
        return "HaskellFileTypeManager";
    }

    public void initComponent() {
        FileTypeManager.getInstance().registerFileType(FILE_TYPE, "hs");
    }

    public void disposeComponent() {
    }
}
