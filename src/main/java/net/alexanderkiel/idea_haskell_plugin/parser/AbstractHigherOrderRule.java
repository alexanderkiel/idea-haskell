package net.alexanderkiel.idea_haskell_plugin.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import net.alexanderkiel.idea_haskell_plugin.HaskellElementType;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public abstract class AbstractHigherOrderRule implements Rule {

    @Nullable
    protected final IElementType resultType;

    @NotNull
    protected final Rule nestedRule;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public AbstractHigherOrderRule(@Nullable IElementType resultType, @NotNull Rule nestedRule) {
        this.resultType = resultType;
        this.nestedRule = nestedRule;
    }

    //---------------------------------------------------------------------------------------------
    //
    //---------------------------------------------------------------------------------------------

    protected void finishMarker(@NotNull PsiBuilder.Marker marker) {
        if (resultType != null) {
            marker.done(resultType);
        } else {
            marker.drop();
        }
    }
}
