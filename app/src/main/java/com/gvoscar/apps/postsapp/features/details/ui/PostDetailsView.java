package com.gvoscar.apps.postsapp.features.details.ui;

import com.gvoscar.apps.postsapp.pojos.Post;
import com.gvoscar.apps.postsapp.pojos.User;

import java.util.List;

public interface PostDetailsView {
    void onError(String message);

    void onSuccess(User user);
}
