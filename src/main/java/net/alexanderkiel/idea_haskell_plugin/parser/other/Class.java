package net.alexanderkiel.idea_haskell_plugin.parser.other;

import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import net.alexanderkiel.idea_haskell_plugin.parser.PlusRule;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Type.aType;
import net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier.*;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.CLASS;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.OPEN_PAR;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.CLOSE_PAR;
import org.jetbrains.annotations.NotNull;
import com.intellij.lang.PsiBuilder;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Class implements Rule {

    public static final Class clazz = new Class();

    private Class() {
    }

    /**
     * class <- qtycls ( tyvar / ( tyvar atype1 ... atypen ) ) ( n >= 1 )
     *
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (!parseQTypeClass(builder)) {
            marker.drop();
            return false;
        }

        if (parseTypeVar(builder)) {
            marker.done(CLASS);
            return true;
        }

        if (!parse(builder, OPEN_PAR)) {
            marker.rollbackTo();
            return false;
        }

        if (!parseTypeVar(builder)) {
            marker.rollbackTo();
            return false;
        }

        if (!aTypePlus.apply(builder)) {
            marker.rollbackTo();
            return false;
        }

        if (!parse(builder, CLOSE_PAR)) {
            marker.rollbackTo();
            return false;
        }

        marker.done(CLASS);
        return true;
    }

    private static final Rule aTypePlus = new PlusRule(aType);
}
