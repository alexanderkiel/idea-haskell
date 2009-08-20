package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Skipping.skipUntil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class GracefulBracketedRule implements GracefulRule {

    private final GracefulRule nestedRule;
    private final BracketPair bracketPair;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public GracefulBracketedRule(GracefulRule nestedRule, BracketPair bracketPair) {
        this.nestedRule = nestedRule;
        this.bracketPair = bracketPair;
    }

    //---------------------------------------------------------------------------------------------
    // GracefulRule Implementation
    //---------------------------------------------------------------------------------------------

    /**
     * Tries to apply this rule to the token stream.
     *
     * @param builder the PSI builder to use
     * @return a result (please have a look into the {@link GracefulResult result documentation})
     */
    public GracefulResult apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (!parse(builder, bracketPair.getOpenBracket())) {
            marker.drop();
            return FAIL;
        }

        GracefulResult result = nestedRule.apply(builder);
        if (result == FAIL) {
            marker.rollbackTo();
            return FAIL;
        } else if (result == RECOVER) {
            skipUntil(builder, bracketPair.getCloseBracket());
        }

        if (!parse(builder, bracketPair.getCloseBracket())) {
            builder.error("'" + bracketPair.getCloseBracket().getBracketSymbol() + "' expected");
            marker.drop();
            return RECOVER;
        }

        marker.drop();
        return MATCH;
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return bracketPair.getOpenBracket().getBracketSymbol() + " " + nestedRule + " " +
                bracketPair.getCloseBracket().getBracketSymbol();
    }
}
