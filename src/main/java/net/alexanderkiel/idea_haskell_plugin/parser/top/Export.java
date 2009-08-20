package net.alexanderkiel.idea_haskell_plugin.parser.top;

import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.BracketPair.PARS;
import net.alexanderkiel.idea_haskell_plugin.parser.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier.modId;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier.qTypeConOrClass;
import net.alexanderkiel.idea_haskell_plugin.parser.basic.VarConOp;
import net.alexanderkiel.idea_haskell_plugin.parser.helper.ParseRule;
import static net.alexanderkiel.idea_haskell_plugin.parser.top.ImportExport.allNames;
import static net.alexanderkiel.idea_haskell_plugin.parser.top.ImportExport.cNames;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Export {

    private Export() {
    }

    /**
     * qtyconorcls ((..) | (cname1 , ... , cnamen))?
     */
    private static final GracefulRule typeConOrClassExport = new GracefulSequenceRule(
            new GracefulAdapter(qTypeConOrClass)
            , new GracefulOptionRule(new GracefulOrElseRule(allNames, cNames))
    );

    /**
     * module modid
     */
    private static final GracefulRule moduleExport = new GracefulSequenceRule(
            new GracefulAdapter(new ParseRule(MODULE_ID)), new GracefulAdapter(modId)
    );

    /**
     * export <- qvar / qtyconorcls / module modid
     */
    private static final GracefulRule export = new GracefulOrElseRule(EXPORT
            , new GracefulAdapter(VarConOp.qVar)
            , typeConOrClassExport
            , moduleExport
    );

    /**
     * export1 , ... , exportn  ( n >= 0 )
     */
    private static final GracefulRule internalExports = new GracefulSeparatedSequenceRule(export, COMMA, 0);

    /**
     * ( export1 , ... , exportn [,] )  ( n >= 0 )
     */
    public static final GracefulRule exports = new GracefulBracketedRule(new GracefulSequenceRule(
            internalExports, new GracefulOptionRule(new GracefulAdapter(new ParseRule(SEMICOLON)))
    ), PARS);
}
