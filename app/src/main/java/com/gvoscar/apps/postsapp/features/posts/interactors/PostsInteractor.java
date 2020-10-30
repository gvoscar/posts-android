package com.gvoscar.apps.postsapp.features.posts.interactors;

public interface PostsInteractor {
    void getData();

    void subscribe();

    void unsubscribe();

    void removeAll();

    void removeById(String postId);
}
