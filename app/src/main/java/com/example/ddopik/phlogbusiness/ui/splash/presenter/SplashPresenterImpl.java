package com.example.ddopik.phlogbusiness.ui.splash.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.splash.view.SplashView;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;

import io.reactivex.Observable;

public class SplashPresenterImpl implements SplashPresenter {

    private SplashView view;

    @Override
    public void setView(SplashView view) {
        this.view = view;
    }

    @Override
    public Observable<Boolean> sendFirebaseToken(Context context) {
        return BaseNetworkApi.updateFirebaseToken(PrefUtils.getBrandToken(context), PrefUtils.getFirebaseToken(context))
                .map(response -> {
                    if (response != null) {
                        PrefUtils.setFirebaseTokenSentToServer(context, true);
                        return true;
                    } else {
                        PrefUtils.setFirebaseTokenSentToServer(context, false);
                        return false;
                    }
                });
    }
}
