package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import static net.alexanderkiel.idea_haskell_plugin.parser.GracefulResult.*;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Skipping.skipUntil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class GracefulBracketedSequenceRule implements GracefulRule {

    private final IElementType resultType;
    private final BracketPair bracketPair;    
    private final GracefulSeparatedSequenceRule separatedSequenceRule;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public GracefulBracketedSequenceRule(@NotNull GracefulRule nestedRule, @NotNull BracketPair bracketPair,
                                         @NotNull IElementType separatorToken, int minOccurrence) {
        this.resultType = null;
        this.bracketPair = bracketPair;
        this.separatedSequenceRule = new GracefulSeparatedSequenceRule(nestedRule, separatorToken, minOccurrence);
    }

    public GracefulBracketedSequenceRule(@NotNull IElementType resultType, @NotNull GracefulRule nestedRule,
                                         @NotNull BracketPair bracketPair, @NotNull IElementType separatorToken,
                                         int minOccurrence) {
        this.resultType = resultType;
        this.bracketPair = bracketPair;
        this.separatedSequenceRule = new GracefulSeparatedSequenceRule(nestedRule, separatorToken, minOccurrence);
    }

    //---------------------------------------------------------------------------------------------
    // Rule Implementation
    //---------------------------------------------------------------------------------------------

    /**
     * ( nested1 , ... , nestedn ) ( n >= k )
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

        GracefulResult result = separatedSequenceRule.apply(builder);
        if (result == FAIL) {
            marker.rollbackTo();
            return FAIL;
        } else if (result == RECOVER) {
            skipUntil(builder, bracketPair.getCloseBracket());
        }

        if (parse(builder, bracketPair.getCloseBracket())) {
            finishMarker(marker);
            return MATCH;
        } else {
            builder.error("" + bracketPair.getCloseBracket().getBracketSymbol() + " expected");
            marker.drop();
            return RECOVER;
        }
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return bracketPair.getOpenBracket().getBracketSymbol() + " " + separatedSequenceRule + " " +
                bracketPair.getCloseBracket().getBracketSymbol();
    }

    //---------------------------------------------------------------------------------------------
    // Helper Methods
    //---------------------------------------------------------------------------------------------

    private void finishMarker(@NotNull PsiBuilder.Marker marker) {
        if (resultType != null) {
            marker.done(resultType);
        } else {
            marker.drop();
        }
    }
}