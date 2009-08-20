package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.MATCH;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.RECOVER;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class GracefulOptionRule extends AbstractHigherOrderGracefulRule {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public GracefulOptionRule(@NotNull GracefulRule nestedRule) {
        super(null, nestedRule);
    }

    public GracefulResult apply(@NotNull PsiBuilder builder) {
        GracefulResult result = nestedRule.apply(builder);
        switch (result) {

            // All right if the nested rule matches
            case MATCH:
                return MATCH;

            // No problem if the nested rule fails, its optional
            case FAIL:
                return MATCH;

            // Just request recovery if the nested rule does so
            case RECOVER:
                return RECOVER;

            default:
                throw new IllegalStateException("unknown GracefulResult: " + result);
        }
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return nestedRule + "?";
    }
}