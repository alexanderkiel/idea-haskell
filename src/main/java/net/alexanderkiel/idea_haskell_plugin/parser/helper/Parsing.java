package net.alexanderkiel.idea_haskell_plugin.parser.helper;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Parsing {

    /**
     * Tries to parse the token with the given token type.
     * <p/>
     * Advances the lexer and marks the token with the given type, if it was the current token. Otherwise does
     * nothing.
     *
     * @param builder   the PSI builder to use
     * @param tokenType the type of the token to parse
     * @return {@code true} if the token could be parsed; {@code false} otherwise
     */
    public static boolean parse(@NotNull PsiBuilder builder, @NotNull IElementType tokenType) {
        if (builder.getTokenType() == tokenType) {
            PsiBuilder.Marker marker = builder.mark();
            builder.advanceLexer();
            marker.done(tokenType);
            return true;
        } else {
            return false;
        }
    }

    public static boolean parse(@NotNull PsiBuilder builder, @NotNull IElementType resultType,
                                @NotNull IElementType tokenType) {
        if (builder.getTokenType() == tokenType) {
            PsiBuilder.Marker marker = builder.mark();
            builder.advanceLexer();
            marker.done(resultType);
            return true;
        } else {
            return false;
        }
    }

    public static boolean parse(@NotNull PsiBuilder builder, @NotNull IElementType resultType,
                                @NotNull IElementType tokenType, @NotNull String tokenText) {
        if (builder.getTokenType() == tokenType && tokenText.equals(builder.getTokenText())) {
            PsiBuilder.Marker marker = builder.mark();
            builder.advanceLexer();
            marker.done(resultType);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tries to parse all the tokens with the given token types.
     * <p/>
     * Advances the lexer and marks the tokens with the given result type, if all tokens could be parsed.
     * Otherwise does nothing.
     *
     * @param builder    the PSI builder to use
     * @param resultType the type to use for marking
     * @param tokenTypes the types of the tokens to parse
     * @return {@code true} if all the tokens could be parsed; {@code false} otherwise
     */
    public static boolean parseAll(@NotNull PsiBuilder builder, @NotNull IElementType resultType,
                                   @NotNull IElementType... tokenTypes) {

        PsiBuilder.Marker marker = builder.mark();

        for (IElementType tokenType : tokenTypes) {
            if (builder.getTokenType() == tokenType) {
                builder.advanceLexer();
            } else {
                marker.rollbackTo();
                return false;
            }
        }

        marker.done(resultType);
        return true;
    }

    public static boolean parse(@NotNull PsiBuilder builder, @NotNull IElementType resultType,
                                @NotNull TokenSet tokenTypes) {

        PsiBuilder.Marker marker = builder.mark();

        if (tokenTypes.contains(builder.getTokenType())) {
            builder.advanceLexer();
            marker.done(resultType);
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
    }
}
