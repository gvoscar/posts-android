package com.gvoscar.apps.postsapp.features.posts.ui;

import com.gvoscar.apps.postsapp.pojos.Post;

import java.util.List;

public interface PostsView {
    void onNotFound(String message);

    void onDataLoaded(List<Post> list);
}
