package com.gvoscar.apps.postsapp.apis.jsonplaceholder;

import com.gvoscar.apps.postsapp.pojos.Post;

import java.util.Arrays;
import java.util.List;

public class PostsResponse {
    private Post[] posts;

    public PostsResponse() {
    }

    public Post[] getPosts() {
        return posts;
    }

    public void setPosts(Post[] posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "PostsResponse{" +
                "posts=" + Arrays.toString(posts) +
                '}';
    }
}
