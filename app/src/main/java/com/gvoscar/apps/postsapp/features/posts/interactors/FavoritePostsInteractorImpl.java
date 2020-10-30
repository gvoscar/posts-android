package com.gvoscar.apps.postsapp.features.posts.interactors;

import com.gvoscar.apps.postsapp.features.posts.repositories.FavoritePostsRepository;
import com.gvoscar.apps.postsapp.features.posts.repositories.FavoritePostsRepositoryImpl;

public class FavoritePostsInteractorImpl implements FavoritePostsInteractor {

    FavoritePostsRepository repository;

    public FavoritePostsInteractorImpl() {
        repository = new FavoritePostsRepositoryImpl();
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }
}
