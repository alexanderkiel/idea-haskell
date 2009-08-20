package net.alexanderkiel.idea_haskell_plugin.parser;

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.MATCH;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.FAIL;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.RECOVER;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class GracefulDecorator implements GracefulRule {

    private final GracefulRule rule;
    private final String error;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public GracefulDecorator(@NotNull GracefulRule rule, @NotNull String error) {
        this.rule = rule;
        this.error = error;
    }

    //---------------------------------------------------------------------------------------------
    // GracefulRule Implementation
    //---------------------------------------------------------------------------------------------

    public GracefulResult apply(@NotNull PsiBuilder builder) {
        GracefulResult result = rule.apply(builder);
        if (result == FAIL) {
            builder.error(error);
            return RECOVER;
        } else {
            return result;
        }
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return rule.toString();
    }
}