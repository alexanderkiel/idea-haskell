package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class BracketedSequenceRule implements Rule {

    private final IElementType resultType;
    private final IElementType openToken;
    private final IElementType closeToken;
    private final SeparatedSequenceRule separatedSequenceRule;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public BracketedSequenceRule(@NotNull Rule nestedRule,
                                 @NotNull IElementType openToken, @NotNull IElementType closeToken,
                                 @NotNull IElementType separatorToken, int minOccurrence) {
        this.resultType = null;
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.separatedSequenceRule = new SeparatedSequenceRule(nestedRule, separatorToken, minOccurrence);
    }

    public BracketedSequenceRule(@NotNull IElementType resultType, @NotNull Rule nestedRule,
                                 @NotNull IElementType openToken, @NotNull IElementType closeToken,
                                 @NotNull IElementType separatorToken, int minOccurrence) {
        this.resultType = resultType;
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.separatedSequenceRule = new SeparatedSequenceRule(nestedRule, separatorToken, minOccurrence);
    }

    //---------------------------------------------------------------------------------------------
    // Rule Implementation
    //---------------------------------------------------------------------------------------------

    /**
     * {@link #openToken (} nested1 , ... , nestedn {@link #closeToken )} ( n >= k )
     *
     * @param builder the PSI builder to use
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (!parse(builder, openToken)) {
            marker.rollbackTo();
            return false;
        }

        if (!separatedSequenceRule.apply(builder)) {
            marker.rollbackTo();
            return false;
        }

        if (parse(builder, closeToken)) {
            finishMarker(marker);
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
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