package com.gvoscar.apps.postsapp.features.loader.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gvoscar.apps.postsapp.database.Database;
import com.gvoscar.apps.postsapp.features.loader.events.LoaderEvent;
import com.gvoscar.apps.postsapp.libs.base.eventbus.EventBus;
import com.gvoscar.apps.postsapp.libs.base.eventbus.GreenRobotEventBus;

import java.util.HashMap;


public class LoaderRepositoryImpl implements LoaderRepository {

    private static final String TAG = LoaderRepositoryImpl.class.getSimpleName();

    private FirebaseAuth mAuth;
    private Database mDatabase;
    private DatabaseReference mUserReference;


    public LoaderRepositoryImpl() {
        mDatabase = Database.getInstance();
        mUserReference = mDatabase.getUserReference();
        mAuth = mDatabase.getAuth();
    }

    @Override
    public void checkAuth() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            postEvent(LoaderEvent.UNAUTHENTICATED);
            return;
        }

        postEvent(LoaderEvent.AUTHENTICATED);
    }

    private void postEvent(int eventType) {
        Log.d(TAG, "postEvent().   Publicar evento.   " + eventType);
        LoaderEvent event = new LoaderEvent();
        event.setEventType(eventType);
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);
    }
}
