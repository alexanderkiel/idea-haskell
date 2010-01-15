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

    /**
     * Parses the contents of the specified PSI builder and returns an AST tree with the specified type of root element.
     * The PSI builder contents is the entire file or (if chameleon tokens are used) the text of a chameleon token which
     * needs to be reparsed.
     *
     * @param root    the type of the root element in the AST tree.
     * @param builder the builder which is used to retrieve the original file tokens and build the AST tree.
     * @return the root of the resulting AST tree.
     */
    @NotNull
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        //TODO: remove in production
        builder.setDebugMode(true);

        builder.setTokenTypeRemapper(new NewLineSniffer(builder));

        PsiBuilder.Marker rootMarker = builder.mark();

        Module.parseModule(builder);

        // Eat the rest of the tokens in the file
        if (!builder.eof()) {
            PsiBuilder.Marker unparsed = builder.mark();
            while (!builder.eof()) {
                builder.advanceLexer();
            }
            unparsed.error("unparsed");
        }

        rootMarker.done(root);

        return builder.getTreeBuilt();
    }
}
