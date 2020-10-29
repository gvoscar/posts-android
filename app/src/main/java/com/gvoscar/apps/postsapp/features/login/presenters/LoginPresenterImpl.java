package com.gvoscar.apps.postsapp.features.login.presenters;

import com.gvoscar.apps.postsapp.features.login.events.LoginEvent;
import com.gvoscar.apps.postsapp.features.login.interactors.LoginInteractor;
import com.gvoscar.apps.postsapp.features.login.interactors.LoginInteractorImpl;
import com.gvoscar.apps.postsapp.features.login.ui.LoginView;
import com.gvoscar.apps.postsapp.libs.base.eventbus.EventBus;
import com.gvoscar.apps.postsapp.libs.base.eventbus.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

public class LoginPresenterImpl implements LoginPresenter {

    static final String TAG = LoginPresenterImpl.class.getSimpleName();

    private EventBus mEventBus;
    private LoginView mView;
    private LoginInteractor mInteractor;

    public LoginPresenterImpl(LoginView view) {
        this.mEventBus = GreenRobotEventBus.getInstance();
        this.mView = view;
        this.mInteractor = new LoginInteractorImpl();
    }

    @Override
    public void onCreate() {
        this.mEventBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.mEventBus.unregister(this);
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password) {
        if (mView != null) {
            mView.disableInputs();
            mView.showProgress();
        }
        this.mInteractor.signInWithEmailAndPassword(email, password);
    }

    @Subscribe
    public void onEvent(LoginEvent event) {
        switch (event.getEventType()) {
            case LoginEvent.ERROR:
                onError(event.getEventMessage());
                break;
            case LoginEvent.SUCCESS:
                onSuccess();
                break;

        }
    }

    public void onError(String message) {
        if (mView != null) {
            mView.enableInputs();
            mView.hideProgress();
            mView.onError(message);
        }
    }

    public void onSuccess() {
        if (mView != null) {
            mView.hideProgress();
            mView.onSuccess();
        }
    }
}
