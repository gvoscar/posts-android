package com.gvoscar.apps.postsapp.features.details.interactors;

public interface PostDetailsInteractor {
    void getData(String userdId);

    void setFavorite(String postId, boolean favorite);

    void setRead(String postId, boolean read);
}
