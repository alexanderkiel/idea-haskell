package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.CLOSE_PAR;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.OPEN_PAR;
import net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class ParenthesizedRule extends AbstractHigherOrderRule {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public ParenthesizedRule(@NotNull Rule nestedRule) {
        super(null, nestedRule);
    }

    public ParenthesizedRule(@NotNull IElementType resultType, @NotNull Rule nestedRule) {
        super(resultType, nestedRule);
    }

    //---------------------------------------------------------------------------------------------
    // Rule Implementation
    //---------------------------------------------------------------------------------------------

    /**
     * ( nested )
     *
     * @param builder the PSI builder to use
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (parse(builder, OPEN_PAR) && nestedRule.apply(builder) && parse(builder, CLOSE_PAR)) {
            finishMarker(marker);
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "( " + nestedRule + " )";
    }
}