package com.gvoscar.apps.postsapp.features.login.events;

public class LoginEvent {
    public static final int ERROR = 100;
    public static final int SUCCESS = 200;

    private int eventType;
    private String eventMessage;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }
}
