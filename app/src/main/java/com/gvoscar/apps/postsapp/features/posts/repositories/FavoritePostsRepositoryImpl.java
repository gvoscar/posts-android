package com.gvoscar.apps.postsapp.features.posts.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gvoscar.apps.postsapp.database.Database;
import com.gvoscar.apps.postsapp.features.posts.events.PostsEvent;
import com.gvoscar.apps.postsapp.libs.base.eventbus.EventBus;
import com.gvoscar.apps.postsapp.libs.base.eventbus.GreenRobotEventBus;
import com.gvoscar.apps.postsapp.pojos.Post;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class FavoritePostsRepositoryImpl implements FavoritePostsRepository {

    private static final String TAG = FavoritePostsRepositoryImpl.class.getSimpleName();

    private Database mDatabase;
    private DatabaseReference reference;
    private ValueEventListener listener;

    public FavoritePostsRepositoryImpl() {
        mDatabase = Database.getInstance();
        reference = mDatabase.getPostsReference();
    }

    @Override
    public void subscribe() {
        Log.d(TAG, "subscribe()");
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {

                    List<Post> favorites = new ArrayList<>();

                    for (DataSnapshot data : snapshot.getChildren()) {
                        Post post = data.getValue(Post.class);
                        if (post.isFavorite()) {
                            favorites.add(post);
                        }
                    }

                    if (favorites.isEmpty()) {
                        postEvent(PostsEvent.NOT_FOUND, "No hay posts favoritos");
                    } else {
                        postEvent(PostsEvent.DATA_LOADED, favorites);
                    }

                } else {
                    postEvent(PostsEvent.NOT_FOUND, "No hay posts favoritos");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "OCURRIO UN ERROR: " + error.getDetails(), error.toException());
            }
        };

        reference.addValueEventListener(listener);
    }

    @Override
    public void unsubscribe() {
        Log.d(TAG, "unsubscribe()");
        if (listener != null) {
            reference.removeEventListener(listener);
            listener = null;
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
        Log.d(TAG, "postEvent().    Evento publicado: " + eventType);
        PostsEvent event = new PostsEvent(eventType, message, posts);
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);
    }
}
