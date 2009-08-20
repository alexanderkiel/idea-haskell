package net.alexanderkiel.idea_haskell_plugin.parser.basic;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.CON_ID;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.MOD_ID;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Symbol.parseDotSym;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
class Qualified {

    static boolean parseQualified(@NotNull PsiBuilder builder, @NotNull IElementType resultType,
                                  @NotNull IElementType tokenType) {
        PsiBuilder.Marker start = builder.mark();
        if (parseReferencingModId(builder) && parseDotSym(builder) && parse(builder, tokenType) ||
                parse(builder, tokenType)) {
            start.done(resultType);
            return true;
        } else {
            start.rollbackTo();
            return false;
        }
    }

    static boolean parseReferencingModId(@NotNull PsiBuilder builder) {
        final PsiBuilder.Marker start = builder.mark();

        PsiBuilder.Marker iter = builder.mark();
        PsiBuilder.Marker beforeLast = null;
        while (consumeConIdDotSymPair(builder)) {
            if (beforeLast != null) {
                beforeLast.drop();
            }
            beforeLast = iter;
            iter = builder.mark();
        }

        if (beforeLast == null) {
            start.rollbackTo();
            return false;
        } else {
            beforeLast.rollbackTo();
            parse(builder, CON_ID);
            start.done(MOD_ID);
            return true;
        }
    }

    static boolean consumeConIdDotSymPair(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker start = builder.mark();
        if (parse(builder, CON_ID) && parseDotSym(builder)) {
            start.drop();
            return true;
        }
        start.rollbackTo();
        return false;
    }
}
