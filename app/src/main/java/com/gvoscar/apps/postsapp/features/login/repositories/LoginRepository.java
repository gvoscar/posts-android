package com.gvoscar.apps.postsapp.features.login.repositories;

public interface LoginRepository {
    void signInWithEmailAndPassword(final String email, final String password);
}
