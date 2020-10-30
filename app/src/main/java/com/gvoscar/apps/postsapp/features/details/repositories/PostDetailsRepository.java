package com.gvoscar.apps.postsapp.features.details.repositories;

public interface PostDetailsRepository {
    void getData(String userdId);

    void setFavorite(String postId, boolean favorite);

    void setRead(String postId, boolean read);
}
