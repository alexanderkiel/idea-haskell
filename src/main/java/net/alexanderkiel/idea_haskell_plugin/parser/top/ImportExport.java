package net.alexanderkiel.idea_haskell_plugin.parser.top;

import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.BracketPair.PARS;
import net.alexanderkiel.idea_haskell_plugin.parser.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.con;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp.var;
import net.alexanderkiel.idea_haskell_plugin.parser.helper.ParseRule;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
class ImportExport {

    private ImportExport() {
    }

    static final GracefulRule allNames = new GracefulBracketedRule(
            new GracefulAdapter(new ParseRule(DOUBLE_DOT_OP)), PARS
    );

    /**
     * cname <- var / con
     */
    private static final Rule cName = new OrElseRule(C_NAME, var, con);

    /**
     * ( cname1 , ... , cnamen )    ( n >= 0 )
     */
    static final GracefulRule cNames = new GracefulBracketedSequenceRule(
            new GracefulAdapter(cName), PARS, COMMA, 0
    );
}
