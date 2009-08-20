package net.alexanderkiel.idea_haskell_plugin.parser.other;

import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.COMMA;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.OPS;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import net.alexanderkiel.idea_haskell_plugin.parser.SeparatedSequenceRule;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.op;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Ops {

    private Ops() {
    }

    public static final Rule ops = new SeparatedSequenceRule(OPS, op, COMMA, 1);
}