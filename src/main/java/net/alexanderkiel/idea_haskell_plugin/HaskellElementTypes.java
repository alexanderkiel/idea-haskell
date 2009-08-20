package net.alexanderkiel.idea_haskell_plugin;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.Language;
import net.alexanderkiel.idea_haskell_plugin.HaskellLanguage;
import net.alexanderkiel.idea_haskell_plugin.HaskellElementType;
import net.alexanderkiel.idea_haskell_plugin.HaskellKeywordElementType;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public interface HaskellElementTypes {

    IFileElementType FILE = new IFileElementType(Language.findInstance(HaskellLanguage.class));

    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    HaskellElementType COMMENT = new HaskellElementType("COMMENT");
    HaskellElementType NCOMMENT = new HaskellElementType("NCOMMENT");
    HaskellElementType OPEN_COMMENT = new HaskellElementType("OPEN_COMMENT");
    HaskellElementType CLOSE_COMMENT = new HaskellElementType("CLOSE_COMMENT");
    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

    HaskellElementType NEWLINE = new HaskellElementType("NEWLINE");
    HaskellElementType TAB = new HaskellElementType("TAB");

    HaskellElementType PREPROCESSOR = new HaskellElementType("PREPROCESSOR");

    //---------------------------------------------------------------------------------------------
    // Lexer Tokens needed by the Parser
    //---------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------
    // Special
    //---------------------------------------------------------------------------------------------

    HaskellBracketElementType OPEN_PAR = new HaskellBracketElementType("OPEN_PAR", "(");
    HaskellBracketElementType CLOSE_PAR = new HaskellBracketElementType("CLOSE_PAR", ")");
    HaskellElementType COMMA = new HaskellElementType("COMMA");
    HaskellElementType SEMICOLON = new HaskellElementType("SEMICOLON");
    HaskellBracketElementType OPEN_BRACKET = new HaskellBracketElementType("OPEN_BRACKET", "[");
    HaskellBracketElementType CLOSE_BRACKET = new HaskellBracketElementType("CLOSE_BRACKET", "]");
    HaskellElementType BACKQUOTE = new HaskellElementType("BACKQUOTE");
    HaskellBracketElementType OPEN_BRACE = new HaskellBracketElementType("OPEN_BRACE", "{");
    HaskellBracketElementType CLOSE_BRACE = new HaskellBracketElementType("CLOSE_BRACE", "}");

    //---------------------------------------------------------------------------------------------
    // Reserved Ids
    //---------------------------------------------------------------------------------------------

    HaskellKeywordElementType CASE_ID = new HaskellKeywordElementType("CASE_ID", "case");
    HaskellKeywordElementType CLASS_ID = new HaskellKeywordElementType("CLASS_ID", "class");
    HaskellKeywordElementType DATA_ID = new HaskellKeywordElementType("DATA_ID", "data");
    HaskellKeywordElementType DEFAULT_ID = new HaskellKeywordElementType("DEFAULT_ID", "default");
    HaskellKeywordElementType DERIVING_ID = new HaskellKeywordElementType("DERIVING_ID", "deriving");
    HaskellKeywordElementType DO_ID = new HaskellKeywordElementType("DO_ID", "do");
    HaskellKeywordElementType ELSE_ID = new HaskellKeywordElementType("ELSE_ID", "else");
    HaskellKeywordElementType IF_ID = new HaskellKeywordElementType("IF_ID", "if");
    HaskellKeywordElementType IMPORT_ID = new HaskellKeywordElementType("IMPORT_ID", "import");
    HaskellKeywordElementType IN_ID = new HaskellKeywordElementType("IN_ID", "in");
    HaskellKeywordElementType INFIX_ID = new HaskellKeywordElementType("INFIX_ID", "infix");
    HaskellKeywordElementType INFIXL_ID = new HaskellKeywordElementType("INFIXL_ID", "infixl");
    HaskellKeywordElementType INFIXR_ID = new HaskellKeywordElementType("INFIXR_ID", "infixr");
    HaskellKeywordElementType INSTANCE_ID = new HaskellKeywordElementType("INSTANCE_ID", "instance");
    HaskellKeywordElementType LET_ID = new HaskellKeywordElementType("LET_ID", "let");
    HaskellKeywordElementType MODULE_ID = new HaskellKeywordElementType("MODULE_ID", "module");
    HaskellKeywordElementType NEWTYPE_ID = new HaskellKeywordElementType("NEWTYPE_ID", "newtype");
    HaskellKeywordElementType OF_ID = new HaskellKeywordElementType("OF_ID", "of");
    HaskellKeywordElementType THEN_ID = new HaskellKeywordElementType("THEN_ID", "then");
    HaskellKeywordElementType TYPE_ID = new HaskellKeywordElementType("TYPE_ID", "type");
    HaskellKeywordElementType WHERE_ID = new HaskellKeywordElementType("WHERE_ID", "where");
    HaskellKeywordElementType WILDCARD_ID = new HaskellKeywordElementType("WILDCARD_ID", "_");

    HaskellElementType VAR_SYM = new HaskellElementType("VAR_SYM");
    HaskellElementType CON_SYM = new HaskellElementType("CON_SYM");

    //---------------------------------------------------------------------------------------------
    // Reserved Ops
    //---------------------------------------------------------------------------------------------

    HaskellElementType DOUBLE_DOT_OP = new HaskellElementType("DOUBLE_DOT_OP");
    HaskellElementType COLON_OP = new HaskellElementType("COLON_OP");
    HaskellElementType DOUBLE_COLON_OP = new HaskellElementType("DOUBLE_COLON_OP");
    HaskellElementType EQUAL_OP = new HaskellElementType("EQUAL_OP");
    HaskellElementType BACKSLASH_OP = new HaskellElementType("BACKSLASH_OP");
    HaskellElementType BAR_OP = new HaskellElementType("BAR_OP");
    HaskellElementType GENERATOR_OP = new HaskellElementType("GENERATOR_OP");
    HaskellElementType ARROW_OP = new HaskellElementType("ARROW_OP");
    HaskellElementType AT_OP = new HaskellElementType("AT_OP");
    HaskellElementType TILDE_OP = new HaskellElementType("TILDE_OP");
    HaskellElementType CONTEXT_OP = new HaskellElementType("CONTEXT_OP");

    HaskellElementType VAR_ID = new HaskellElementType("VAR_ID");
    HaskellElementType CON_ID = new HaskellElementType("CON_ID");

    HaskellElementType INTEGER = new HaskellElementType("INTEGER");
    HaskellElementType FLOAT = new HaskellElementType("FLOAT");

    HaskellElementType CHAR = new HaskellElementType("CHAR");
    HaskellElementType STRING = new HaskellElementType("STRING");

    //---------------------------------------------------------------------------------------------
    // Parser Token Types
    //---------------------------------------------------------------------------------------------

    HaskellElementType MODULE = new HaskellElementType("MODULE");
    HaskellElementType BODY = new HaskellElementType("BODY");
    HaskellElementType IMP_DECLS = new HaskellElementType("IMP_DECLS");
    HaskellElementType EXPORTS = new HaskellElementType("EXPORTS");
    HaskellElementType EXPORT = new HaskellElementType("EXPORT");
    HaskellElementType IMP_DECL = new HaskellElementType("IMP_DECL");
    HaskellElementType IMP_SPEC = new HaskellElementType("IMP_SPEC");
    HaskellElementType IMPORT = new HaskellElementType("IMPORT");
    HaskellElementType C_NAME = new HaskellElementType("C_NAME");
    HaskellElementType TOP_DECLS = new HaskellElementType("TOP_DECLS");
    HaskellElementType TOP_DECL = new HaskellElementType("TOP_DECL");
    HaskellElementType TYPE_DECL = new HaskellElementType("TYPE_DECL");
    HaskellElementType DATA_DECL = new HaskellElementType("DATA_DECL");
    HaskellElementType NEWTYPE_DECL = new HaskellElementType("NEWTYPE_DECL");
    HaskellElementType CLASS_DECL = new HaskellElementType("CLASS_DECL");
    HaskellElementType INSTANCE_DECL = new HaskellElementType("INSTANCE_DECL");
    HaskellElementType DEFAULT_DECL = new HaskellElementType("DEFAULT_DECL");
    HaskellElementType DECLS = new HaskellElementType("DECLS");
    HaskellElementType DECL = new HaskellElementType("DECL");
    HaskellElementType C_DECLS = new HaskellElementType("C_DECLS");
    HaskellElementType C_DECL = new HaskellElementType("C_DECL");
    HaskellElementType I_DECLS = new HaskellElementType("I_DECLS");
    HaskellElementType I_DECL = new HaskellElementType("I_DECL");

    HaskellElementType BINDING = new HaskellElementType("BINDING");
    HaskellElementType S_BINDING = new HaskellElementType("S_BINDING");
    HaskellElementType GEN_DECL = new HaskellElementType("GEN_DECL");
    HaskellElementType TYPE_SIG = new HaskellElementType("TYPE_SIG");
    HaskellElementType FIXITY_DECL = new HaskellElementType("FIXITY_DECL");

    HaskellElementType OPS = new HaskellElementType("OPS");
    HaskellElementType VARS = new HaskellElementType("VARS");
    HaskellElementType FIXITY = new HaskellElementType("FIXITY");

    HaskellElementType TYPE = new HaskellElementType("TYPE");
    HaskellElementType B_TYPE = new HaskellElementType("B_TYPE");
    HaskellElementType A_TYPE = new HaskellElementType("A_TYPE");
    HaskellElementType G_TYPE_CON = new HaskellElementType("G_TYPE_CON");
    HaskellElementType CONTEXT = new HaskellElementType("CONTEXT");
    HaskellElementType CLASS = new HaskellElementType("CLASS");
    HaskellElementType S_CONTEXT = new HaskellElementType("S_CONTEXT");
    HaskellElementType SIMPLE_CLASS = new HaskellElementType("SIMPLE_CLASS");
    HaskellElementType SIMPLE_TYPE = new HaskellElementType("SIMPLE_TYPE");
    HaskellElementType CONSTRUCTORS = new HaskellElementType("CONSTRUCTORS");
    HaskellElementType CONSTRUCTOR = new HaskellElementType("CONSTRUCTOR");
    HaskellElementType NEW_CONSTRUCTOR = new HaskellElementType("NEW_CONSTRUCTOR");
    HaskellElementType FIELD_DECLARATION = new HaskellElementType("FIELD_DECLARATION");
    HaskellElementType DERIVING = new HaskellElementType("DERIVING");
    HaskellElementType DERIVING_CLASS = new HaskellElementType("DERIVING_CLASS");

    //TODO
    HaskellElementType INST = new HaskellElementType("INST");

    HaskellElementType FUN_LHS = new HaskellElementType("FUN_LHS");
    HaskellElementType RHS = new HaskellElementType("RHS");

    HaskellElementType GUARD_RHS = new HaskellElementType("GUARD_RHS");
    HaskellElementType GUARD = new HaskellElementType("GUARD");

    HaskellElementType EXP = new HaskellElementType("EXP");
    HaskellElementType LEFT_EXP = new HaskellElementType("LEFT_EXP");
    HaskellElementType RIGHT_EXP = new HaskellElementType("RIGHT_EXP");
    HaskellElementType LEXP9 = new HaskellElementType("LEXP9");
    HaskellElementType EXP10 = new HaskellElementType("EXP10");

    HaskellElementType LAMBDA_EXP = new HaskellElementType("LAMBDA_EXP");
    HaskellElementType LET_EXP = new HaskellElementType("LET_EXP");
    HaskellElementType IF_EXP = new HaskellElementType("IF_EXP");
    HaskellElementType CASE_EXP = new HaskellElementType("CASE_EXP");
    HaskellElementType NEGATE_EXP = new HaskellElementType("NEGATE_EXP");    
    HaskellElementType DO_EXP = new HaskellElementType("DO_EXP");
    HaskellElementType FUNCTION_EXP = new HaskellElementType("FUNCTION_EXP");
    HaskellElementType A_EXP = new HaskellElementType("A_EXP");
    HaskellElementType LEFT_SECTION = new HaskellElementType("LEFT_SECTION");
    HaskellElementType RIGHT_SECTION = new HaskellElementType("RIGHT_SECTION");

    HaskellElementType QUAL = new HaskellElementType("QUAL");
    HaskellElementType ALTS = new HaskellElementType("ALTS");
    HaskellElementType ALT = new HaskellElementType("ALT");

    HaskellElementType GUARD_PAT = new HaskellElementType("GUARD_PAT");

    HaskellElementType STATEMENTS = new HaskellElementType("STATEMENTS");
    HaskellElementType STATEMENT = new HaskellElementType("STATEMENT");
    HaskellElementType FBIND = new HaskellElementType("FBIND");

    HaskellElementType PAT = new HaskellElementType("PAT");
    HaskellElementType LEFT_PAT = new HaskellElementType("LEFT_PAT");
    HaskellElementType RIGHT_PAT = new HaskellElementType("RIGHT_PAT");
    HaskellElementType LPAT9 = new HaskellElementType("LPAT9");
    HaskellElementType PAT10 = new HaskellElementType("PAT10");

    HaskellElementType A_PAT = new HaskellElementType("A_PAT");
    HaskellElementType F_PAT = new HaskellElementType("F_PAT");
    HaskellElementType G_CON = new HaskellElementType("G_CON");

    HaskellElementType VAR = new HaskellElementType("VAR");
    HaskellElementType Q_VAR = new HaskellElementType("Q_VAR");
    HaskellElementType CON = new HaskellElementType("CON");
    HaskellElementType Q_CON = new HaskellElementType("Q_CON");
    HaskellElementType VAR_OP = new HaskellElementType("VAR_OP");
    HaskellElementType Q_VAR_OP = new HaskellElementType("Q_VAR_OP");
    HaskellElementType CON_OP = new HaskellElementType("CON_OP");
    HaskellElementType Q_CON_OP = new HaskellElementType("Q_CON_OP");
    HaskellElementType OP = new HaskellElementType("OP");
    HaskellElementType Q_OP = new HaskellElementType("Q_OP");
    HaskellElementType G_CON_SYM = new HaskellElementType("G_CON_SYM");

    HaskellElementType QUALIFIED_ID = new HaskellElementType("QUALIFIED_ID");
    HaskellElementType AS_ID = new HaskellElementType("AS_ID");
    HaskellElementType HIDING_ID = new HaskellElementType("HIDING_ID");

    HaskellElementType DOT_SYM = new HaskellElementType("DOT_SYM");
    HaskellElementType STRICT_SYM = new HaskellElementType("STRICT_SYM");

    HaskellElementType TUPLE_TYPE = new HaskellElementType("TUPLE_TYPE");
    HaskellElementType LIST_TYPE = new HaskellElementType("LIST_TYPE");
    HaskellElementType PAR_CON = new HaskellElementType("PAR_CON");

    HaskellElementType UNIT_TYPE = new HaskellElementType("UNIT_TYPE");
    HaskellElementType LIST_CON = new HaskellElementType("LIST_CON");
    HaskellElementType FUNCTION_CON = new HaskellElementType("FUNCTION_CON");
    HaskellElementType TUPLE_CON = new HaskellElementType("TUPLE_CON");

    HaskellElementType NEGATE_OP = new HaskellElementType("NEGATE_OP");

    HaskellElementType LITERAL = new HaskellElementType("LITERAL");

    HaskellElementType TYPE_VAR = new HaskellElementType("TYPE_VAR");
    HaskellElementType TYPE_CON = new HaskellElementType("TYPE_CON");
    HaskellElementType TYPE_CLASS = new HaskellElementType("TYPE_CLASS");
    HaskellElementType TYPE_CON_OR_CLASS = new HaskellElementType("TYPE_CON_OR_CLASS");
    HaskellElementType MOD_ID = new HaskellElementType("MOD_ID");

    HaskellElementType Q_VAR_ID = new HaskellElementType("Q_VAR_ID");
    HaskellElementType Q_CON_ID = new HaskellElementType("Q_CON_ID");
    HaskellElementType Q_TYPE_CON = new HaskellElementType("Q_TYPE_CON");
    HaskellElementType Q_TYPE_CLASS = new HaskellElementType("Q_TYPE_CLASS");
    HaskellElementType Q_TYPE_CON_OR_CLASS = new HaskellElementType("Q_TYPE_CON_OR_CLASS");
    HaskellElementType Q_VAR_SYM = new HaskellElementType("Q_VAR_SYM");
    HaskellElementType Q_CON_SYM = new HaskellElementType("Q_CON_SYM");

    //---------------------------------------------------------------------------------------------
    //
    //---------------------------------------------------------------------------------------------

    TokenSet WHITE_SPACES = TokenSet.create(WHITE_SPACE, TAB);
    TokenSet COMMENTS = TokenSet.create(COMMENT, NCOMMENT, PREPROCESSOR);
    TokenSet STRING_LITERALS = TokenSet.create(STRING, CHAR);
    TokenSet RESERVED_IDS = TokenSet.create(CASE_ID, CLASS_ID, DATA_ID, DEFAULT_ID, DERIVING_ID, DO_ID, ELSE_ID, IF_ID,
            IMPORT_ID, IN_ID, INFIX_ID, INFIXL_ID, INFIXR_ID, INSTANCE_ID, LET_ID, MODULE_ID, NEWTYPE_ID, OF_ID,
            THEN_ID, TYPE_ID, WHERE_ID, WILDCARD_ID);
    TokenSet LITERALS = TokenSet.create(INTEGER, FLOAT, CHAR, STRING);
}