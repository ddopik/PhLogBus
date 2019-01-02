package com.example.ddopik.phlogbusiness.ui.brand.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.brand.view.BrandInnerActivityView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 11/12/2018.
 */
public class BrandInnerDataPresenterImpl implements BrandInnerPresenter {

    private String TAG=BrandInnerDataPresenterImpl.class.getSimpleName();
    private Context context;
    private BrandInnerActivityView brandInnerActivityView;

    public BrandInnerDataPresenterImpl(Context context, BrandInnerActivityView brandInnerActivityView) {
        this.context = context;
        this.brandInnerActivityView = brandInnerActivityView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getBrandInnerData(String brandId) {
        brandInnerActivityView.viewInnerBrandProgressBar(true);
        BaseNetworkApi.getBrandInnerData(PrefUtils.getBrandToken(context), brandId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(brandInnerResponse -> {
                         brandInnerActivityView.viewInnerBrandData(brandInnerResponse.data);

                    brandInnerActivityView.viewInnerBrandProgressBar(false);
                }, throwable -> {
                    brandInnerActivityView.viewInnerBrandProgressBar(false);
                    CustomErrorUtil.Companion.setError(context,TAG,throwable.getMessage());
                });

    }
}
