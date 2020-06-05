package com.arshiya.newsapp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.arshiya.newsapp.utils.Logger;

public class AppLifeCycleObserver implements LifecycleObserver {

    private static final String TAG = "AppLifeCycleObserver";

    private static int eventCount = 0;

    private static AppEventListener appEventListener;

    public void setAppEventListener(AppEventListener listener) {
        appEventListener = listener;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onAppCreate() {
        if (eventCount == 0 && appEventListener != null) {
            appEventListener.onAppOpen();
        }
        eventCount++;
        Logger.d(TAG, "onAppCreate: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppStart(){
        Logger.d(TAG, "onAppStart: " + eventCount);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAppResume() {
        Logger.d(TAG, "onAppResume: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onAppPause() {
        Logger.d(TAG, "onAppPause: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppStop() {
        Logger.d(TAG, "onAppStop: " + eventCount);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onAppDestroy() {
        eventCount--;
        Logger.d(TAG, "onAppDestroy: ");
        if (eventCount == 0 && appEventListener != null) {
            appEventListener.onAppClose();
        }
    }

    public interface AppEventListener {

        void onAppOpen();

        void onAppClose();
    }

}
