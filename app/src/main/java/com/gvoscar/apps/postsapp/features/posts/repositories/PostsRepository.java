package com.gvoscar.apps.postsapp.features.posts.repositories;

public interface PostsRepository {
    void getData();

    void subscribe();

    void unsubscribe();

    void removeAll();

    void removeById(String postId);
}
