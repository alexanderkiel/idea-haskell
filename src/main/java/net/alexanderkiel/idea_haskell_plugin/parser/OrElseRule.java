package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public class OrElseRule implements Rule {

    private final IElementType resultType;
    private final Rule firstRule;
    private final Rule secondRule;
    private final Rule[] otherRules;

    //---------------------------------------------------------------------------------------------
    // Constructor
    //---------------------------------------------------------------------------------------------

    public OrElseRule(@NotNull IElementType resultType, @NotNull Rule firstRule, @NotNull Rule secondRule,
                      @NotNull Rule... otherRules) {
        this.resultType = resultType;
        this.firstRule = firstRule;
        this.secondRule = secondRule;
        this.otherRules = otherRules;
    }

    public boolean apply(@NotNull PsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (firstRule.apply(builder) || secondRule.apply(builder)) {
            marker.done(resultType);
            return true;
        }

        if (otherRules != null) {
            for (Rule rule : otherRules) {
                if (rule.apply(builder)) {
                    marker.done(resultType);
                    return true;
                }
            }
        }

        marker.drop();
        return false;
    }

    //---------------------------------------------------------------------------------------------
    // Overridden Object Methods
    //---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append(firstRule);
        sb.append(" / ");
        sb.append(secondRule);
        for (Rule rule : otherRules) {
            sb.append(" / ");
            sb.append(rule);
        }
        sb.append(')');
        return sb.toString();
    }
}
