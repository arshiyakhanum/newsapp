package com.arshiya.newsapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
/**
 * Network connection utility class
 * Created by khanuma
 */

public class ConnectivityUtils {

    private static final String TAG = "ConnectivityUtils";

    static ConnectivityManager mConnMgr;

    /**
     * initialzes ConnectivityUtils to create {@link ConnectivityManager} object
     * @param context - Application context
     */
    public static void initialize(Context context) {
         mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     *
     * @return {@link ConnectivityManager} object
     */
    public static ConnectivityManager getConnectivityManager() {
        return mConnMgr;
    }

    /**
     * Checks if active internet connection is available
     * @param context - Application context
     * @return - true if active network is available
     */
    public static boolean isNetworkAvailable(Context context) {
        if (mConnMgr != null) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = mConnMgr.getNetworkCapabilities(mConnMgr.getActiveNetwork());
                if (capabilities != null) {
                    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
                }
            } else {
                try {
                    NetworkInfo activeNetworkInfo = mConnMgr.getActiveNetworkInfo();
                    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
                } catch (Exception e) {
                    Logger.i(TAG, "" + e.getMessage());
                }
            }
        }
        return false;
    }
}
