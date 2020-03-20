package com.arshiya.newsapp.network;

import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.arshiya.newsapp.NewsApplication;
import com.arshiya.newsapp.utils.ConnectivityUtils;
import com.arshiya.newsapp.utils.Logger;

import java.util.ArrayList;

/**
 * Created by khanuma on 8/14/2019
 * Base Class to registered to {@link ConnectivityManager} NetworkCallback
 * All the other app components that require system default network change callbacks must register {@link INetworkCallback}
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackManager extends ConnectivityManager.NetworkCallback {

    private static final String TAG = NetworkCallbackManager.class.getSimpleName();
    private static NetworkCallbackManager sNetworkCallbackManager;
    private static ArrayList<INetworkCallback> sINetworkCallbacks = new ArrayList<>();
    private static boolean isFirstInit = true;

    private NetworkCallbackManager() {}

    /**
     * Initializes singleton {@link NetworkCallbackManager} object
     * and registers to receive notifications about changes in system default network.
     */
    private static void init() {
        Logger.d(TAG, "init::" + (sNetworkCallbackManager == null));
        sNetworkCallbackManager = new NetworkCallbackManager();
        registerDefaultNetworkCallback();
    }

    /**
     * initializes callback and Returns static reference to {@link NetworkCallbackManager}
     * @return - {@link NetworkCallbackManager} object
     */
    public static NetworkCallbackManager getInstance() {
        if (isFirstInit) {
            init();
        }
        return sNetworkCallbackManager;
    }

    /**
     * Registers {@link NetworkCallbackManager} to receive callbacks about system default network change.
     */
    private static void registerDefaultNetworkCallback() {
        Logger.d(TAG, "registerDefaultNetworkCallback: sdk version: " + Build.VERSION.SDK_INT);
        isFirstInit = false;
        ConnectivityManager connectivityManager = ConnectivityUtils.getConnectivityManager();
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(sNetworkCallbackManager);
            } else {
                //create network request, class will get callback only to change related to specified TransportType
                NetworkRequest.Builder networkRequest = new NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
                connectivityManager.registerNetworkCallback(networkRequest.build(), sNetworkCallbackManager);
            }
        }
    }

    /**
     * Unregisters {@code NetworkCallback} and releases references
     */
    private static void unregisterDefaultNetworkCallback() {
        Logger.d(TAG, "unregisterDefaultNetworkCallback");
        if (sNetworkCallbackManager != null) {
            ConnectivityManager connectivityManager = ConnectivityUtils.getConnectivityManager();

            if (connectivityManager != null) {
                try {
                    connectivityManager.unregisterNetworkCallback(sNetworkCallbackManager);
                } catch (Exception ex) {
                    Logger.e(TAG, "unregisterDefaultNetworkCallback: " + ex.getMessage(), ex);
                } finally {
                    sINetworkCallbacks = null;
                }
            }
        }
    }

    /**
     * Registers {@link INetworkCallback} to receive notification about changes in system default network.
     * @param callback - {@link INetworkCallback}
     */
    public void register(INetworkCallback callback) {
        if (callback == null) {
            Logger.e(TAG, "cannot register null object.");
            return;
        }
        if (!sINetworkCallbacks.contains(callback)) {
            sINetworkCallbacks.add(callback);

            if (ConnectivityUtils.isNetworkAvailable(NewsApplication.getInstance().getApplicationContext())) {
                ConnectivityManager connectivityManager = ConnectivityUtils.getConnectivityManager();
                if (connectivityManager != null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        callback.onAvailable(connectivityManager.getActiveNetwork());
                    } else {
                        callback.onAvailable(null);
                    }
            } else {
                callback.onLost(null);
            }
        }
    }

    /**
     * Unregisters {@link INetworkCallback}
     * @param callback - {@link INetworkCallback} instance
     */
    public void unregister(@NonNull INetworkCallback callback) {
        if (sINetworkCallbacks.contains(callback)) {
            sINetworkCallbacks.remove(callback);
        } else {
            Logger.e(TAG, "unregister():: Receiver not registered");
        }
    }

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Logger.d(TAG, "onAvailable: ");
        for (INetworkCallback callback: sINetworkCallbacks) {
            if (callback != null) {
                callback.onAvailable(network);
            }
        }
    }

    @Override
    public void onLosing(Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
        Logger.d(TAG, "onLosing: ");
        for (INetworkCallback callback: sINetworkCallbacks) {
            if (callback != null) {
                callback.onLosing(network, maxMsToLive);
            }
        }
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Logger.d(TAG, "onLost: ");
        for (INetworkCallback callback: sINetworkCallbacks) {
            if (callback != null) {
                callback.onLost(network);
            }
        }
    }

    @Override
    public void onUnavailable() {
        super.onUnavailable();
        Logger.d(TAG, "onUnavailable: ");
        for (INetworkCallback callback: sINetworkCallbacks) {
            if (callback != null) {
                callback.onUnavailable();
            }
        }
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        Logger.d(TAG, "onCapabilitiesChanged: ");
        for (INetworkCallback callback: sINetworkCallbacks) {
            if (callback != null) {
                callback.onCapabilitiesChanged(network, networkCapabilities);
            }
        }
    }

    @Override
    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
        Logger.d(TAG, "onLinkPropertiesChanged: ");
        for (INetworkCallback callback: sINetworkCallbacks) {
            if (callback != null) {
                callback.onLinkPropertiesChanged(network, linkProperties);
            }
        }
    }
}
