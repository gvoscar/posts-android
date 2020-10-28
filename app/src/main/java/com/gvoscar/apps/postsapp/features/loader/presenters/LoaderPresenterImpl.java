package com.gvoscar.apps.postsapp.features.loader.presenters;


import android.content.Intent;

import com.gvoscar.apps.postsapp.features.loader.events.LoaderEvent;
import com.gvoscar.apps.postsapp.features.loader.interactors.LoaderInteractor;
import com.gvoscar.apps.postsapp.features.loader.interactors.LoaderInteractorImpl;
import com.gvoscar.apps.postsapp.features.loader.ui.LoaderView;
import com.gvoscar.apps.postsapp.features.login.ui.LoginActivity;
import com.gvoscar.apps.postsapp.libs.base.eventbus.EventBus;
import com.gvoscar.apps.postsapp.libs.base.eventbus.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

public class LoaderPresenterImpl implements LoaderPresenter {

    private static final String TAG = LoaderPresenterImpl.class.getSimpleName();
    private EventBus mBus;
    private LoaderView mView;

    private LoaderInteractor mInteractor;

    public LoaderPresenterImpl(LoaderView view) {
        this.mBus = GreenRobotEventBus.getInstance();
        this.mView = view;
        this.mInteractor = new LoaderInteractorImpl();
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
    public void checkAuth() {
        mInteractor.checkAuth();
    }

    @Subscribe
    public void onEvent(LoaderEvent event) {
        switch (event.getEventType()) {
            case LoaderEvent.UNAUTHENTICATED:
                onUnauthenticated();
                break;
            case LoaderEvent.AUTHENTICATED:
                onAuthenticated();
                break;
        }
    }

    public void onUnauthenticated() {
        if (this.mView != null) {
            this.mView.onUnauthenticated();
        }
    }

    public void onAuthenticated() {
        if (this.mView != null) {
            this.mView.onAuthenticated();
        }
    }
}
