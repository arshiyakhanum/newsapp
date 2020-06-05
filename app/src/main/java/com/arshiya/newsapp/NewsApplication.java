package com.arshiya.newsapp;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.arshiya.newsapp.utils.ConnectivityUtils;
import com.arshiya.newsapp.utils.Logger;
import com.google.firebase.FirebaseApp;
import com.moe.pushlibrary.MoEHelper;
import com.moengage.core.MoEngage;
import com.moengage.core.Properties;

/**
 * Created by Arshiya on 2020-03-19.
 */
public class NewsApplication extends Application implements LifecycleObserver, AppLifeCycleObserver.AppEventListener {

    private static final String TAG = NewsApplication.class.getSimpleName();
    private static NewsApplication sNewsApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.initialize(this);
        ConnectivityUtils.initialize(this);
        FirebaseApp.initializeApp(this);
        sNewsApplication = this;
        
        new AppLifeCycleObserver().setAppEventListener(this);
        initMoEngage();
    }

    private void initMoEngage() {
        MoEngage moEngage =
                new MoEngage.Builder(this, "U5LQ55SI54FZR7WO98BMSDNH")
                        .setNotificationSmallIcon(R.drawable.ic_stat_dvr)
                        .setNotificationLargeIcon(R.drawable.ic_launcher)
                        .optOutTokenRegistration()
                        .setLogLevel(com.moengage.core.Logger.VERBOSE)
                        .build();

        MoEngage.initialise(moEngage);
    }

    public static NewsApplication getInstance() {
     return sNewsApplication;
    }

    @Override
    public void onAppOpen() {
        Log.d(TAG, "onAppOpen: ");
        Properties properties = new Properties();
        properties.addAttribute("lifecycle", "open");
        MoEHelper.getInstance(this).trackEvent("app-event", properties);
    }

    @Override
    public void onAppClose() {
        Log.d(TAG, "onAppClose: ");
        Properties properties = new Properties();
        properties.addAttribute("lifecycle", "close");
        MoEHelper.getInstance(this).trackEvent("app-event", properties);
    }

    /**
     * Integrate MoEngage SDK into the news application(problem statement shared earlier). To add to it build support for login/logout
     * Track events for app open, close, article viewed, article saved.
     * Track user attributes on user login.
     * Integrate Firebase Cloud Messaging(do not use MoEngage's receiver)
     */
}
