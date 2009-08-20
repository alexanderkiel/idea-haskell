package net.alexanderkiel.idea_haskell_plugin;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import net.alexanderkiel.idea_haskell_plugin.highlighting.HaskellSyntaxHighlighter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellLanguage extends Language {

    public HaskellLanguage() {
        super("Haskell"); //TODO: Haskell MIME type?

        SyntaxHighlighterFactory.LANGUAGE_FACTORY.addExplicitExtension(this,
                new SingleLazyInstanceSyntaxHighlighterFactory() {
                    @NotNull
                    protected SyntaxHighlighter createHighlighter() {
                        return new HaskellSyntaxHighlighter();
                    }
                });
    }

    
}
