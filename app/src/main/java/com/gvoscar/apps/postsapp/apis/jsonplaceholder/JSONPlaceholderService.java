package com.gvoscar.apps.postsapp.apis.jsonplaceholder;



import com.gvoscar.apps.postsapp.pojos.Post;
import com.gvoscar.apps.postsapp.pojos.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONPlaceholderService {

    @GET("posts")
    Observable<List<Post>> getPosts();

    @GET("posts")
    Call<List<Post>> posts();

    @GET("users/{userId}")
    Observable<User> getUsersById(@Path("userId") String userId);

}
 