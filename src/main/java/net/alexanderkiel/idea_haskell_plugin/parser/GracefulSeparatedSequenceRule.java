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
public class GracefulSeparatedSequenceRule extends AbstractHigherOrderGracefulRule {

    private final IElementType separatorToken;
    private final int minOccurrence;

    //---------------------------------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------------------------------

    public GracefulSeparatedSequenceRule(@NotNull GracefulRule nestedRule, @NotNull IElementType separatorToken,
                                         int minOccurrence) {
        super(null, nestedRule);
        this.separatorToken = separatorToken;
        this.minOccurrence = minOccurrence;
    }

    public GracefulSeparatedSequenceRule(@NotNull IElementType resultType, @NotNull GracefulRule nestedRule,
                                         @NotNull IElementType separatorToken, int minOccurrence) {
        super(resultType, nestedRule);
        this.separatorToken = separatorToken;
        this.minOccurrence = minOccurrence;
    }

    //---------------------------------------------------------------------------------------------
    // Rule Implementation
    //---------------------------------------------------------------------------------------------

    /**
     * nested1 {@link #separatorToken ,} ... {@link #separatorToken ,} nestedn ( n >= {@link #minOccurrence k} )
     *
     * @param builder the PSI builder to use
     * @return a result (please have a look into the {@link GracefulResult result documentation})
     */
    public GracefulResult apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        // Handle first occurrence
        GracefulResult firstResult = nestedRule.apply(builder);
        if (firstResult == FAIL) {

            // The first application of the nested rule failed already.
            // Lets get out here.
            return exitAfterFirstApplication(marker);

        } else if (firstResult == RECOVER) {

            // The nested rule asks to recover. So we can skip until the separator
            skipUntil(builder, separatorToken);
        }

        // Handle other occurrences
        for (int i = 1; i < minOccurrence; i++) {

            // We always skip to the separator if we recover. So the separator has to be there.
            if (!parse(builder, separatorToken)) {
                marker.rollbackTo();
                return FAIL;
            }

            GracefulResult result = nestedRule.apply(builder);
            if (result == FAIL) {
                marker.rollbackTo();
                return FAIL;
            } else if (firstResult == RECOVER) {

                // The nested rule asks to recover. So we can skip until the separator
                skipUntil(builder, separatorToken);
            }
        }

        // Other nested tokens
        while (parse(builder, separatorToken)) {

            GracefulResult result = nestedRule.apply(builder);
            if (result == FAIL) {
                marker.rollbackTo();
                return FAIL;
            } else if (firstResult == RECOVER) {

                // The nested rule asks to recover. So we can skip until the separator
                skipUntil(builder, separatorToken);
            }
        }

        finishMarker(marker);
        return MATCH;
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(nestedRule);
        sb.append(' ');
        sb.append(separatorToken);
        sb.append(" ... ");
        sb.append(separatorToken);
        sb.append(' ');
        sb.append(nestedRule);
        sb.append(')');
        return sb.toString();
    }

    //---------------------------------------------------------------------------------------------
    // Helper Methods
    //---------------------------------------------------------------------------------------------

    @NotNull
    private GracefulResult exitAfterFirstApplication(@NotNull PsiBuilder.Marker marker) {
        if (minOccurrence == 0) {
            finishMarker(marker);
            return MATCH;
        } else {
            marker.drop();
            return FAIL;
        }
    }
}