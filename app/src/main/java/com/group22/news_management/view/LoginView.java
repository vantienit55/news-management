package com.group22.news_management.view;

import android.content.Context;

import com.group22.news_management.model.UserModel;

public interface LoginView {

    void loginSuccess(UserModel userModel);
    void loginError();
}
