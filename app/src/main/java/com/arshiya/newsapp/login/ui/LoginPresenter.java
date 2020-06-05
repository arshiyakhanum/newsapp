package com.arshiya.newsapp.login.ui;

import android.content.Context;

import com.arshiya.newsapp.login.data.LoginDataSource;
import com.arshiya.newsapp.common.Result;
import com.arshiya.newsapp.login.data.local.UserSharedPreferences;
import com.arshiya.newsapp.login.data.model.LoggedInUser;
import com.moe.pushlibrary.MoEHelper;


public class LoginPresenter implements ILoginPresenter {

    private static final String TAG = "LoginPresenter";

    private LoginEventListener loginEventListener;

    public LoginPresenter(LoginEventListener loginEventListener) {
        this.loginEventListener = loginEventListener;
    }

    @Override
    public void login(Context context, String username, String password) {
        LoginDataSource dataSource = new LoginDataSource();

        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            onLoginSuccess(context, ((Result.Success<LoggedInUser>) result).getData());
        }
        if (result instanceof Result.Success) {
            loginEventListener.onLoginSuccess();
        } else {
            loginEventListener.onLoginFail(((Result.Error)result).getError());
        }
    }

    private void onLoginSuccess(Context context, LoggedInUser data) {
        //login
        MoEHelper.getInstance(context).setUniqueId(data.getUniqueId());

        //user attribute
        MoEHelper.getInstance(context).setUniqueId(data.getUniqueId());
        MoEHelper.getInstance(context).setFullName(data.getUserName());

        UserSharedPreferences sharedPreferences = UserSharedPreferences.getInstance(context);
        sharedPreferences.saveLoginStatus(true);
        sharedPreferences.saveUniqueId(data.getUniqueId());
        sharedPreferences.saveUserName(data.getUserName());
    }

}
