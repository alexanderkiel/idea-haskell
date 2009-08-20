package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageUtil;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import net.alexanderkiel.idea_haskell_plugin.HaskellFileTypeManager;
import net.alexanderkiel.idea_haskell_plugin.lexer.HaskellIncrementalLexer;
import net.alexanderkiel.idea_haskell_plugin.lexer.HaskellLayoutLexer;
import net.alexanderkiel.idea_haskell_plugin.lexer.HaskellCommentLexer;
import net.alexanderkiel.idea_haskell_plugin.psi.impl.*;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class HaskellParserDefinition implements ParserDefinition {

    @NotNull
    public Lexer createLexer(Project project) {
        return new HaskellLayoutLexer(new HaskellCommentLexer(new HaskellIncrementalLexer()));        
    }

    public PsiParser createParser(Project project) {
        return new HaskellParser();
    }

    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return STRING_LITERALS;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        IElementType type = node.getElementType();
        if (type == MODULE) {
            return new HaskellModuleImpl(node);
        } else if (type == MOD_ID) {
            return new HaskellModuleIdImpl(node);
        } else if (type == IMP_DECL) {
            return new HaskellImportDeclarationImpl(node);
        } else {
            return new HaskellWrapperElementImpl(node);
        }
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new HaskellPsiFileImpl(viewProvider, HaskellFileTypeManager.FILE_TYPE);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        final Lexer lexer = createLexer(left.getPsi().getProject());
        return LanguageUtil.canStickTokensTogetherByLexer(left, right, lexer, 0);
    }
}
