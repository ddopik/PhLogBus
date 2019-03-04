package com.example.ddopik.phlogbusiness.utiltes.networkstatus;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import com.example.ddopik.phlogbusiness.utiltes.rxeventbus.RxEvent;
import com.example.ddopik.phlogbusiness.utiltes.rxeventbus.RxEvent.Type;
import com.example.ddopik.phlogbusiness.utiltes.rxeventbus.RxEventBus;

@RequiresApi(api = Build.VERSION_CODES.M)
public class NetworkStateChangeManager {
    private final Application application;

    public NetworkStateChangeManager(Application application) {
        this.application = application;
    }

    public void listen() {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
        }
    }

    public void stopListening() {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    private NetworkRequest networkRequest = new NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            .build();

    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            super.onAvailable(network);
        }

        @Override
        public void onLosing(Network network, int maxMsToLive) {
            super.onLosing(network, maxMsToLive);
        }

        @Override
        public void onLost(Network network) {
            super.onLost(network);
            RxEventBus.getInstance().post(new RxEvent<>(Type.CONNECTIVITY, false));
        }

        @Override
        public void onUnavailable() {
            super.onUnavailable();
        }

        @Override
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            boolean connected = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
            RxEventBus.getInstance().post(new RxEvent<>(Type.CONNECTIVITY, connected));
        }

        @Override
        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties);
        }
    };
}
