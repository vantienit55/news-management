package com.group22.news_management.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.group22.news_management.database.DatabaseHelper;
import com.group22.news_management.model.UserModel;
import com.group22.news_management.presenter.LoginPresenter;
import com.group22.news_management.presenter.impl.LoginPresenterImpl;
import com.group22.news_management.utils.Session;
import com.group22.news_management.view.LoginView;
import com.group22.newsmanagerment.R;

public class LoginActivity extends AppCompatActivity implements FacebookCallback<LoginResult>, LoginView {

    private Button btnLogin;
    private LoginButton loginFacebookButton;
    private EditText editTextUsername, editTextPassword;
    private LoginPresenter loginPresenter;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        loginPresenter = new LoginPresenterImpl(this);
        callbackManager = CallbackManager.Factory.create();
        loginFacebookButton.registerCallback(callbackManager, this);
        btnLoginEvenOnClick();
    }

    private void initViews() {
        btnLogin = findViewById(R.id.btn_login);
        loginFacebookButton = findViewById(R.id.login_button);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    private void btnLoginEvenOnClick() {
        btnLogin.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            loginPresenter.login(username, password);
        });
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loginSuccess(UserModel userModel) {
        Toast.makeText(LoginActivity.this,"Xin chào " + userModel.getFullName() + "!", Toast.LENGTH_SHORT).show();
        Session session = new Session(this);
        session.put(userModel);
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginError() {
        Toast.makeText(LoginActivity.this,"Login Fail", Toast.LENGTH_SHORT).show();
    }

}