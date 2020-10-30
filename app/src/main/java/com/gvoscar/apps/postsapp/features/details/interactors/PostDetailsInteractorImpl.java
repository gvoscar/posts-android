package com.gvoscar.apps.postsapp.features.details.interactors;

import com.gvoscar.apps.postsapp.features.details.repositories.PostDetailsRepository;
import com.gvoscar.apps.postsapp.features.details.repositories.PostDetailsRepositoryImpl;

public class PostDetailsInteractorImpl implements PostDetailsInteractor {
    private PostDetailsRepository repository;

    public PostDetailsInteractorImpl() {
        repository = new PostDetailsRepositoryImpl();
    }

    @Override
    public void getData(String userdId) {
        repository.getData(userdId);
    }

    @Override
    public void setFavorite(String postId, boolean favorite) {
        repository.setFavorite(postId, favorite);
    }

    @Override
    public void setRead(String postId, boolean read) {
        repository.setRead(postId, read);
    }
}
