package com.gvoscar.apps.postsapp.features.details.repositories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DatabaseReference;
import com.gvoscar.apps.postsapp.apis.jsonplaceholder.JSONPlaceholderClient;
import com.gvoscar.apps.postsapp.apis.jsonplaceholder.JSONPlaceholderService;
import com.gvoscar.apps.postsapp.database.Database;
import com.gvoscar.apps.postsapp.features.details.events.PostDetailsEvent;
import com.gvoscar.apps.postsapp.features.posts.events.PostsEvent;
import com.gvoscar.apps.postsapp.features.posts.repositories.PostsRepositoryImpl;
import com.gvoscar.apps.postsapp.features.posts.services.FetchPostsService;
import com.gvoscar.apps.postsapp.libs.base.eventbus.EventBus;
import com.gvoscar.apps.postsapp.libs.base.eventbus.GreenRobotEventBus;
import com.gvoscar.apps.postsapp.pojos.Post;
import com.gvoscar.apps.postsapp.pojos.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PostDetailsRepositoryImpl implements PostDetailsRepository {

    private static final String TAG = PostDetailsRepositoryImpl.class.getSimpleName();
    private Database mDatabase;

    private boolean mRequested = false;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public PostDetailsRepositoryImpl() {
        Log.d(TAG, "PostDetailsRepositoryImpl()");
        this.mDatabase = Database.getInstance();
    }

    @Override
    public void getData(String userdId) {
        Log.d(TAG, "getData() userdId: " + userdId);
        if (!mRequested) {
            // Utilizar RXJava para consumir API.
            getDataWithRX(userdId);
        }
    }

    @Override
    public void setFavorite(String postId, boolean favorite) {
        Map<String, Object> map = new HashMap<>();
        map.put("favorite", favorite);
        DatabaseReference reference = mDatabase.getPostReference(postId);
        reference.updateChildren(map);
    }

    @Override
    public void setRead(String postId, boolean read) {
        Map<String, Object> map = new HashMap<>();
        map.put("readed", read);
        DatabaseReference reference = mDatabase.getPostReference(postId);
        reference.updateChildren(map);
    }

    private void getDataWithRX(String userdId) {
        Log.d(TAG, "fetchDataRX()");
        JSONPlaceholderService service = new JSONPlaceholderClient().getJsonPlaceholderService();
        mCompositeDisposable.add(service.getUsersById(userdId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        Log.d(TAG, "accept()");
                        try {
                            Log.d(TAG, user.toString());
                            mRequested = false;
                            postEvent(PostDetailsEvent.SUCESS, user);

                        } catch (Exception e) {
                            Log.d(TAG, e.getLocalizedMessage(), e);
                        }
                    }
                })
        );
    }

    private void postEvent(int eventType) {
        postEvent(eventType, null, null);
    }

    private void postEvent(int eventType, String message) {
        postEvent(eventType, message, null);
    }

    private void postEvent(int eventType, User user) {
        postEvent(eventType, null, user);
    }

    private void postEvent(int eventType, String message, User user) {
        Log.d(TAG, "postEvent().    Evento publicado: " + eventType);
        PostDetailsEvent event = new PostDetailsEvent(eventType);
        event.setMessage(message);
        event.setUser(user);
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);

        mCompositeDisposable.clear();
    }
}
