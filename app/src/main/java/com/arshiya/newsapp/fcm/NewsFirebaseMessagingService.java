package com.arshiya.newsapp.fcm;

import androidx.annotation.NonNull;

import com.arshiya.newsapp.articlelist.domain.Article;
import com.arshiya.newsapp.articlelist.parser.ArticlesParser;
import com.arshiya.newsapp.utils.Logger;
import com.arshiya.newsapp.utils.NotificationHandler;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arshiya on 2020-03-20.
 */
public class NewsFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = NewsFirebaseMessagingService.class.getSimpleName();

    public NewsFirebaseMessagingService() {
        super();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Logger.d(TAG, "From: " + remoteMessage.getFrom());

        Map<String, String> data = remoteMessage.getData();


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Logger.d(TAG, "Message data payload: " + remoteMessage.getData());
            Article article = new ArticlesParser().parse(new HashMap<>(remoteMessage.getData()));
            Logger.d(TAG, "Message data parsed: " + article.toString());
            new NotificationHandler()
                    .notifyArticle(getApplicationContext(), article);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Logger.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(@NonNull String s) {
        super.onMessageSent(s);

    }

    @Override
    public void onSendError(@NonNull String s, @NonNull Exception e) {
        super.onSendError(s, e);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Logger.d(TAG, "Refreshed token: " + s);
    }
}
