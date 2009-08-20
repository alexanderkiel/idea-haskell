package net.alexanderkiel.idea_haskell_plugin.parser;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public interface PrecedenceLevelRule {

    boolean apply(int level);
}