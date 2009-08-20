package net.alexanderkiel.idea_haskell_plugin.parser.other;

import com.intellij.lang.PsiBuilder;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.*;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.COMMA;
import static net.alexanderkiel.idea_haskell_plugin.HaskellElementTypes.S_CONTEXT;
import net.alexanderkiel.idea_haskell_plugin.parser.BracketedSequenceRule;
import net.alexanderkiel.idea_haskell_plugin.parser.Rule;
import net.alexanderkiel.idea_haskell_plugin.parser.OrElseRule;
import net.alexanderkiel.idea_haskell_plugin.parser.SequenceRule;
import static net.alexanderkiel.idea_haskell_plugin.parser.helper.Parsing.parse;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.Class.clazz;
import static net.alexanderkiel.idea_haskell_plugin.parser.other.SimpleClass.simpleClass;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class Context {

    /**
     * context <- class / ( class1 , ... , classn ) ( n >= 0)
     */
    public final static Rule context = new OrElseRule(CONTEXT, clazz, new BracketedSequenceRule(clazz, OPEN_PAR,
            CLOSE_PAR, COMMA, 0));

    /**
     * scontext <- simpleclass / ( simpleclass1 , ... , simpleclassn ) ( n >= 0)
     */
    public final static Rule simpleContext = new OrElseRule(S_CONTEXT, simpleClass, new BracketedSequenceRule(
            simpleClass, OPEN_PAR, CLOSE_PAR, COMMA, 0));

    public static final Rule contextWithOp = new SequenceRule(context, new Rule() {
        public boolean apply(@NotNull PsiBuilder builder) {
            return parse(builder, CONTEXT_OP);
        }
    });

    public static final Rule simpleContextWithOp = new SequenceRule(simpleContext, new Rule() {
        public boolean apply(@NotNull PsiBuilder builder) {
            return parse(builder, CONTEXT_OP);
        }
    });
}
