package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.*;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class GracefulPlusRule extends AbstractHigherOrderGracefulRule {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public GracefulPlusRule(@NotNull GracefulRule nestedRule) {
        super(null, nestedRule);
    }

    public GracefulResult apply(@NotNull PsiBuilder builder) {

        // I can't recover so return on FAIL and RECOVER
        GracefulResult result = nestedRule.apply(builder);
        if (result != MATCH) {
            return result;
        }

        while (result == MATCH) {
            result = nestedRule.apply(builder);
        }

        // Return MATCH at FAIL because this is the normal way to terminate the while loop
        if (result == FAIL) {
            return MATCH;
        } else {
            return result;
        }
    }
}