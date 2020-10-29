package com.gvoscar.apps.postsapp.apis.jsonplaceholder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class JSONPlaceholderClient {

    private final static String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private Retrofit retrofit;

    public JSONPlaceholderClient() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public JSONPlaceholderService getJsonPlaceholderService() {
        return this.retrofit.create(JSONPlaceholderService.class);
    }
}
