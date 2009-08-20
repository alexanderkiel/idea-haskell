package net.alexanderkiel.idea_haskell_plugin.parser;

import com.intellij.openapi.util.UserDataHolder;
import com.intellij.openapi.util.Key;

/**
 * @author Alexander Kiel
 * @version $Id$
 */
class SimpleUserDataHolder implements UserDataHolder {

    public <T> T getUserData(Key<T> key) {
        throw new UnsupportedOperationException();
    }

    public <T> void putUserData(Key<T> key, T value) {
        throw new UnsupportedOperationException();
    }
}
