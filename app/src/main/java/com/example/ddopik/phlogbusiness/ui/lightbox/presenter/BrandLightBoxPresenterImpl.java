package com.example.ddopik.phlogbusiness.ui.lightbox.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.Utiltes.ErrorUtil;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.lightbox.view.BrandLightBoxFragmentView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BrandLightBoxPresenterImpl implements BrandLightBoxPresnter {

    private static String TAG=BrandLightBoxPresenterImpl.class.getSimpleName();

    private BrandLightBoxFragmentView brandLightBoxFragmentView;
    private Context context;
    public BrandLightBoxPresenterImpl(BrandLightBoxFragmentView brandLightBoxFragmentView,Context context){
        this.brandLightBoxFragmentView=brandLightBoxFragmentView;
        this.context=context;

    }



    @SuppressLint("CheckResult")
    @Override
    public void getLightBoxes(int page) {
        brandLightBoxFragmentView.viewLightBoxProgress(true);
        BaseNetworkApi.getBrandLightBoxes(String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(brandLightBoxResponse -> {
                    brandLightBoxFragmentView.viewLightBoxes(brandLightBoxResponse.data);
                    brandLightBoxFragmentView.viewLightBoxProgress(false);
                }, throwable -> {
                    ErrorUtil.Companion.setError(context,TAG,throwable);
                    brandLightBoxFragmentView.viewLightBoxProgress(false);
                });
    }
}
