package net.alexanderkiel.idea_haskell_plugin.parser;

import org.jetbrains.annotations.NotNull;
import com.intellij.lang.PsiBuilder;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public interface GracefulRule {

    /**
     * Tries to apply this rule to the token stream.
     *
     * @param builder the PSI builder to use
     * @return a result (please have a look into the {@link GracefulResult result documentation})
     */
    GracefulResult apply(@NotNull PsiBuilder builder);
}