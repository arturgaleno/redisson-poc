package io.github.arturgaleno.sessionmanager;

import io.github.arturgaleno.domain.SessionObject;

/**
 * Created by arturgaleno on 16/02/2018.
 */
public interface SessionManager {

    SessionObject getSession(String key);

    void storeSession(String key, SessionObject sessionObject);
}
