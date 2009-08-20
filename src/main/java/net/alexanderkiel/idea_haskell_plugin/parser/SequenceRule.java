package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class SequenceRule implements Rule {

    @Nullable
    private final IElementType resultType;

    @NotNull
    private final Rule[] rules;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public SequenceRule(@NotNull Rule... rules) {
        this.resultType = null;
        this.rules = rules;
    }

    public SequenceRule(@NotNull IElementType resultType, @NotNull Rule... rules) {
        this.resultType = resultType;
        this.rules = rules;
    }

    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        for (Rule rule : rules) {
            if (!rule.apply(builder)) {
                marker.rollbackTo();
                return false;
            }
        }

        if (resultType != null) {
            marker.done(resultType);
        } else {
            marker.drop();
        }
        return true;
    }
}