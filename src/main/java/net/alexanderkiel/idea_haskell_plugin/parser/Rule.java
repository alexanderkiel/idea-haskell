package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public interface Rule {

    /**
     * Tries to apply this rule to the token stream.
     *
     * @param builder the PSI builder to use
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    boolean apply(@NotNull PsiBuilder builder);
}
