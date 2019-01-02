package com.example.ddopik.phlogbusiness.ui.profile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.profile.view.BrandProfileFragmentView;
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
                    brandProfileFragmentView.viewBrandProfileData(brandProfileResponse.brand);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context,TAG,throwable);
                    brandProfileFragmentView.viewBrandProfileProgress(false);
                });
    }
}
