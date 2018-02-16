package io.github.arturgaleno.domain;

/**
 * Created by arturgaleno on 16/02/2018.
 */
public class SessionObject {

    private String value;

    private SessionSubObject sessionSubObject;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SessionSubObject getSessionSubObject() {
        return sessionSubObject;
    }

    public void setSessionSubObject(SessionSubObject sessionSubObject) {
        this.sessionSubObject = sessionSubObject;
    }
}
