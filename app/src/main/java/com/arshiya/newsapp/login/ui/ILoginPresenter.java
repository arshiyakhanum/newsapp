package com.arshiya.newsapp.login.ui;

import android.content.Context;

public interface ILoginPresenter {

    void login(Context context, String userName, String password);

    interface LoginEventListener {

        void onLoginFail(Exception error);

        void onLoginSuccess();
    }
}
