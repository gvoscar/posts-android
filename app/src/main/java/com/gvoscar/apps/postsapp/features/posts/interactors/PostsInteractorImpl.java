package com.gvoscar.apps.postsapp.features.posts.interactors;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.gvoscar.apps.postsapp.features.posts.repositories.PostsRepository;
import com.gvoscar.apps.postsapp.features.posts.repositories.PostsRepositoryImpl;

public class PostsInteractorImpl implements PostsInteractor {
    private static final String TAG = PostsInteractorImpl.class.getSimpleName();
    private PostsRepository repository;

    public PostsInteractorImpl(FragmentActivity fragmentActivity, Context context) {
        Log.d(TAG, "PostsInteractorImpl()");
        this.repository = new PostsRepositoryImpl(fragmentActivity, context);
    }

    @Override
    public void getData() {
        Log.d(TAG, "getData()");
        this.repository.getData();
    }
}
