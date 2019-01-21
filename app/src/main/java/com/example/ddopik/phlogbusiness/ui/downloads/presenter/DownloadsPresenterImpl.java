package com.example.ddopik.phlogbusiness.ui.downloads.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.downloads.view.DownloadsView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DownloadsPresenterImpl implements DownloadsPresenter {

    public static final String TAG = DownloadsPresenterImpl.class.getSimpleName();

    private DownloadsView view;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void setView(DownloadsView view) {
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getDownloads(Context context) {
        BaseNetworkApi.getDownloads(PrefUtils.getBrandToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    view.setDownloads(response.getData());
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }
}
