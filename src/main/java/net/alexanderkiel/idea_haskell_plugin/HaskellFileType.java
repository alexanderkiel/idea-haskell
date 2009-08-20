package net.alexanderkiel.idea_haskell_plugin;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellFileType extends LanguageFileType {

    public HaskellFileType() {
        super(new HaskellLanguage());
    }

    @NotNull
    public String getName() {
        return "Haskell";
    }

    @NotNull
    public String getDescription() {
        return "Haskell";
    }

    @NotNull
    public String getDefaultExtension() {
        return "hs";
    }

    public Icon getIcon() {
        return IconLoader.getIcon("/net/alexanderkiel/idea_haskell_plugin/normal-partround-lambda-col1-16.png");
    }
}
