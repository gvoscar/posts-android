package com.gvoscar.apps.postsapp.features.loader.events;

public class LoaderEvent {

    public static final int UNAUTHENTICATED = 100;
    public static final int AUTHENTICATED = 200;


    private int eventType;
    private String message;

    public LoaderEvent() {
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
