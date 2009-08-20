package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class SeparatedSequenceRule extends AbstractHigherOrderRule {

    private final IElementType separatorToken;
    private final int minOccurrence;

    //---------------------------------------------------------------------------------------------
    // Constructors
    //---------------------------------------------------------------------------------------------

    public SeparatedSequenceRule(@NotNull Rule nestedRule, @NotNull IElementType separatorToken,
                                 int minOccurrence) {
        super(null, nestedRule);
        this.separatorToken = separatorToken;
        this.minOccurrence = minOccurrence;
    }

    public SeparatedSequenceRule(@NotNull IElementType resultType, @NotNull Rule nestedRule,
                                 @NotNull IElementType separatorToken, int minOccurrence) {
        super(resultType, nestedRule);
        this.separatorToken = separatorToken;
        this.minOccurrence = minOccurrence;
    }

    //---------------------------------------------------------------------------------------------
    // Rule Implementation
    //---------------------------------------------------------------------------------------------

    /**
     * nested1 {@link #separatorToken ,} ... {@link #separatorToken ,} nestedn ( n >= {@link #minOccurrence k}
     * )
     *
     * @param builder the PSI builder to use
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (!nestedRule.apply(builder)) {
            if (minOccurrence == 0) {
                finishMarker(marker);
                return true;
            } else {
                marker.drop();
                return false;
            }
        }

        for (int i = 1; i < minOccurrence; i++) {
            if (!parse(builder, separatorToken) || !nestedRule.apply(builder)) {
                marker.rollbackTo();
                return false;
            }
        }

        // Other nested tokens
        while (parse(builder, separatorToken)) {
            if (!nestedRule.apply(builder)) {
                marker.rollbackTo();
                return false;
            }
        }

        finishMarker(marker);
        return true;
    }

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
}
