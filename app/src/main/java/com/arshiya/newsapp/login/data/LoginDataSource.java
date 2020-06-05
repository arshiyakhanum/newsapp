package com.arshiya.newsapp.login.data;

import android.text.TextUtils;

import com.arshiya.newsapp.common.Result;
import com.arshiya.newsapp.login.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                return new Result.Error(new IOException("Error! Invalid username/password"));
            }

            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            username);
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
    }
}