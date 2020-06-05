package com.arshiya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.arshiya.newsapp.AppLifeCycleObserver;
import com.arshiya.newsapp.articlelist.ui.activities.ArticleListActivity;
import com.arshiya.newsapp.login.data.local.UserSharedPreferences;
import com.arshiya.newsapp.login.ui.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLifecycle().addObserver(new AppLifeCycleObserver());

        final Class<? extends Activity> activityClass;

        if(UserSharedPreferences.getInstance(this).isLoggedIn())
            activityClass = ArticleListActivity.class;
        else
            activityClass = LoginActivity.class;

        Intent newActivity = new Intent(this, activityClass);
        startActivity(newActivity);
        finish();
    }
}