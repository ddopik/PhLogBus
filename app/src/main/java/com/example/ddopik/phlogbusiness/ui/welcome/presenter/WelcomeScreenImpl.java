package com.example.ddopik.phlogbusiness.ui.welcome.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
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

//                        welcomeView.showWelcomeImageSlider(welcomeScreenResponse.initSlider);

                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable.toString());
                });

    }
}
