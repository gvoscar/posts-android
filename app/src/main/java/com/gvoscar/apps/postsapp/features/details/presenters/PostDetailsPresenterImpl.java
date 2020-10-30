package com.gvoscar.apps.postsapp.features.details.presenters;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.gvoscar.apps.postsapp.features.details.events.PostDetailsEvent;
import com.gvoscar.apps.postsapp.features.details.interactors.PostDetailsInteractor;
import com.gvoscar.apps.postsapp.features.details.interactors.PostDetailsInteractorImpl;
import com.gvoscar.apps.postsapp.features.details.ui.PostDetailsView;
import com.gvoscar.apps.postsapp.libs.base.eventbus.EventBus;
import com.gvoscar.apps.postsapp.libs.base.eventbus.GreenRobotEventBus;
import com.gvoscar.apps.postsapp.pojos.Post;
import com.gvoscar.apps.postsapp.pojos.User;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class PostDetailsPresenterImpl implements PostDetailsPresenter {

    private static final String TAG = PostDetailsPresenterImpl.class.getSimpleName();
    private EventBus mBus;
    private PostDetailsView mView;
    private PostDetailsInteractor mInteractor;

    public PostDetailsPresenterImpl(PostDetailsView view) {
        this.mBus = GreenRobotEventBus.getInstance();
        this.mView = view;
        this.mInteractor = new PostDetailsInteractorImpl();
    }

    @Override
    public void onCreate() {
        this.mBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.mBus.unregister(this);
    }

    @Override
    public void getData(String userdId) {
        this.mInteractor.getData(userdId);
    }

    @Override
    public void setFavorite(String postId, boolean favorite) {
        mInteractor.setFavorite(postId, favorite);
    }

    @Override
    public void setRead(String postId, boolean read) {
        mInteractor.setRead(postId, read);
    }

    @Subscribe
    public void onEvent(PostDetailsEvent event) {
        switch (event.getEventType()) {
            case PostDetailsEvent.ERROR:
                onError(event.getMessage());
                break;
            case PostDetailsEvent.SUCESS:
                onSuccess(event.getUser());
                break;
        }
    }

    public void onError(String message) {
        if (mView != null) {
            mView.onError(message);
        }
    }

    public void onSuccess(User user) {
        if (mView != null) {
            mView.onSuccess(user);
        }
    }
}
