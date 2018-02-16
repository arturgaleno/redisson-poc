package io.github.arturgaleno.sessionmanager;

import io.github.arturgaleno.domain.SessionObject;
import org.springframework.stereotype.Component;

/**
 * Created by arturgaleno on 16/02/2018.
 */
@Component
public class RedissonSessionManager implements SessionManager {

    @Override
    public SessionObject getSession(String key) {
        return null;
    }

    @Override
    public void storeSession(String key, SessionObject sessionObject) {

    }
}
