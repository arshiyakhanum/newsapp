package com.arshiya.newsapp.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.arshiya.newsapp.R;
import com.arshiya.newsapp.articlelist.domain.Article;

/**
 *
 * Created by Arshiya on 2020-03-20.
 */
public class NotificationHandler {

    private static final String CHANNEL_ID = "com.arshiya.newsapp.newarticle";

    private static final int NOTIFICATION_ID = 1;

    private Notification mNotification;
    private NotificationManager mNotificationManager;

    /**
     * Create and register Notification channel for Android version 8 and higher
     * @param context - Application context
     */
    private void createNotificationChannel(@NonNull Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            mNotificationManager.createNotificationChannel(channel);
        }

    }

    /**
     * Build article notification and return
     * @param context - Application context
     * @param article - {@link Article}
     * @return - {@link Notification}
     */
    private void buildNotification(@NonNull Context context, Article article) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(article.getTitle())
                .setContentText(article.getContent())
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        //todo create PI to open browser on notification click

        mNotification =  builder.build();
    }

    public void notifyArticle(@NonNull Context context, Article article) {
        buildNotification(context, article);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel(context);
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }
}
