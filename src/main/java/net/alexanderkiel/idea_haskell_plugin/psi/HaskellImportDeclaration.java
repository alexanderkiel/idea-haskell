package net.alexanderkiel.idea_haskell_plugin.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
public interface HaskellImportDeclaration extends HaskellElement {

    /**
     * Returns the name of the module which is imported by this declaration.
     *
     * @return the name of the module which is imported by this declaration
     */
    @NotNull
    String getModuleName();

    /**
     * Returns {@code true} iff this import is a qualified import.
     *
     * @return {@code true} if this import is a qualified import; {@code false} otherwise
     * @see #getAliasName()
     */
    boolean isQualified();

    /**
     * Returns the alias with which the module is imported.
     *
     * Returns {@code null} if this import isn't qualified.
     *
     * @return the alias name or {@code null} if this import isn't qualified
     * @see #isQualified()
     */
    @Nullable
    String getAliasName();

    //TODO importspecs
}
