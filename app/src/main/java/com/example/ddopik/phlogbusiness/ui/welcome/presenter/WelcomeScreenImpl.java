package com.example.ddopik.phlogbusiness.ui.welcome.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.Utiltes.ErrorUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.welcome.view.WelcomeView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WelcomeScreenImpl implements WelcomePresenter {


    private String TAG = WelcomeScreenImpl.class.getSimpleName();
    private WelcomeView welcomeView;
    private Context context;

    public WelcomeScreenImpl(WelcomeView welcomeView, Context context) {
        this.welcomeView = welcomeView;
        this.context = context;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getWelcomeSlidesImages() {

        BaseNetworkApi.getWelcomeSlidesImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(welcomeScreenResponse -> {
                    if (welcomeScreenResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        welcomeView.showWelcomeImageSlider(welcomeScreenResponse.initSlider);
                    } else {
                        ErrorUtils.setError(context, TAG, welcomeScreenResponse.toString(), welcomeScreenResponse.state);
                    }
                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable.toString());
                });

    }
}
