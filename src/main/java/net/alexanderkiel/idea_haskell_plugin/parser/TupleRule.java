package net.alexanderkiel.idea_haskell_plugin.parser;

import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.OPEN_PAR;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.COMMA;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.CLOSE_PAR;

/**
 * ( nested1 , ... , nestedn ) ( n >= 2 )
 *
 * @author Alexander Kiel
 * @version $Id$
 */
public class TupleRule extends BracketedSequenceRule {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public TupleRule(Rule nestedRule) {
        super(nestedRule, OPEN_PAR, CLOSE_PAR, COMMA, 2);
    }
}
