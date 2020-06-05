package com.arshiya.newsapp.login.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPreferences {

    private static final String TAG = "UserSharedPreference";
    private static final String PREF_NAME = "USER_DATA_PREFERENCES";

    private static UserSharedPreferences sUserSharedPreferences;
    private SharedPreferences sharedPreferences;

    /**
     *  USER DATA KEYS
     */
    private final String IS_LOGGED_IN = "is_logged_in";

    private final String USER_NAME = "user_name";
    private final String UNIQUE_ID = "unique_id";


    private UserSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static UserSharedPreferences getInstance(Context context) {
        if (sUserSharedPreferences == null) {
            sUserSharedPreferences = new UserSharedPreferences(context);
        }
        return sUserSharedPreferences;
    }

    public void saveLoginStatus(boolean loggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN, loggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void saveUserName(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, username);
        editor.apply();
    }

    public void getUserName() {
        sharedPreferences.getString(USER_NAME, "");
    }

    public void saveUniqueId(String uniqueId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UNIQUE_ID, uniqueId);
        editor.apply();
    }

    public void getUniqueId() {
        sharedPreferences.getString(UNIQUE_ID, "");
    }
}
