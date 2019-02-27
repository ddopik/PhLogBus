package com.example.ddopik.phlogbusiness.ui.profile.presenter;

import android.content.Context;

import io.reactivex.Observable;

public interface BrandProfilePresenter {
    void getProfileBrandData();

    void clearLoginData(Context context);

    Observable<Boolean> logout();
}
