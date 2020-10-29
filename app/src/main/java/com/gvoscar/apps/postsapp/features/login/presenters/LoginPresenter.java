package com.gvoscar.apps.postsapp.features.login.presenters;

public interface LoginPresenter {
    void onCreate();

    void onDestroy();

    void signInWithEmailAndPassword(final String email, final String password);
}
