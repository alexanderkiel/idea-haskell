package net.alexanderkiel.idea_haskell_plugin;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiRecursiveElementVisitor;
import com.intellij.testFramework.fixtures.IdeaProjectTestFixture;
import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory;
import com.intellij.testFramework.fixtures.TestFixtureBuilder;
import static junit.framework.Assert.assertEquals;
import org.junit.Assert;

import java.io.*;
import java.util.concurrent.*;


/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class BaseHaskellTest {

    protected Project myProject;
    protected Module myModule;
    protected IdeaProjectTestFixture myFixture;

    private static final Logger LOG = Logger.getInstance(BaseHaskellTest.class.getName());
    private static final byte[] BUFF = new byte[8000];
    protected ExecutorService executorService;

    protected void setupProject() {
        executorService = Executors.newSingleThreadExecutor();
        myFixture = createFixture();

        try {
            myFixture.setUp();
        }
        catch (Exception e) {
            LOG.error(e);
        }
        myModule = myFixture.getModule();
        myProject = myModule.getProject();
    }

    protected IdeaProjectTestFixture createFixture() {
        TestFixtureBuilder<IdeaProjectTestFixture> fixtureBuilder =
                IdeaTestFixtureFactory.getFixtureFactory().createLightFixtureBuilder();
        return fixtureBuilder.getFixture();
    }

    public static String readFile(File myTestFile) throws IOException {
        String content = new String(FileUtil.loadFileText(myTestFile));
        Assert.assertNotNull(content);

        Assert.assertTrue("No data found in source file", content.length() > 0);

        return content;
    }

    /*protected void lexerAllInDir(File dir, List<LexerResult> results) throws Throwable {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                lexerAllInDir(file, results);
            } else {
                String fileName = file.getName();
                if (fileName.endsWith(".fan") && !fileName.equals("gamma.fan")) {
                    try {
                        List<ParserBlock> list = lex(BaseFanTest.readFile(file).toCharArray());
                        checkNoBadCharacters(list);
                        LexerResult result = new LexerResult(file.getPath(), list.size());
                        for (ParserBlock block : list) {
                            Integer t = result.totals.get(block.type);
                            if (t == null) {
                                result.totals.put(block.type, 1);
                            } else {
                                result.totals.put(block.type, t + 1);
                            }
                        }
                        results.add(result);
                    } catch (Throwable throwable) {
                        System.err.println("Error parsing " + file.getAbsolutePath());
                        results.add(new LexerResult(throwable.getMessage(), ResultStatusCode.TIMEOUT, file.getPath()));
                    }
                }
            }
        }
    }*/

    /*protected List<ParserBlock> lex(char[] buff) throws Throwable {
        FanHighlightingLexer lexer = new FanHighlightingLexer();
        lexer.start(buff);
        List<ParserBlock> blocks = new ArrayList<ParserBlock>();
        ParserBlock block;
        while (lexer.getState() == 0) {
            block = new ParserBlock();
            block.type = lexer.getTokenType();
            if (block.type == null) {
                break;
            }
            block.start = lexer.getTokenStart();
            block.end = lexer.getTokenEnd();
            blocks.add(block);
            try {
                lexer.advance();
            } catch (Throwable e) {
                for (int i = Math.max(blocks.size() - 30, 0); i < blocks.size(); i++) {
                    ParserBlock rBlock = blocks.get(i);
                    System.out.println(rBlock.toString() + " " + getTextBlock(buff, rBlock));
                }
                e.printStackTrace(System.out);
                junit.framework.Assert.assertTrue("Got Exception parsing " + e.getMessage(), false);
            }
        }
        return blocks;
    }*/

    /*protected String getTextBlock(char[] buff, ParserBlock rBlock) {
        return new String(buff, rBlock.start, rBlock.end - rBlock.start);
    }*/

    public String getResourceCharArray(String resourceName) throws IOException {
        InputStream is = getClass().getResourceAsStream(resourceName);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int nbRead;
        while ((nbRead = is.read(BUFF)) > 0) {
            bos.write(BUFF, 0, nbRead);
        }
        return bos.toString();
    }

    /*protected void checkNoBadCharacters(List<ParserBlock> list) {
        for (ParserBlock block : list) {
            junit.framework.Assert.assertNotSame(block.type, FanTokenTypes.BAD_CHARACTER);
        }
    }*/

    /*protected void parseAllInDir(File dir, FileFilter fileFilter, List<ParsingResult> results) throws Throwable {
        if (fileFilter == null) {
            fileFilter = new FileFilter() {
                public boolean accept(File pathname) {
                    return true;
                }
            };
        }
        File[] files = dir.listFiles(fileFilter);
        for (final File file : files) {
            if (file.isDirectory()) {
                parseAllInDir(file, fileFilter, results);
            } else {
                String fileName = file.getName();
                if (fileName.endsWith(".fan") && !fileName.equals("gamma.fan")) {
                    try {
                        System.out.println("Parsing file: " + file.getAbsolutePath());
                        PsiFile psiFile = parse(readFile(file));
                        if (psiFile == null) {
                            System.out.println("[Error] Failed parsing file (timeout): " + file.getAbsolutePath());
                            results.add(new ParsingResult(ResultStatusCode.TIMEOUT, file.getPath(), "timeout", null));
                        } else {
                            int errors = visitErrorElements(psiFile);
                            System.out.println("Parsed file: " + file.getAbsolutePath());
                            if (errors == 0) {
                                results.add(new ParsingResult(ResultStatusCode.OK, file.getPath(), "", psiFile));
                            } else {
                                results.add(
                                        new ParsingResult(ResultStatusCode.PARSING_ERROR, file.getPath(), "", psiFile));
                            }
                        }
                    } catch (Throwable throwable) {
                        String msg = throwable.getMessage();
                        System.out.println("[Error] Error parsing " + file.getAbsolutePath() + ": " + msg);
                        int firstReturn = msg.indexOf("\n");
                        if (firstReturn != -1) {
                            msg = msg.substring(0, firstReturn);
                        }
                        results.add(new ParsingResult(ResultStatusCode.EXCEPTION, file.getPath(), msg, null));
                    }
                }
            }
        }
    }*/

    protected int visitErrorElements(PsiFile psiFile) {
        PsiRecursiveErrorElementVisitor recursiveErrorElementVisitor = new PsiRecursiveErrorElementVisitor();
        psiFile.accept(recursiveErrorElementVisitor);
        return recursiveErrorElementVisitor.getErrors();
    }

    public PsiFile parse(final String text) throws Throwable {
        Callable<PsiFile> testBlock = new Callable<PsiFile>() {
            public PsiFile call() throws Exception {
                try {
                    return TestUtil.createPseudoPhysicalFanFile(myProject, text);
                } catch (Throwable throwable) {
                    throw new Exception(throwable);
                }
            }
        };

        Future<PsiFile> fileFuture = executorService.submit(testBlock);
        try {
            return fileFuture.get(getTimeoutSecs(), TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            return null;
        } finally {
            if (fileFuture != null && !fileFuture.isDone()) {
                fileFuture.cancel(true);
            }
        }
    }

    protected int getTimeoutSecs() {
        return 20;
    }

    public PsiFile parseWithNoTimeout(final String text) throws Throwable {
        PsiFile psiFile = TestUtil.createPseudoPhysicalFanFile(myProject, text);
        assertEquals(0, visitErrorElements(psiFile));
        return psiFile;
    }

    private class PsiRecursiveErrorElementVisitor extends PsiRecursiveElementVisitor {

        private int errors = 0;

        @Override
        public void visitErrorElement(PsiErrorElement element) {
            errors++;
            String textFile = element.getContainingFile().getText();
            int offset = element.getTextOffset();
            System.out.println("\tError element: " + element.getErrorDescription() +
                    " for pos " + offset +
                    " around '" +
                    textFile.substring(Math.max(0, offset - 20), Math.min(textFile.length(), offset + 20)) +
                    "'");
        }

        public int getErrors() {
            return errors;
        }
    }
}
