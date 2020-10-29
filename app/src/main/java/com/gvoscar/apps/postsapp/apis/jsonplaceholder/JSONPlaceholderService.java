package com.gvoscar.apps.postsapp.apis.jsonplaceholder;

import android.database.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONPlaceholderService {

    @GET("posts")
    Observable<Call<PostsResponse>> getPosts();

    @GET("posts")
    Call<PostsResponse> posts();

    @GET("users/{userId}")
    Call<PostsResponse> usersById(@Path("userId") String userId);

}
