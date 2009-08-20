package net.alexanderkiel.idea_haskell_plugin.parser.expression;

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
public class IfExp {

    private IfExp() {
    }

    //---------------------------------------------------------------------------------------------
    // Public Rules
    //---------------------------------------------------------------------------------------------

    public static final GracefulRule ifExp = new GracefulSequenceRule(IF_EXP
            , new GracefulAdapter(new ParseRule(IF_ID))
            , new GracefulDecorator(new GracefulAdapter(exp), "expression expected")
            , new GracefulParseRule(THEN_ID, "'then' expected")
            , new GracefulDecorator(new GracefulAdapter(exp), "expression expected")
            , new GracefulParseRule(ELSE_ID, "'else' expected")
            , new GracefulDecorator(new GracefulAdapter(exp), "expression expected")
    );
}