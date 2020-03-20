package com.arshiya.newsapp.network;

import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;

/**
 * Created by khanuma on 8/14/2019
 */
public interface INetworkCallback {

    /**
     * Called when the {@link android.net.ConnectivityManager.NetworkCallback}'s onAvailable is called
     * @param network - {@link Network}
     */
    void onAvailable(Network network);
    /**
     * Called when the {@link android.net.ConnectivityManager.NetworkCallback}'s onLosing is called
     * @param network - {@link Network}
     * @param maxMsToLive - The time in ms the framework will attempt to keep the
     *       network connected.  Note that the network may suffer ahard loss at any time.
     */
    void onLosing(Network network, int maxMsToLive);
    /**
     * Called when the {@link android.net.ConnectivityManager.NetworkCallback}'s onLost
     * is called
     * @param network - {@link Network}
     */
    void onLost(Network network) ;
    /**
     * Called when the {@link android.net.ConnectivityManager.NetworkCallback}'s onUnavailable
     * is called
     */
    void onUnavailable();
    /**
     * Called when the {@link android.net.ConnectivityManager.NetworkCallback}'s
     * onCapabilitiesChanged is called
     *
     * @param network The {@link Network} whose capabilities have changed.
     * @param networkCapabilities The new {@link NetworkCapabilities} for this
     *                            network.
     */
    void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities);
    /**
     * Called when the {@link android.net.ConnectivityManager.NetworkCallback}'s
     * onLinkPropertiesChanged is called
     * @param network The {@link Network} whose link properties have changed.
     * @param linkProperties The new {@link LinkProperties} for this network.
     */
    void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) ;
}
