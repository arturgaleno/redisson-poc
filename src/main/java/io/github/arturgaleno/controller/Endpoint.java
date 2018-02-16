package io.github.arturgaleno.controller;

import io.github.arturgaleno.domain.SessionObject;
import io.github.arturgaleno.domain.SessionSubObject;
import io.github.arturgaleno.sessionmanager.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by arturgaleno on 16/02/2018.
 */
@RestController
public class Endpoint {

    @Autowired
    private SessionManager sessionManager;

    @GetMapping(path = "test")
    public SessionObject test(@RequestParam("sessionId") String sessionId) {

        SessionObject sessionObject = sessionManager.getSession(sessionId);

        return sessionObject;
    }

    @GetMapping(path = "createSession")
    public String createSession() {

        SessionObject sessionObject = new SessionObject();
        sessionObject.setValue("value1");

        SessionSubObject sessionSubObject = new SessionSubObject();
        sessionSubObject.setSubValue("value2");

        sessionObject.setSessionSubObject(sessionSubObject);

        String uuid = UUID.randomUUID().toString();

        sessionManager.storeSession(uuid, sessionObject);

        return uuid;
    }


    @GetMapping(path = "updateSession")
    public void createSession(@RequestParam("sessionId") String sessionId, @RequestParam("value") String value) {

        SessionObject sessionObject = sessionManager.getSession(sessionId);

        sessionObject.setValue(value);

        sessionManager.storeSession(sessionId, sessionObject);
    }
}
