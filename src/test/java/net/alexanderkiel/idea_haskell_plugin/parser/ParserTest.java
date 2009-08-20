package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import net.alexanderkiel.idea_haskell_plugin.BaseHaskellTest;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.MODULE;
import net.alexanderkiel.idea_haskell_plugin.psi.HaskellModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.FileFilter;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class ParserTest extends BaseHaskellTest {

    @Before
    public void setUp() {
        setupProject();
    }

    @Test
    public void testImport() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "import Data.Map");
    }

    @Test
    public void testTwoImports() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "import Data.Map\n" +
                "import Data.IntMap");
    }

    @Test
    public void testTwoImportsAndDeclaration() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "import Data.Map\n" +
                "import Data.IntMap\n" +
                "f x = y");
    }

    @Test
    public void testData1() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "data Data a = Con1 a | Con2");
    }

    @Test
    public void testData2() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "data Data a = Con { f1 :: Type a, f2 :: !Int }");
    }

    @Test
    public void testFunction() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "f x = y");
    }

    @Test
    public void testFunctionOp() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "f x = 1 + x");
    }

    @Test
    public void testFunctionApplication() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "f x = g x");
    }

    @Test
    public void testFunctionApplication2() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "f x y = g x y");
    }

    @Test
    public void testTwoDeclarations() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "f x = x + 1\n" +
                "g x = x + 2");
    }

    @Test
    public void testLet() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "f a b = let { x = a; y = b } in x + y");
    }

    @Test
    public void testArithmeticSequence() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "x = [1..]");
        parseWithNoTimeout("module Test where\n" +
                "x = [1..2]");
        parseWithNoTimeout("module Test where\n" +
                "x = [1,2..]");
        parseWithNoTimeout("module Test where\n" +
                "x = [1,2..3]");
        parseWithNoTimeout("module Test where\n" +
                "x = map (\\x -> 30) [0..]");
    }

    @Test
    public void testListComprehension() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "x = [x * x | x <- [1..]]");
        parseWithNoTimeout("module Test where\n" +
                "x = [x * x | let { start = 1 }, x <- [start..]]");
        parseWithNoTimeout("module Test where\n" +
                "x = [x * x | let { start = 1 }, x <- [start..], x > 2]");
    }

    @Test
    public void testFunctionWithWhere() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "f x = y where\n" +
                "    y = 1");
    }

    @Test
    public void testFunctionFib() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "fib :: Int -> Int\n" +
                "fib 0 = 0\n" +
                "fib 1 = 1\n" +
                "fib n = fib (n - 1) + fib (n - 2)");
    }

    @Test
    public void testFunctionParMap() throws Throwable {
        parseWithNoTimeout("module Test where\n" +
                "parMap :: (a -> b) -> [a] -> [b]\n" +
                "parMap f []     = []\n" +
                "parMap f (x:xs) = y `par` y:ys where\n" +
                "    y  = f x\n" +
                "    ys = parMap f xs");
    }

    @Test
    public void testComment() throws Throwable {
        parseWithNoTimeout(readFile(new File("Comment.hs")));
    }

    @Test
    public void testInstance() throws Throwable {
        parseWithNoTimeout(readFile(new File("Instance.hs")));
    }
    
    @Test
    public void testClass() throws Throwable {
        parseWithNoTimeout(readFile(new File("Class.hs")));
    }

    @Test
    public void testGuard() throws Throwable {
        parseWithNoTimeout(readFile(new File("Guard.hs")));
    }
    
    @Test
    public void testIf() throws Throwable {
        parseWithNoTimeout(readFile(new File("If.hs")));
    }
    
    @Test
    public void testCase() throws Throwable {
        parseWithNoTimeout(readFile(new File("Case.hs")));
    }

    @Test
    public void testParFib() throws Throwable {
        parseWithNoTimeout(readFile(new File("ParFib.hs")));
    }

    @Test
    public void testControllerUtils() throws Throwable {
        parseWithNoTimeout(readFile(new File("ControllerUtils.hs")));
    }

    @Test
    public void testCommonTest() throws Throwable {
        parseWithNoTimeout(readFile(new File("CommonTest.hs")));
    }

    //---------------------------------------------------------------------------------------------
    // Helper Methods
    //---------------------------------------------------------------------------------------------

    private HaskellModule getHaskellModule(PsiFile psiFile) {
        PsiElement[] elements = psiFile.getChildren();
        assertEquals(1, elements.length);
        assertTrue(elements[0] instanceof HaskellModule);
        return (HaskellModule) elements[0];
    }

    private ASTNode getModuleNode(PsiFile psiFile) {
        ASTNode node = psiFile.getNode().getFirstChildNode();
        assertEquals(MODULE, node.getElementType());
        return node;
    }
}
