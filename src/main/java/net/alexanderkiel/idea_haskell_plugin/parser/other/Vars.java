package net.alexanderkiel.idea_haskell_plugin.parser.other;

import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.COMMA;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.VARS;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import net.alexanderkiel.idea_haskell_plugin.parser.SeparatedSequenceRule;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.var;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Vars {

    private Vars() {
    }

    public static final Rule vars = new SeparatedSequenceRule(VARS, var, COMMA, 1);
}
