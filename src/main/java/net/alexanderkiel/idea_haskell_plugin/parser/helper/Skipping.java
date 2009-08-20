package net.alexanderkiel.idea_haskell_plugin.parser.helper;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Skipping {

    public static boolean skipUntil(PsiBuilder builder, IElementType tokenType) {

        while (builder.getTokenType() != tokenType) {

            // Return false if we aren't able to skip
            if (builder.eof()) {
                return false;
            }

            // Skip
            builder.advanceLexer();
        }

        return true;
    }

    public static boolean skipUntil(PsiBuilder builder, IElementType tokenType1, IElementType tokenType2) {

        while (builder.getTokenType() != tokenType1 && builder.getTokenType() != tokenType2) {

            // Return false if we aren't able to skip
            if (builder.eof()) {
                return false;
            }

            // Skip
            builder.advanceLexer();
        }

        return true;
    }

    public static void skipUntil(PsiBuilder builder, IElementType tokenType, String errorMessage) {
        if (!builder.eof() && builder.getTokenType() != tokenType) {
            PsiBuilder.Marker marker = builder.mark();
            while (!builder.eof() && builder.getTokenType() != tokenType) {
                builder.advanceLexer();
            }
            marker.error(errorMessage);
        }
    }

    public static void skipUntil(PsiBuilder builder, IElementType tokenType1, IElementType tokenType2,
                                 String errorMessage) {
        if (!builder.eof() && builder.getTokenType() != tokenType1 && builder.getTokenType() != tokenType2) {
            PsiBuilder.Marker marker = builder.mark();
            while (!builder.eof() && builder.getTokenType() != tokenType1 &&
                    builder.getTokenType() != tokenType2) {
                builder.advanceLexer();
            }
            marker.error(errorMessage);
        }
    }

    public static void skipUntil(PsiBuilder builder, IElementType tokenType1, IElementType tokenType2,
                                 IElementType tokenType3,
                                 String errorMessage) {
        if (!builder.eof() && builder.getTokenType() != tokenType1 && builder.getTokenType() != tokenType2 &&
                builder.getTokenType() != tokenType3) {
            PsiBuilder.Marker marker = builder.mark();
            while (!builder.eof() && builder.getTokenType() != tokenType1 &&
                    builder.getTokenType() != tokenType2 && builder.getTokenType() != tokenType3) {
                builder.advanceLexer();
            }
            marker.error(errorMessage);
        }
    }
}
