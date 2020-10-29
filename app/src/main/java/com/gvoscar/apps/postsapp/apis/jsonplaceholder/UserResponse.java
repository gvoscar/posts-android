package com.gvoscar.apps.postsapp.apis.jsonplaceholder;

import com.gvoscar.apps.postsapp.pojos.User;

public class UserResponse {
    private User user;

    public UserResponse() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "user=" + user +
                '}';
    }
}
