package com.example.ddopik.phlogbusiness.ui.profile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Device;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.profile.view.BrandProfileFragmentView;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;

import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class BrandProfilePresenterImpl implements BrandProfilePresenter {

    private static String TAG=BrandProfilePresenterImpl.class.getSimpleName();
    private Context context;
    private BrandProfileFragmentView brandProfileFragmentView;

    public BrandProfilePresenterImpl(Context context, BrandProfileFragmentView brandProfileFragmentView) {
        this.brandProfileFragmentView = brandProfileFragmentView;
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getProfileBrandData() {

        brandProfileFragmentView.viewBrandProfileProgress(true);
        BaseNetworkApi.getBrandProfileData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(brandProfileResponse -> {
                    brandProfileFragmentView.viewBrandProfileProgress(false);
//                    Log.e(TAG, brandProfileResponse);
                    brandProfileFragmentView.viewBrandProfileData(brandProfileResponse.brand);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context,TAG,throwable);
                    brandProfileFragmentView.viewBrandProfileProgress(false);
                });
    }

    @Override
    public void clearLoginData(Context context) {
        PrefUtils.setLoginState(context, false);
        PrefUtils.setBrandToken(context, null);
    }

    @SuppressLint("CheckResult")
    @Override
    public void logout() {
        BaseNetworkApi.updateFirebaseToken(new Device(Utilities.getDeviceName(), false, PrefUtils.getFirebaseToken(context)), PrefUtils.getBrandToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null)
                        brandProfileFragmentView.logoutSuccess();
                    else
                        brandProfileFragmentView.viewMessage(context.getString(R.string.error_logout));
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    brandProfileFragmentView.viewMessage(context.getString(R.string.error_logout));
                });
    }
}
