package net.alexanderkiel.idea_haskell_plugin.lexer;

import com.intellij.lexer.Lexer;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import com.intellij.psi.tree.IElementType;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import org.jetbrains.annotations.NotNull;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellLayoutLexerTest {

    private Lexer lexer;

    @Before
    public void setUp() {
        lexer = new HaskellLayoutLexer(new HaskellCommentLexer(new HaskellIncrementalLexer()));
    }

    @Test
    public void testLetLayout() {
        initLex("pair = let y1 = f x\n" +
                "           y2 = g x\n" +
                "       in (y1, y2)");

        // Line 1
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "pair");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(LET_ID, "let");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "y1");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "f");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();

        // Line 2
        assertToken(WHITE_SPACE, "           ");
        lexer.advance();
        assertToken(SEMICOLON, "");
        lexer.advance();
        assertToken(VAR_ID, "y2");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "g");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();

        // Line 3
        assertToken(WHITE_SPACE, "       ");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();
        assertToken(IN_ID, "in");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(OPEN_PAR, "(");
        lexer.advance();
        assertToken(VAR_ID, "y1");
        lexer.advance();
        assertToken(COMMA, ",");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "y2");
        lexer.advance();
        assertToken(CLOSE_PAR, ")");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();

        assertEof();
    }

    @Test
    public void testModuleStart() {
        initLex("module Test where\n" +
                "test = 1");

        // Line 1
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
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "test");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(INTEGER, "1");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();

        assertEof();
    }

    @Test
    public void testModuleStartAndWhere() {
        initLex("module Test where\n" +
                "test = x where\n" +
                "    x = 1");

        // Line 1
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
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "test");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(WHERE_ID, "where");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();
        assertToken(WHITE_SPACE, "    ");
        lexer.advance();
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(INTEGER, "1");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();

        assertEof();
    }

    @Test
    public void testModuleImportAndTop() {
        initLex("module Test where\n" +
                "import Lib\n" +
                "test :: Int\n" +
                "test = x");

        // Line 1
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
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(IMPORT_ID, "import");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(CON_ID, "Lib");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();
        assertToken(SEMICOLON, "");
        lexer.advance();
        assertToken(VAR_ID, "test");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(DOUBLE_COLON_OP, "::");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(CON_ID, "Int");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();
        assertToken(SEMICOLON, "");
        lexer.advance();
        assertToken(VAR_ID, "test");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();

        assertEof();
    }

    @Test
    public void testTopDeclWithComment() {
        initLex("f x = y -- f x = y\n" +
                "f x = y -- f x = y\n");

        // Line 1
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "f");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "y");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(COMMENT, "-- f x = y\n");
        lexer.advance();
        assertToken(SEMICOLON, "");
        lexer.advance();
        assertToken(VAR_ID, "f");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "y");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(COMMENT, "-- f x = y\n");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();

        assertEof();
    }

    @Test
    public void testStartingLineComment() {
        initLex("-- Comment\n" +
                "module Test where\n" +
                "f x = y");

        // Line 1
        assertToken(COMMENT, "-- Comment\n");
        lexer.advance();

        // Line 2
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
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();
        assertToken(OPEN_BRACE, "");
        lexer.advance();

        // Line 3
        assertToken(VAR_ID, "f");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "y");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();

        assertEof();
    }

    @Test
    public void testStartingComment() {
        initLex("{-# LANGUAGE FlexibleInstances #-}\n" +
                "module Test where\n" +
                "f x = y");

        // Line 1        
        assertToken(NCOMMENT, "{-# LANGUAGE FlexibleInstances #-}");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();

        // Line 2
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
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();
        assertToken(OPEN_BRACE, "");
        lexer.advance();

        // Line 3
        assertToken(VAR_ID, "f");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "y");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();

        assertEof();
    }

    @Test
    public void testLetInListComprehension() {
        initLex("x = [x * x | let { start = 1; end = 10 }, x <- [start..end]]");

        // Line 1
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(OPEN_BRACKET, "[");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_SYM, "*");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(BAR_OP, "|");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(LET_ID, "let");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(OPEN_BRACE, "{");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "start");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(INTEGER, "1");
        lexer.advance();
        assertToken(SEMICOLON, ";");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "end");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(INTEGER, "10");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(CLOSE_BRACE, "}");
        lexer.advance();
        assertToken(COMMA, ",");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(GENERATOR_OP, "<-");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(OPEN_BRACKET, "[");
        lexer.advance();
        assertToken(VAR_ID, "start");
        lexer.advance();
        assertToken(DOUBLE_DOT_OP, "..");
        lexer.advance();
        assertToken(VAR_ID, "end");
        lexer.advance();
        assertToken(CLOSE_BRACKET, "]");
        lexer.advance();
        assertToken(CLOSE_BRACKET, "]");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();

        assertEof();
    }

    @Test
    public void testTopContextAfterNestedContext() {
        initLex("f x = y where\n" +
                "    y = 1\n" +
                "g x = 2\n");

        // Line 1
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "f");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "y");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(WHERE_ID, "where");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();

        // Line 2
        assertToken(WHITE_SPACE, "    ");
        lexer.advance();
        assertToken(OPEN_BRACE, "");
        lexer.advance();        
        assertToken(VAR_ID, "y");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(INTEGER, "1");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();

        // Line 3
        assertToken(CLOSE_BRACE, "");
        lexer.advance();
        assertToken(SEMICOLON, "");
        lexer.advance();
        assertToken(VAR_ID, "g");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(INTEGER, "2");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();

        assertEof();
    }

    @Test
    public void testSemicolonBeforeContextOpeningKeyword() {
        initLex("do a\n" +
                "   let b");

        // Line 1
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(DO_ID, "do");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "a");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();
        assertToken(WHITE_SPACE, "   ");
        lexer.advance();
        assertToken(SEMICOLON, "");
        lexer.advance();
        assertToken(LET_ID, "let");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "b");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();
        
        assertEof();
    }

    @Test
    public void test1() {
        initLex("x =\n" +
                "    let y = 1\n" +
                "    in y");

        // Line 1
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();

        //Line 2
        assertToken(WHITE_SPACE, "    ");
        lexer.advance();
        assertToken(LET_ID, "let");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "y");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(INTEGER, "1");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();

        // Line 3
        assertToken(WHITE_SPACE, "    ");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();
        assertToken(IN_ID, "in");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "y");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();

        assertEof();
    }

    /**
     * Tests the correct application of a tab width of 8 according the Haskell 98 Report page 131.
     */
    @Test
    public void testTabWidth() {
        initLex("pair = let y1 = f x\n" +
                "\t   y2 = g x\n" +
                "       in (y1, y2)");

        // Line 1
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "pair");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(LET_ID, "let");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(OPEN_BRACE, "");
        lexer.advance();
        assertToken(VAR_ID, "y1");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "f");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();

        // Line 2
        assertToken(TAB, "\t");
        lexer.advance();
        assertToken(WHITE_SPACE, "   ");
        lexer.advance();
        assertToken(SEMICOLON, "");
        lexer.advance();
        assertToken(VAR_ID, "y2");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(EQUAL_OP, "=");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "g");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "x");
        lexer.advance();
        assertToken(WHITE_SPACE, "\n");
        lexer.advance();

        // Line 3
        assertToken(WHITE_SPACE, "       ");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();
        assertToken(IN_ID, "in");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(OPEN_PAR, "(");
        lexer.advance();
        assertToken(VAR_ID, "y1");
        lexer.advance();
        assertToken(COMMA, ",");
        lexer.advance();
        assertToken(WHITE_SPACE, " ");
        lexer.advance();
        assertToken(VAR_ID, "y2");
        lexer.advance();
        assertToken(CLOSE_PAR, ")");
        lexer.advance();
        assertToken(CLOSE_BRACE, "");
        lexer.advance();

        assertEof();
    }

    //---------------------------------------------------------------------------------------------
    // Helper Methods
    //---------------------------------------------------------------------------------------------

    private void initLex(@NotNull String str) {
        lexer.start(str, 0, str.length(), 0);
    }

    private String getTokenText() {
        return lexer.getBufferSequence().subSequence(lexer.getTokenStart(), lexer.getTokenEnd()).toString();
    }

    private void assertToken(@NotNull IElementType tokenType, @NotNull String tokenContent) {
        assertSame("token type", tokenType, lexer.getTokenType());
        assertEquals("token content", tokenContent, getTokenText());
    }

    private void assertEof() {
        assertNull("EOF " + lexer.getTokenType(), lexer.getTokenType());
    }
}
