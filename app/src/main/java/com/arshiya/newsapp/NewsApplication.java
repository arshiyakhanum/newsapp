package com.arshiya.newsapp;

import android.app.Application;

import com.arshiya.newsapp.utils.ConnectivityUtils;
import com.arshiya.newsapp.utils.Logger;
import com.google.firebase.FirebaseApp;

/**
 * Created by Arshiya on 2020-03-19.
 */
public class NewsApplication extends Application {

    private static final String TAG = NewsApplication.class.getSimpleName();
    private static NewsApplication sNewsApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.initialize(this);
        ConnectivityUtils.initialize(this);
        FirebaseApp.initializeApp(this);
        sNewsApplication = this;
    }

    public static NewsApplication getInstance() {
     return sNewsApplication;
    }
}
