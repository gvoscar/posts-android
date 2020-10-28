package com.gvoscar.apps.postsapp.database;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Database {
    private static final String TAG = Database.class.getSimpleName();
    private DatabaseReference database;

    private static final String ORIENTATION = SetupDatabase.getOrientation();
    private static final String USERS = "users";
    private static final String POSTS = "posts";
    private static final String DETAILS = "details";

    public Database() {
        database = FirebaseDatabase.getInstance().getReference().child(ORIENTATION);
    }

    private static class SingletonHolder {
        private static final Database INSTANCE = new Database();
    }


    public static Database getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    public String getEmail() {
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        return auth != null ? auth.getEmail() : null;
    }

    public String getUid() {
        String uid = null;
        if (getEmail() != null) {
            uid = getAuth().getUid();
        }
        return uid;
    }

    public DatabaseReference getUsersReference() {
        DatabaseReference reference = null;
        if (getEmail() != null) {
            reference = database.getRoot()
                    .child(ORIENTATION)
                    .child(USERS);
        }
        return reference;
    }

    public DatabaseReference getUserReference() {
        DatabaseReference reference = null;
        if (getEmail() != null) {
            reference = database.getRoot()
                    .child(ORIENTATION)
                    .child(USERS)
                    .child(getUid());
        }
        return reference;
    }

    public DatabaseReference getPostsReference() {
        DatabaseReference reference = null;
        if (getEmail() != null) {
            reference = database.getRoot()
                    .child(ORIENTATION)
                    .child(POSTS);
        }
        return reference;
    }

    public DatabaseReference getPostReference(String postId) {
        DatabaseReference reference = null;
        if (getEmail() != null) {
            reference = database.getRoot()
                    .child(ORIENTATION)
                    .child(POSTS)
                    .child(postId);
        }
        return reference;
    }

    public DatabaseReference getDetailsReference() {
        DatabaseReference reference = null;
        if (getEmail() != null) {
            reference = database.getRoot()
                    .child(ORIENTATION)
                    .child(DETAILS);
        }
        return reference;
    }

    public DatabaseReference getPostDetailReference(String postId) {
        DatabaseReference reference = null;
        if (getEmail() != null) {
            reference = database.getRoot()
                    .child(ORIENTATION)
                    .child(DETAILS)
                    .child(postId);
        }
        return reference;
    }
}
