package net.alexanderkiel.idea_haskell_plugin.lexer;

import com.intellij.lexer.FlexAdapter;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellIncrementalLexer extends FlexAdapter {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public HaskellIncrementalLexer() {
        super(new HaskellFlexLexer((java.io.Reader) null));
    }
}
