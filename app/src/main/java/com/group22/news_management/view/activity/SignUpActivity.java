package com.group22.news_management.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.group22.news_management.presenter.SignUpPresenter;
import com.group22.news_management.presenter.impl.SignUpPresenterImpl;
import com.group22.news_management.view.SignUpView;
import com.group22.newsmanagerment.R;

public class SignUpActivity extends AppCompatActivity implements SignUpView {

    private Button btnSignUp;
    private EditText editTextUsername, editTextPassword;
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
        signUpPresenter = new SignUpPresenterImpl(this);
        btnSignUpEventOnClick();
    }

    private void initViews() {
        btnSignUp = findViewById(R.id.btnSignUp);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    private void btnSignUpEventOnClick() {
        btnSignUp.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            signUpPresenter.signUp(username, password);
        });
    }

    @Override
    public void onSuccess() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Thông báo!");
        alertDialog.setMessage("Đăng ký thành công");
        alertDialog.setPositiveButton("Xác nhận", (dialogInterface, i) -> {
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);
        });
        alertDialog.show();
    }

    @Override
    public void onError() {
        Toast.makeText(SignUpActivity.this,"Username Exists", Toast.LENGTH_SHORT).show();
    }
}