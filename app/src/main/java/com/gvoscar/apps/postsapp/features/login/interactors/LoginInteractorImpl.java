package com.gvoscar.apps.postsapp.features.login.interactors;

import com.gvoscar.apps.postsapp.features.login.repositories.LoginRepository;
import com.gvoscar.apps.postsapp.features.login.repositories.LoginRepositoryImpl;

public class LoginInteractorImpl implements LoginInteractor {

    private static final String TAG = LoginInteractorImpl.class.getSimpleName();
    private LoginRepository mRepository;

    public LoginInteractorImpl() {
        mRepository = new LoginRepositoryImpl();
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password) {
        mRepository.signInWithEmailAndPassword(email, password);
    }
}
