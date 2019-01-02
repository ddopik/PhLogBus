package com.example.ddopik.phlogbusiness.ui.lightbox.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.lightbox.view.BrandLightBoxFragmentView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.HashMap;

public class BrandLightBoxPresenterImpl implements BrandLightBoxPresenter {

    private static String TAG=BrandLightBoxPresenterImpl.class.getSimpleName();

    private BrandLightBoxFragmentView brandLightBoxFragmentView;
    private Context context;
    public BrandLightBoxPresenterImpl(BrandLightBoxFragmentView brandLightBoxFragmentView,Context context){
        this.brandLightBoxFragmentView=brandLightBoxFragmentView;
        this.context=context;

    }



    @SuppressLint("CheckResult")
    @Override
    public void getLightBoxes(int page,boolean forceReFresh) {
        brandLightBoxFragmentView.viewLightBoxProgress(true);
        BaseNetworkApi.getBrandLightBoxes(String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(brandLightBoxResponse -> {
                    brandLightBoxFragmentView.viewLightBoxes(brandLightBoxResponse.data,forceReFresh);
                    brandLightBoxFragmentView.viewLightBoxProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context,TAG,throwable);
                    brandLightBoxFragmentView.viewLightBoxProgress(false);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void deleteLightBox(int id) {
        brandLightBoxFragmentView.viewLightBoxProgress(true);
        BaseNetworkApi.deleteLightBox(String.valueOf(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(deleteLightBoxResponse -> {
                    brandLightBoxFragmentView.showMessage(context.getResources().getString(R.string.light_box_deleted));
                    brandLightBoxFragmentView.viewLightBoxProgress(false);
                    brandLightBoxFragmentView.onLightBoxLightDeleted();
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(
                            context,TAG,throwable);
                    brandLightBoxFragmentView.viewLightBoxProgress(false);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void addLightBox(String name,String description) {

        brandLightBoxFragmentView.viewLightBoxProgress(true);
        HashMap<String,String> data=new HashMap<String, String>();
        data.put("name",name);
        data.put("description",description);
        BaseNetworkApi.addLightBox(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addLightBoxResponse -> {
                    brandLightBoxFragmentView.viewLightBoxProgress(false);
                    brandLightBoxFragmentView.showMessage(addLightBoxResponse.msg);
                    brandLightBoxFragmentView.onLightBoxLightDeleted();
                },throwable -> {
                    brandLightBoxFragmentView.viewLightBoxProgress(false);
                    CustomErrorUtil.Companion.setError(context,TAG,throwable);
                });
    }
}
