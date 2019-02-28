package com.example.ddopik.phlogbusiness.utiltes.networkstatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import com.example.ddopik.phlogbusiness.utiltes.rxeventbus.RxEvent;
import com.example.ddopik.phlogbusiness.utiltes.rxeventbus.RxEventBus;

public class NetworkChangeBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean connected = networkInfo != null && networkInfo.isConnected();
        RxEventBus.getInstance().post(new RxEvent<>(RxEvent.Type.CONNECTIVITY, connected));
    }
}
