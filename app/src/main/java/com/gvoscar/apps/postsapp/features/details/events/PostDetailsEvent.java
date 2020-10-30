package com.gvoscar.apps.postsapp.features.details.events;

import com.gvoscar.apps.postsapp.pojos.Post;
import com.gvoscar.apps.postsapp.pojos.User;

import java.util.List;

public class PostDetailsEvent {
    public static final int ERROR = 100;
    public static final int SUCESS = 200;

    private int eventType;
    private String message;
    private Post post;
    private User user;

    public PostDetailsEvent() {
    }

    public PostDetailsEvent(int eventType) {
        this.eventType = eventType;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "DetailsEvent{" +
                "eventType=" + eventType +
                ", message='" + message + '\'' +
                ", post=" + post +
                ", user=" + user +
                '}';
    }
}
