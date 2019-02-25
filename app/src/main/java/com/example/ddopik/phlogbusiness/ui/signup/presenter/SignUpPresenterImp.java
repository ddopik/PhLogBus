package com.example.ddopik.phlogbusiness.ui.signup.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.signup.view.SignUpView;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.HashMap;

public class SignUpPresenterImp implements SignUpPresenter {

    private SignUpView signUpView;
    private String TAG = SignUpPresenterImp.class.getSimpleName();
    private Context context;

    public SignUpPresenterImp(Context context, SignUpView signUpView) {
        this.signUpView = signUpView;
        this.context=context;
    }


    @SuppressLint("CheckResult")
    @Override
    public void signUpUser(HashMap<String, String> signUpData) {

        signUpData.put("firebase_token", PrefUtils.getFirebaseToken(context));
        BaseNetworkApi.signUpUser(signUpData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(signUpResponse -> {
                    signUpView.signUpSuccess();
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    public void getAllIndustries() {
        BaseNetworkApi.getAllIndustries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allIndustriesResponse -> {
                    signUpView.showIndustries(allIndustriesResponse.industryList);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }


}
