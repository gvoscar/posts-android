package com.gvoscar.apps.postsapp.features.login.ui;

public interface LoginView {

    void enableInputs();

    void disableInputs();

    void showProgress();

    void hideProgress();

    void onError(String message);

    void onSuccess();
}
