package com.gvoscar.apps.postsapp.features.login.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.gvoscar.apps.postsapp.database.Database;
import com.gvoscar.apps.postsapp.features.login.events.LoginEvent;
import com.gvoscar.apps.postsapp.libs.base.eventbus.EventBus;
import com.gvoscar.apps.postsapp.libs.base.eventbus.GreenRobotEventBus;

public class LoginRepositoryImpl implements LoginRepository {

    private static final String TAG = LoginRepositoryImpl.class.getSimpleName();
    private FirebaseAuth mAuth;
    private Database mDatabase;

    public LoginRepositoryImpl() {
        mDatabase = Database.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password) {
        try {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Comprobar usuario.
                                postEvent(LoginEvent.SUCCESS);
                            } else {
                                Log.e(TAG, "signInWithEmailAndPassword().   Ocurrio un error al iniciar sesion.  " + task.getException().getLocalizedMessage(), task.getException());
                                if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                    postEvet(LoginEvent.ERROR, "Usuario y contraseña incorrectos");
                                } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    postEvet(LoginEvent.ERROR, "Usuario y contraseña incorrectos");
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "signInWithEmailAndPassword().   Ocurrio un error al intentar iniciar sesion.  " + e.getLocalizedMessage(), e);
            postEvet(LoginEvent.ERROR, "Ocurrio un error inesperado");
        }
    }

    private void postEvent(int eventType) {
        postEvet(eventType, null);
    }

    private void postEvet(int eventType, String eventMessage) {
        Log.i(TAG, "postEvet().   Publicar evento.    " + eventType);
        LoginEvent event = new LoginEvent();
        event.setEventType(eventType);
        if (eventMessage != null) {
            event.setEventMessage(eventMessage);
        }
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);
    }
}
