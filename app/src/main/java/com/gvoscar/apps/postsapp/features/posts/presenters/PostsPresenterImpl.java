package com.gvoscar.apps.postsapp.features.posts.presenters;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.gvoscar.apps.postsapp.features.posts.events.PostsEvent;
import com.gvoscar.apps.postsapp.features.posts.interactors.PostsInteractor;
import com.gvoscar.apps.postsapp.features.posts.interactors.PostsInteractorImpl;
import com.gvoscar.apps.postsapp.features.posts.ui.PostsView;
import com.gvoscar.apps.postsapp.libs.base.eventbus.EventBus;
import com.gvoscar.apps.postsapp.libs.base.eventbus.GreenRobotEventBus;
import com.gvoscar.apps.postsapp.pojos.Post;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class PostsPresenterImpl implements PostsPresenter {
    private EventBus mBus;
    private PostsView mView;
    private PostsInteractor mInteractor;

    public PostsPresenterImpl(FragmentActivity fragmentActivity, Context context, PostsView view) {
        this.mBus = GreenRobotEventBus.getInstance();
        this.mView = view;
        this.mInteractor = new PostsInteractorImpl(fragmentActivity, context);
    }

    @Override
    public void onCreate() {
        this.mBus.register(this);
    }

    @Override
    public void onStart() {
        mInteractor.subscribe();
    }

    @Override
    public void onStop() {
        mInteractor.unsubscribe();
    }

    @Override
    public void onDestroy() {
        this.mBus.unregister(this);
    }

    @Override
    public void getData() {
        this.mInteractor.getData();
    }

    @Override
    public void removeAll() {
        mInteractor.removeAll();
    }

    @Override
    public void removeById(String postId) {
        mInteractor.removeById(postId);
    }

    @Subscribe
    public void onEvent(PostsEvent event) {
        switch (event.getEventType()) {
            case PostsEvent.NOT_FOUND:
                onNotFound(event.getMessage());
                break;
            case PostsEvent.DATA_LOADED:
                onDataLoaded(event.getList());
                break;
        }
    }

    public void onNotFound(String message) {
        if (mView != null) {
            mView.onNotFound(message);
        }
    }

    public void onDataLoaded(List<Post> list) {
        if (mView != null) {
            mView.onDataLoaded(list);
        }
    }
}
