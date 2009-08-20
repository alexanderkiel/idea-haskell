package net.alexanderkiel.idea_haskell_plugin.parser.helper;

import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Brackets {

    private Brackets() {
    }
    
    public static final Rule openPar = new ParseRule(OPEN_PAR);
    public static final Rule closePar = new ParseRule(CLOSE_PAR);

    public static final Rule openBracket = new ParseRule(OPEN_BRACKET);
    public static final Rule closeBracket = new ParseRule(CLOSE_BRACKET);

    public static final Rule openBrace = new ParseRule(OPEN_BRACE);
    public static final Rule closeBrace = new ParseRule(CLOSE_BRACE);
}
