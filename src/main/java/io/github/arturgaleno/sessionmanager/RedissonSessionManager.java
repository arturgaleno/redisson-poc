package io.github.arturgaleno.sessionmanager;

import io.github.arturgaleno.domain.SessionObject;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by arturgaleno on 16/02/2018.
 */
@Component
public class RedissonSessionManager implements SessionManager {

    @Autowired
    private RLocalCachedMap<String, SessionObject> sessionMap;

    @Override
    public SessionObject getSession(String key) {
        return sessionMap.get(key);
    }

    @Override
    public void storeSession(String key, SessionObject sessionObject) {
        RLock sessionMapLock = sessionMap.getLock(key);
        sessionMapLock.lock();
        sessionMap.fastPut(key, sessionObject);
        sessionMapLock.unlock();
    }
}
