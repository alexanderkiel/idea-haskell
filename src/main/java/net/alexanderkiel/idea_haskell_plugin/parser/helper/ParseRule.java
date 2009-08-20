package net.alexanderkiel.idea_haskell_plugin.parser.helper;

import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.PsiBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
* @version $Id$
*/
public class ParseRule implements Rule {

    private final IElementType tokenType;

    public ParseRule(IElementType tokenType) {
        this.tokenType = tokenType;
    }

    public boolean apply(@NotNull PsiBuilder builder) {
        return parse(builder, tokenType);
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return tokenType.toString();
    }
}
