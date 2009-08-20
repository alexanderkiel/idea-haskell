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
public abstract class AbstractHigherOrderGracefulRule extends AbstractGracefulRule {

    @NotNull
    protected final GracefulRule nestedRule;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public AbstractHigherOrderGracefulRule(@Nullable IElementType resultType, @NotNull GracefulRule nestedRule) {
        super(resultType);
        this.nestedRule = nestedRule;
    }
}