package com.gvoscar.apps.postsapp.features.login.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.gvoscar.apps.postsapp.MainActivity;
import com.gvoscar.apps.postsapp.R;
import com.gvoscar.apps.postsapp.features.login.presenters.LoginPresenter;
import com.gvoscar.apps.postsapp.features.login.presenters.LoginPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.l1)
    LinearLayout l1;
    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.containerLogin)
    ConstraintLayout containerLogin;

    private LoginPresenter mPresenter;
    private String mEmail = null;
    private String mPassword = null;
    private ProgressDialog mProgressDialog;
    private Snackbar mSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mPresenter = new LoginPresenterImpl(this);
        mPresenter.onCreate();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isValidateEmail() {
        if (TextUtils.isEmpty(txtEmail.getText())) {
            txtEmail.setError("Ingrese usuario");
            showError("Ingrese usuario");
            return false;
        }
        if (TextUtils.isEmpty(txtPassword.getText())) {
            txtEmail.setError("Ingrese contraseña");
            showError("Ingrese contraseña");
            return false;
        }
        return true;
    }

    @OnClick(R.id.btnSubmit)
    public void onClickBtnSubmit() {
        if (isValidateEmail()) {
            Log.d(TAG, "INGRESAR");
            mEmail = txtEmail.getText().toString().trim() + "@postsapp.com";
            mPassword = txtPassword.getText().toString().trim();
            Log.d(TAG, "USUARIO: "+ mEmail);
            Log.d(TAG, "CLAVE: "+ mPassword);
            mPresenter.signInWithEmailAndPassword(mEmail, mPassword);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        setupInputs(true);
    }

    @Override
    public void disableInputs() {
        setupInputs(false);
    }

    private void setupInputs(boolean sw) {
        txtEmail.setEnabled(sw);
        txtPassword.setEnabled(sw);
    }

    @Override
    public void showProgress() {
        try {
            // Iniciar nuevo dialogo.
            mProgressDialog = new ProgressDialog(this);
            // Establecer que el dialogo no es cancelable con la tecla atras.
            mProgressDialog.setCancelable(false);
            // Establecer llave de tecla atras.
            mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    return i == KeyEvent.KEYCODE_BACK;
                }
            });
            mProgressDialog.setTitle("Iniciando sesion");
            mProgressDialog.setMessage("Por favor espere");
            mProgressDialog.show();
        } catch (Exception e) {
            Log.e(TAG, "showProgress().   Ocurrio un error al intentar mostrar dialogo de progreso.");
        }
    }

    @Override
    public void hideProgress() {
        try {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        } catch (Exception e) {
            Log.e(TAG, "hideProgress().   Ocurrio un error al intentar ocultar dialogo de progreso.");
        }
    }

    @Override
    public void onError(String message) {
        txtEmail.setError(message);
        showError(message);
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "SUCCESS: Usuario ingreso");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void showError(String str) {
        Log.d(TAG, "ERROR : " + str);
        Snackbar snackbar = Snackbar.make(containerLogin, str, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBarRed));
        snackbar.show();
    }
}