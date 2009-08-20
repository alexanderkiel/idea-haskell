package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class OptionRule extends AbstractHigherOrderRule {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public OptionRule(@NotNull Rule nestedRule) {
        super(null, nestedRule);
    }

    public OptionRule(@NotNull IElementType resultType, @NotNull Rule nestedRule) {
        super(resultType, nestedRule);
    }

    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (nestedRule.apply(builder)) {
            finishMarker(marker);
            return true;
        } else {
            marker.drop();
            return true;
        }
    }
}