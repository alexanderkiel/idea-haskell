package net.alexanderkiel.idea_haskell_plugin.parser.other;

import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier;
import static net.alexanderkiel.idea_haskell_plugin.parser.basic.Identifier.*;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import org.jetbrains.annotations.NotNull;
import com.intellij.lang.PsiBuilder;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class SimpleClass implements Rule {

    public static final SimpleClass simpleClass = new SimpleClass();

    private SimpleClass() {
    }

    /**
     * simpleclass <- qtycls tyvar
     *
     * @return {@code true} if the rule could be parsed; {@code false} otherwise
     */
    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (parseQTypeClass(builder) && parseTypeVar(builder)) {
            marker.done(SIMPLE_CLASS);
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
    }
}