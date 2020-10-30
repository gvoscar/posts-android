package com.gvoscar.apps.postsapp.features.posts.presenters;

public interface PostsPresenter {
    void onCreate();

    void onStart();

    void onStop();

    void onDestroy();

    void getData();

    void removeAll();

    void removeById(String postId);
}
