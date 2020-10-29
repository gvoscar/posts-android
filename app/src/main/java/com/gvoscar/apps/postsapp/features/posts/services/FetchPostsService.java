package com.gvoscar.apps.postsapp.features.posts.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

import com.gvoscar.apps.postsapp.apis.jsonplaceholder.JSONPlaceholderClient;
import com.gvoscar.apps.postsapp.apis.jsonplaceholder.JSONPlaceholderService;
import com.gvoscar.apps.postsapp.apis.jsonplaceholder.PostsResponse;
import com.gvoscar.apps.postsapp.pojos.Post;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchPostsService extends IntentService implements Callback<List<Post>>{

    private static final String TAG = FetchPostsService.class.getSimpleName();

    private static final String PACKAGE_NAME = "com.gvoscar.apps.postsapp.features.posts.services";
    public static final String RECEIVER_EXTRA = PACKAGE_NAME + ".RECEIVER_EXTRA";
    public static final String RESULT_CODE_EXTRA = PACKAGE_NAME + ".RESULT_CODE_EXTRA";
    public static final String RESULT_DATA_EXTRA = PACKAGE_NAME + ".RESULT_DATA_EXTRA";

    public static final int ERROR = 100;
    public static final int SUCCESS = 200;

    private ResultReceiver mReceiver;
    private List<Post> posts = null;
    private boolean retry;


    public FetchPostsService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "INICIA SERVICIO DE POSTS");
        mReceiver = intent.getParcelableExtra(RECEIVER_EXTRA);

        if (mReceiver == null) {
            Log.wtf(TAG, "RECEPTOR NO ENCONTRADO: No se puede enviar el resultado.");
            return;
        }

        executeApi();
    }

    private void executeApi() {
        Log.d(TAG, "Ejecutar API");
        try {
            JSONPlaceholderService service = new JSONPlaceholderClient().getJsonPlaceholderService();

            Call<List<Post>> call = service.posts();
            call.enqueue(this);


        } catch (Exception e) {
            Log.d(TAG, "ERROR: " + e.getLocalizedMessage(), e);
        }
    }

    private void deliverResultToReceiver(int resultCode, List<Post> result) {
        Log.d(TAG, "ENVIAR RESULTADO : " + resultCode);
        Bundle bundle = new Bundle();
        bundle.putInt(RESULT_CODE_EXTRA, resultCode);
        if (result != null) {
            bundle.putSerializable(RESULT_DATA_EXTRA, (Serializable) result);
        }

        mReceiver.send(resultCode, bundle);
    }


    //
    //
    //

    @Override
    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
        Log.d(TAG, "RESULTADO RECIBIDO");

        try {
            Log.d(TAG, "ESTADO : " + response.code());

            posts = response.body();
            if (posts == null) {
                Log.d(TAG, "No hay datos");
                return;
            }

            Log.d(TAG, "DATOS  : " + posts.toString());


            int cant = posts.size();

            Log.d(TAG, "____________________________________");
            Log.d(TAG, "Cantidad de posts : " + cant);


            deliverResultToReceiver(SUCCESS, posts);
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void onFailure(Call<List<Post>> call, Throwable t) {
        Log.d(TAG, "OCURRIO UN ERROR : " + t.getLocalizedMessage(), t);
        if (!retry) {
            retry = true;
            Log.d(TAG, "Reintentar");
            // directionApi();
        } else {
            deliverResultToReceiver(ERROR, null);
        }
    }

}