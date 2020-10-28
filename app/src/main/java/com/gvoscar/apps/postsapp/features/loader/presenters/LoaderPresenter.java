package com.gvoscar.apps.postsapp.features.loader.presenters;

public interface LoaderPresenter {
    void onCreate();

    void onDestroy();

    void checkAuth();
}
