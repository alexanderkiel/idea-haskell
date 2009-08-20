package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class EmptyRule implements Rule {

    public static final Rule empty = new EmptyRule();

    private EmptyRule() {
    }

    /**
     * Does nothing. Returns always {@code true}.
     *
     * @param builder the PSI builder to use
     * @return always {@code true}
     */
    public boolean apply(@NotNull PsiBuilder builder) {
        return true;
    }

    @Override
    public String toString() {
        return "empty";
    }
}
