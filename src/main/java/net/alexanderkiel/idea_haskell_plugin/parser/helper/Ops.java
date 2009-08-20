package net.alexanderkiel.idea_haskell_plugin.parser.helper;

import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Ops {

    private Ops() {
    }

    public static final Rule arrowOp = new ParseRule(ARROW_OP);
}
