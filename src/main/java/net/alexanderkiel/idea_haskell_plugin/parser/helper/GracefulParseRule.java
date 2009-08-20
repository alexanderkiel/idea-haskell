package net.alexanderkiel.idea_haskell_plugin.parser.helper;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.MATCH;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.RECOVER;
import net.alexanderkiel.idea_haskell_plugin.parser.GracefulRule;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class GracefulParseRule implements GracefulRule {

    private final IElementType tokenType;
    private final String error;

    public GracefulParseRule(@NotNull IElementType tokenType, @NotNull String error) {
        this.tokenType = tokenType;
        this.error = error;
    }

    public GracefulResult apply(@NotNull PsiBuilder builder) {
        if (!parse(builder, tokenType)) {
            builder.error(error);
            return RECOVER;
        } else {
            return MATCH;
        }
    }
}