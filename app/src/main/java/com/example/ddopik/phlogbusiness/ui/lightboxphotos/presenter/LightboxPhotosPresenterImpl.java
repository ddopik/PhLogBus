package com.example.ddopik.phlogbusiness.ui.lightboxphotos.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.lightboxphotos.view.LightboxPhotosView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LightboxPhotosPresenterImpl implements LightboxPhotosPresenter {

    public static final String TAG = LightboxPhotosPresenterImpl.class.getSimpleName();

    private LightboxPhotosView view;

    @Override
    public void setView(LightboxPhotosView view) {
        this.view = view;
    }

    @Override
    public void getLightboxPhotos(Context context, int lightBoxId, int page) {
        Disposable disposable = BaseNetworkApi.getLigtboxPhotos(lightBoxId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    view.addPhotos(response.getData());
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }
}
