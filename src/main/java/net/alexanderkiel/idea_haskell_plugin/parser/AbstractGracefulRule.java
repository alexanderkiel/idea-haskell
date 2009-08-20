package net.alexanderkiel.idea_haskell_plugin.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public abstract class AbstractGracefulRule implements GracefulRule {

    @Nullable
    private final IElementType resultType;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public AbstractGracefulRule(@Nullable IElementType resultType) {
        this.resultType = resultType;
    }

    //---------------------------------------------------------------------------------------------
    // Helper Methods
    //---------------------------------------------------------------------------------------------

    protected void finishMarker(@NotNull PsiBuilder.Marker marker) {
        if (resultType != null) {
            marker.done(resultType);
        } else {
            marker.drop();
        }
    }
}
