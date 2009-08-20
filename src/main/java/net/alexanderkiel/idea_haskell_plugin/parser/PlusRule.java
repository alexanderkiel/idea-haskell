package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class PlusRule extends AbstractHigherOrderRule {

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public PlusRule(@NotNull Rule nestedRule) {
        super(null, nestedRule);
    }

    public PlusRule(@NotNull IElementType resultType, @NotNull Rule nestedRule) {
        super(resultType, nestedRule);
    }

    public boolean apply(@NotNull PsiBuilder builder) {
        return resultType != null ? applyMarking(builder) : applyNotMarking(builder);
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return nestedRule + "+";
    }

    //---------------------------------------------------------------------------------------------
    // Helper Methods
    //---------------------------------------------------------------------------------------------

    private boolean applyMarking(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (!nestedRule.apply(builder)) {
            marker.drop();
            return false;
        }

        marker = doneAndPrecede(marker);

        while (nestedRule.apply(builder)) {
            marker = doneAndPrecede(marker);
        }

        marker.drop();
        return true;
    }

    private boolean applyNotMarking(@NotNull PsiBuilder builder) {
        if (!nestedRule.apply(builder)) {
            return false;
        }

        while (nestedRule.apply(builder)) {}

        return true;
    }

    @NotNull
    private PsiBuilder.Marker doneAndPrecede(@NotNull PsiBuilder.Marker marker) {
        marker.done(resultType);
        marker = marker.precede();
        return marker;
    }
}