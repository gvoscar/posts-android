package com.gvoscar.apps.postsapp.features.details.presenters;

public interface PostDetailsPresenter {
    void onCreate();

    void onDestroy();

    void getData(String userdId);

    void setFavorite(String postId, boolean favorite);

    void setRead(String postId, boolean read);
}
