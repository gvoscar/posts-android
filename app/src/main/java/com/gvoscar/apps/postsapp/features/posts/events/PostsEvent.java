package com.gvoscar.apps.postsapp.features.posts.events;

import com.gvoscar.apps.postsapp.pojos.Post;

import java.util.List;

public class PostsEvent {
    public static final int NOT_FOUND = 100;
    public static final int DATA_LOADED = 200;

    private int eventType;
    private String message;
    private List<Post> list;

    public PostsEvent() {
    }

    public PostsEvent(int eventType, String message, List<Post> list) {
        this.eventType = eventType;
        this.message = message;
        this.list = list;
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

    public List<Post> getList() {
        return list;
    }

    public void setList(List<Post> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PostsEvent{" +
                "eventType=" + eventType +
                ", message='" + message + '\'' +
                ", list=" + list +
                '}';
    }
}
