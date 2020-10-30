package com.gvoscar.apps.postsapp;

import androidx.multidex.MultiDexApplication;

import com.gvoscar.apps.postsapp.pojos.Post;
import com.gvoscar.apps.postsapp.pojos.User;

public class App extends MultiDexApplication {

    private Post post;
    private User user;

    @Override
    public void onCreate() {
        super.onCreate();
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
}
