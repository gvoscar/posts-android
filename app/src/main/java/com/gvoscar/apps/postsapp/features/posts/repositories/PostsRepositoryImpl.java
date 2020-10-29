package com.gvoscar.apps.postsapp.features.posts.repositories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.gvoscar.apps.postsapp.apis.jsonplaceholder.JSONPlaceholderClient;
import com.gvoscar.apps.postsapp.apis.jsonplaceholder.JSONPlaceholderService;
import com.gvoscar.apps.postsapp.database.Database;
import com.gvoscar.apps.postsapp.features.posts.events.PostsEvent;
import com.gvoscar.apps.postsapp.features.posts.services.FetchPostsService;
import com.gvoscar.apps.postsapp.libs.base.eventbus.EventBus;
import com.gvoscar.apps.postsapp.libs.base.eventbus.GreenRobotEventBus;
import com.gvoscar.apps.postsapp.pojos.Post;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

public class PostsRepositoryImpl implements PostsRepository {

    private static final String TAG = PostsRepositoryImpl.class.getSimpleName();
    private Database mDatabase;
    private Context mContext;
    private boolean mRequested = false;
    private PostsResultReceiver mResultReceiver;
    private FragmentActivity mFragmentActivity;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    public PostsRepositoryImpl(FragmentActivity fragmentActivity, Context context) {
        Log.d(TAG, "PostsRepositoryImpl()");
        this.mFragmentActivity = fragmentActivity;
        this.mContext = context;
        this.mResultReceiver = new PostsResultReceiver(new Handler());
        this.mDatabase = Database.getInstance();
    }

    @Override
    public void getData() {
        Log.d(TAG, "getData()");
        if (!mRequested) {
            // Utilizar RXJava para consumir API.
            // getDataWithRX();

            // Utilizar Service para consumir API
            getDataWithService();

        }
    }

    private void getDataWithRX(){
        Log.d(TAG, "fetchDataRX()");
        JSONPlaceholderService service = new JSONPlaceholderClient().getJsonPlaceholderService();
        mCompositeDisposable.add(service.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Post>>() {
                    @Override
                    public void accept(List<Post> posts) throws Exception {
                        Log.d(TAG, "accept() ");
                        try {

                            int cant = posts.size();

                            Log.d(TAG, "____________________________________");
                            Log.d(TAG, "Cantidad de posts : " + cant);

                            mRequested = false;
                            postEvent(PostsEvent.DATA_LOADED, posts);

                        } catch (Exception e) {
                            Log.d(TAG, e.getLocalizedMessage(), e);
                        }
                    }
                })
        );
    }

    private void getDataWithService() {
        Log.d(TAG, "getDataWithService()");
        try {
            mRequested = true;
            Intent intent = new Intent(mFragmentActivity, FetchPostsService.class);
            intent.putExtra(FetchPostsService.RECEIVER_EXTRA, mResultReceiver);
            mFragmentActivity.startService(intent);

        } catch (Exception e) {
            Log.d(TAG, "ERROR: " + e.getLocalizedMessage(), e);
        }
    }

    private class PostsResultReceiver extends ResultReceiver {
        PostsResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Log.d(TAG, "onReceiveResult()");
            switch (resultCode) {
                case FetchPostsService.SUCCESS:
                    Log.d(TAG, "Hay datos");

                    List<Post> posts = (List<Post>) resultData.getSerializable(FetchPostsService.RESULT_DATA_EXTRA);

                    postEvent(PostsEvent.DATA_LOADED, posts);

                    break;
                case FetchPostsService.ERROR:
                    postEvent(PostsEvent.NOT_FOUND, "No hay datos");
                    break;

            }

            mRequested = false;
        }
    }

    private void postEvent(int eventType) {
        postEvent(eventType, null, null);
    }

    private void postEvent(int eventType, String message) {
        postEvent(eventType, message, null);
    }

    private void postEvent(int eventType, List<Post> posts) {
        postEvent(eventType, null, posts);
    }

    private void postEvent(int eventType, String message, List<Post> posts) {
        Log.d(TAG, "postEvent().    Evento publicado: "+ eventType);
        PostsEvent event = new PostsEvent(eventType, message, posts);
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);

        mCompositeDisposable.clear();
    }
}
