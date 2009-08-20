package net.alexanderkiel.idea_haskell_plugin.parser;

import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.CLOSE_BRACKET;

/**
 * [ nested1 , ... , nestedn ] ( n >= 1 )
 *
 * @author Alexander Kiel
 * @version $Id$
 */
public class ListRule extends BracketedSequenceRule {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public ListRule(Rule nestedRule) {
        super(nestedRule, OPEN_BRACKET, CLOSE_BRACKET, COMMA, 1);
    }
}