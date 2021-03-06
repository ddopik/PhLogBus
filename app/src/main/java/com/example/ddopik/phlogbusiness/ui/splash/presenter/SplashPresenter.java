package com.example.ddopik.phlogbusiness.ui.splash.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.ui.splash.model.CheckVersionData;
import com.example.ddopik.phlogbusiness.ui.splash.model.CheckVersionReponse;
import com.example.ddopik.phlogbusiness.ui.splash.view.SplashView;

import io.reactivex.Observable;

public interface SplashPresenter {
    void setView(SplashView view);

    Observable<Boolean> sendFirebaseToken(Context context);

    Observable<CheckVersionData> checkAppVersion();
}
