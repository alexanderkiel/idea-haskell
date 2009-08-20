package net.alexanderkiel.idea_haskell_plugin.parser;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
@Target(ElementType.METHOD)
public @interface GracefulErrorRecovery {
}
