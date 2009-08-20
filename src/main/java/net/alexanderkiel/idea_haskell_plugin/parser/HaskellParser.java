package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import net.alexanderkiel.idea_haskell_plugin.parser.top.Module;
import net.alexanderkiel.idea_haskell_plugin.parser.layout.NewLineSniffer;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellParser implements PsiParser {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellParser() {
    }

    //---------------------------------------------------------------------------------------------
    // PsiParser Implementation
    //---------------------------------------------------------------------------------------------

    @NotNull
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        //TODO: remove in production
        builder.setDebugMode(true);

        builder.setTokenTypeRemapper(new NewLineSniffer(builder));

        final PsiBuilder.Marker rootMarker = builder.mark();

        Module.parseModule(builder);

        // Eat the rest of the tokens in the file
        if (!builder.eof()) {
            final PsiBuilder.Marker rest = builder.mark();
            while (!builder.eof()) {
                builder.advanceLexer();
            }
            rest.error("unparsed");
        }

        rootMarker.done(root);

        return builder.getTreeBuilt();
    }
}
