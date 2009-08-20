package net.alexanderkiel.idea_haskell_plugin.parser;

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.MATCH;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.FAIL;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class GracefulAdapter implements GracefulRule {

    private final Rule rule;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public GracefulAdapter(Rule rule) {
        this.rule = rule;
    }

    //---------------------------------------------------------------------------------------------
    // GracefulRule Implementation
    //---------------------------------------------------------------------------------------------

    public GracefulResult apply(@NotNull PsiBuilder builder) {
        return rule.apply(builder) ? MATCH : FAIL;
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return rule.toString();
    }
}
