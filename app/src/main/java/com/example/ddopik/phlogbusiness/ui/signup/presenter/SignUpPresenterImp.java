package com.example.ddopik.phlogbusiness.ui.signup.presenter;

import android.annotation.SuppressLint;
import android.util.Log;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.signup.view.SignUpView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.HashMap;

public class SignUpPresenterImp implements SignUpPresenter {

    private SignUpView signUpView;
    private String TAG = SignUpPresenterImp.class.getSimpleName();

    public SignUpPresenterImp(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    @Override
    public void getAllCounters() {
        requestAllCounters();
    }

    @SuppressLint("CheckResult")
    @Override
    public void signUpUser(HashMap<String, String> signUpData) {
        BaseNetworkApi.signUpUser(signUpData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(signUpResponse -> {
                    Log.e(TAG,"signUpUser() --->"+signUpResponse.toString());
                    if (signUpResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        signUpView.showMessage(signUpResponse.token);
                        signUpView.showMessage("What should we do now");
                    } else if (signUpResponse.state.equals(BaseNetworkApi.STATUS_IN_VALID_RESPONSE)){
                        signUpView.showMessage(signUpResponse.message);
                    }
                }, throwable -> {
                    signUpView.showMessage(throwable.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    private void requestAllCounters() {
        BaseNetworkApi.getAllCounters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allCountersRepose -> {
                    signUpView.showCounters(allCountersRepose.countries);
                }, throwable -> {
                    Log.e(TAG, "requestAllCounters() ---->Errot --->" + throwable.getMessage());
                });
    }


}
