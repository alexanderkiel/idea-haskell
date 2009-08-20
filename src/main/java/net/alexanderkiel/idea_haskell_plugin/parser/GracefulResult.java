package net.alexanderkiel.idea_haskell_plugin.parser;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public enum GracefulResult {

    /**
     * This is a full match of the rule like {@code true} in {@link Rule#apply(com.intellij.lang.PsiBuilder)}.
     */
    MATCH,

    /**
     * This is a full fail of the rule like {@code false} in {@link Rule#apply(com.intellij.lang.PsiBuilder)}.
     * <p/>
     * If a rule returns {@code FAIL} than like in {@link Rule} it has to rollback the lexer completely. Returning fail
     * means that nothing happend and that the caller can try other rules as if the returning rule wouldn't been
     * called.
     */
    FAIL,

    /**
     * This means that the rule has found something, issued an error in the builder but was not able to recover fully
     * from this error. So it might possible that there are some bad tokens left in the stream. Essentually the rule
     * asks the caller to skip over this bad tokens as he might have better informations to do so.
     */
    RECOVER
}
