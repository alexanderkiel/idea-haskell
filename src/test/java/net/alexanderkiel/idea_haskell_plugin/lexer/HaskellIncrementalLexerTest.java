package net.alexanderkiel.idea_haskell_plugin.lexer;

import com.intellij.lexer.Lexer;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import com.intellij.psi.tree.IElementType;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.HaskellKeywordElementType;
import org.jetbrains.annotations.NotNull;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellIncrementalLexerTest {

    private Lexer lexer;

    @Before
    public void setUp() {
        lexer = new HaskellIncrementalLexer();
    }

    @Test
    public void testReservedIds() {
        testReservedId(CASE_ID, "case");
        testReservedId(CLASS_ID, "class");
        testReservedId(DATA_ID, "data");
        testReservedId(DEFAULT_ID, "default");
        testReservedId(DERIVING_ID, "deriving");
        testReservedId(DO_ID, "do");
        testReservedId(ELSE_ID, "else");
        testReservedId(IF_ID, "if");
        testReservedId(IMPORT_ID, "import");
        testReservedId(IN_ID, "in");
        testReservedId(INFIX_ID, "infix");
        testReservedId(INFIXL_ID, "infixl");
        testReservedId(INFIXR_ID, "infixr");
        testReservedId(INSTANCE_ID, "instance");
        testReservedId(LET_ID, "let");
        testReservedId(MODULE_ID, "module");
        testReservedId(NEWTYPE_ID, "newtype");
        testReservedId(OF_ID, "of");
        testReservedId(THEN_ID, "then");
        testReservedId(TYPE_ID, "type");
        testReservedId(WHERE_ID, "where");
        testReservedId(WILDCARD_ID, "_");
    }

    private void testReservedId(HaskellKeywordElementType tokenType, String name) {
        assertEquals(name, tokenType.getKeywordName());
        initLex(name);
        assertToken(tokenType, name);
        lexer.advance();
        assertEof();
    }

    @Test
    public void testModuleDeclaration() {
        initLex("module Test where");
        assertToken(MODULE_ID, "module");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(CON_ID, "Test");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(WHERE_ID, "where");
        lexer.advance();
        assertEof();
    }

    @Test
    public void testImportDeclaration() {
        initLex("import Control.Parallel (par, pseq)");
        assertToken(IMPORT_ID, "import");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(CON_ID, "Control");
        lexer.advance();
        assertToken(VAR_SYM, ".");
        lexer.advance();
        assertToken(CON_ID, "Parallel");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(OPEN_PAR, "(");
        lexer.advance();
        assertToken(VAR_ID, "par");
        lexer.advance();
        assertToken(COMMA, ",");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "pseq");
        lexer.advance();
        assertToken(CLOSE_PAR, ")");
        lexer.advance();
        assertEof();
    }

    @Test
    public void testComment1() {
        initLex("------------------------------------------------------------------\n" +
                "-- | Module Test\n" +
                "--\n" +
                "\n" +
                "module Test where\n"
        );
        assertToken(COMMENT, "------------------------------------------------------------------\n");
        lexer.advance();
        assertToken(COMMENT, "-- | Module Test\n");
        lexer.advance();
        assertToken(COMMENT, "--\n");
        lexer.advance();
        assertToken(NEWLINE, "\n");
        lexer.advance();
        assertToken(MODULE_ID, "module");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(CON_ID, "Test");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(WHERE_ID, "where");
        lexer.advance();
        assertToken(NEWLINE, "\n");
        lexer.advance();
        assertEof();
    }

    @Test
    public void testVarId() {
        initLex("casevar");
        assertToken(VAR_ID, "casevar");
        lexer.advance();
        assertEof();
    }

    @Test
    public void testSymbolMinus() {
        initLex("-");
        assertToken(VAR_SYM, "-");
        lexer.advance();
        assertEof();
    }

    @Test
    public void testPreprocessor() {
        initLex("#if foo\n");

        assertToken(PREPROCESSOR, "#if foo\n");        
        lexer.advance();

        assertEof();
    }

    //---------------------------------------------------------------------------------------------
    // Helper Methods
    //---------------------------------------------------------------------------------------------

    private void initLex(@NotNull String str) {
        lexer.start(str, 0, str.length(), 0);
    }

    private void assertToken(@NotNull IElementType tokenType, @NotNull String tokenContent) {
        assertSame("token type", tokenType, lexer.getTokenType());
        assertEquals("token content", tokenContent, lexer.getBufferSequence().subSequence(
                lexer.getTokenStart(), lexer.getTokenEnd()).toString());
    }

    private void assertEof() {
        assertNull(lexer.getTokenType());
    }
}
