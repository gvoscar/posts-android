package com.gvoscar.apps.postsapp.features.loader.interactors;

import com.gvoscar.apps.postsapp.features.loader.repositories.LoaderRepository;
import com.gvoscar.apps.postsapp.features.loader.repositories.LoaderRepositoryImpl;

public class LoaderInteractorImpl implements LoaderInteractor {

    private static final String TAG = LoaderInteractorImpl.class.getSimpleName();
    private LoaderRepository mRepository;

    public LoaderInteractorImpl() {
        mRepository = new LoaderRepositoryImpl();
    }

    @Override
    public void checkAuth() {
        mRepository.checkAuth();
    }
}
