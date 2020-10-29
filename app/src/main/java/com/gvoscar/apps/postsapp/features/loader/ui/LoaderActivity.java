package com.gvoscar.apps.postsapp.features.loader.ui;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.common.GoogleApiAvailability;
import com.gvoscar.apps.postsapp.MainActivity;
import com.gvoscar.apps.postsapp.R;
import com.gvoscar.apps.postsapp.features.loader.presenters.LoaderPresenter;
import com.gvoscar.apps.postsapp.features.loader.presenters.LoaderPresenterImpl;
import com.gvoscar.apps.postsapp.features.login.ui.LoginActivity;
import com.jrummyapps.android.animations.Technique;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoaderActivity extends AppCompatActivity implements LoaderView {

    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.containerLoader)
    ConstraintLayout containerLoader;

    private static final String TAG = LoaderActivity.class.getSimpleName();
    private LoaderPresenter mPresenter;
    private CountDownTimer mTimer;
    private boolean hasInitiated = false;
    private boolean isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_loader);
        ButterKnife.bind(this);

        // Establecer pantalla completa.
        onFullscreenUI();

        // Iniciar presentador.
        initPresenter();

        // Inciar
        mTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // SimpleLog.i(TAG, "Segundos restantes: " + (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                // SimpleLog.i(TAG, "Termino.");
                // Iniciar verificacion de autenticacion.
                mPresenter.checkAuth();
            }
        };
    }

    private void onFullscreenUI() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                containerLoader.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        } catch (Exception e) {
            Log.e(TAG, "onFullscreenUI(). Ocurrio un error al intentar quitar barra de estado.    " + e.getLocalizedMessage(), e);
        }
    }

    private void initPresenter() {
        mPresenter = new LoaderPresenterImpl(this);
        mPresenter.onCreate();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mTimer.start();

        // Comprobar animacion no iniciada.
        if (!isStarted) {
            // Indicar que la animacion ha iniciado.
            isStarted = true;
            // Primera animación.
            onFirstAnimation();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return (keyCode == KeyEvent.KEYCODE_BACK);
    }

    private void onFirstAnimation() {
        Technique.PULSE.getComposer()
                .duration(800)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        // SimpleLog.i(TAG, "onFirstAnimation().onAnimationStart().  Primera animacion iniciada.");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // SimpleLog.i(TAG, "onFirstAnimation().onAnimationEnd().  Primera animacion terminada.");
                        // Comienza segunda animacion.
                        onSecondAnimation();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // SimpleLog.i(TAG, "onFirstAnimation().onAnimationCancel().  Primera animacion cancelada.");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // SimpleLog.i(TAG, "onFirstAnimation().onAnimationRepeat().  Primera animacion repetida.");
                    }
                })
                .playOn(imgLogo);
    }

    private void onSecondAnimation() {
        Technique.PULSE.getComposer()
                .duration(800)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        // SimpleLog.i(TAG, "onSecondAnimation().onAnimationStart().  Segunda animacion iniciada.");
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        // SimpleLog.i(TAG, "onSecondAnimation().onAnimationEnd().  Segunda animacion terminada.");
                        // Comienza tercera animacion.
                        onThirdAnimation();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                        // SimpleLog.i(TAG, "onSecondAnimation().onAnimationCancel().  Segunda animacion cancelada.");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                        // SimpleLog.i(TAG, "onSecondAnimation().onAnimationRepeat().  Segunda animacion repetida.");
                    }
                })
                .playOn(imgLogo);
    }

    /**
     * Comienza tercera animacion.
     */
    public void onThirdAnimation() {
        Technique.PULSE.getComposer()
                .duration(800)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        // SimpleLog.i(TAG, "onThirdAnimation().onAnimationStart().  Tercera animacion iniciada.");
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        // SimpleLog.i(TAG, "onThirdAnimation().onAnimationEnd().  Tercera animacion terminada.");
                        // Iniciar verificacion de autenticacion.
                        // verifyAuthentication();

                        // Indicar que la animacion ha finalizado.
                        isStarted = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                        // SimpleLog.i(TAG, "onThirdAnimation().onAnimationCancel().  Tercera animacion cancelada.");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                        // SimpleLog.i(TAG, "onThirdAnimation().onAnimationRepeat().  Tercera animacion cancelada.");
                    }
                })
                .playOn(imgLogo);
    }

    @Override
    public void onUnauthenticated() {
        Log.d(TAG, "Usuario no autenticado");
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onAuthenticated() {
        Log.d(TAG, "Usuario autenticado");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}