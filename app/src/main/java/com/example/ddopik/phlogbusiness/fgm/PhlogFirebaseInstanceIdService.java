package com.example.ddopik.phlogbusiness.fgm;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.ddopik.phlogbusiness.base.commonmodel.Device;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PhlogFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = PhlogFirebaseInstanceIdService.class.getSimpleName();

    @SuppressLint("CheckResult")
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("Firebase token", token);
        PrefUtils.saveFirebaseToken(getApplicationContext(), token);
        if (PrefUtils.isLoginProvided(getApplicationContext()))
            BaseNetworkApi.updateFirebaseToken(new Device(Utilities.getDeviceName(), true, PrefUtils.getBrandToken(getApplicationContext())))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        PrefUtils.setFirebaseTokenSentToServer(getApplicationContext(), true);
                    }, throwable -> {
                        PrefUtils.setFirebaseTokenSentToServer(getApplicationContext(), false);
                    });
    }
}
