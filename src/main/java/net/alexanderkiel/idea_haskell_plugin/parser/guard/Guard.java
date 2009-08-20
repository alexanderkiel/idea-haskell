package net.alexanderkiel.idea_haskell_plugin.parser.guard;

import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.parser.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.Exp.exp;
import static net.alexanderkiel.idea_haskell_plugin.parser.expression.Exp0.exp0;
import net.alexanderkiel.idea_haskell_plugin.parser.helper.GracefulParseRule;
import net.alexanderkiel.idea_haskell_plugin.parser.helper.ParseRule;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Guard {

    private Guard() {
    }

    //---------------------------------------------------------------------------------------------
    // Public Rules
    //---------------------------------------------------------------------------------------------

    public static final GracefulRule guardRhs;

    public static final GracefulRule guardPat;

    //---------------------------------------------------------------------------------------------
    // Private Rules
    //---------------------------------------------------------------------------------------------

    private static final GracefulRule guard = new GracefulSequenceRule(GUARD
            , new GracefulAdapter(new ParseRule(BAR_OP))
            , new GracefulDecorator(new GracefulAdapter(exp0), "expression expected")
    );

    static {
        guardRhs = new GracefulPlusRule(new GracefulSequenceRule(GUARD_RHS
                , guard
                , new GracefulParseRule(EQUAL_OP, "'=' expected")
                , new GracefulDecorator(new GracefulAdapter(exp), "expression expected")
        ));

        guardPat = new GracefulPlusRule(new GracefulSequenceRule(GUARD_PAT
                , guard
                , new GracefulParseRule(ARROW_OP, "'->' expected")
                , new GracefulDecorator(new GracefulAdapter(exp), "expression expected")
        ));
    }
}
