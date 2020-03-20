package com.arshiya.newsapp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Utility class for commonly used functions
 * Created by Arshiya on 2020-03-20.
 */
public class Util {

    /**
     * Verifies if there is an app to handle the given Intent
     * @param context - {@link Context}
     * @param intent - {@link Intent}
     * @return - true if application(s) exists to handle the intent, otherwise false
     */
    public static boolean isIntentSafe(@NonNull Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }

    /**
     * Opens the given url in browser
     * @param context - {@link Context}
     * @param url - non-empty url
     */
    public static void openInBrowser(@NonNull Context context, @NonNull String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (isIntentSafe(context, intent)) {
            context.startActivity(intent);
        }
    }
}
