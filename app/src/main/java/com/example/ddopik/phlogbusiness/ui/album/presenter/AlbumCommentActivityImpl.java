package com.example.ddopik.phlogbusiness.ui.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;


import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.album.view.AlbumCommentActivityView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class AlbumCommentActivityImpl implements AlbumCommentActivityPresenter {

    private String TAG = AlbumCommentActivityImpl.class.getSimpleName();
    private Context context;
    private AlbumCommentActivityView albumCommentActivityView;

    public AlbumCommentActivityImpl(Context context, AlbumCommentActivityView albumCommentActivityView) {
        this.context = context;
        this.albumCommentActivityView = albumCommentActivityView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getImageComments(String imageId, String page) {
        albumCommentActivityView.viewAddCommentProgress(true);
        BaseNetworkApi.getImageComments(PrefUtils.getBrandToken(context), imageId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumImgCommentResponse -> {
                    albumCommentActivityView.viewPhotoComment(albumImgCommentResponse.data);
                    albumCommentActivityView.viewAddCommentProgress(false);
                }, throwable -> {

                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    albumCommentActivityView.viewAddCommentProgress(false);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void submitComment(String imageId, String comment) {
        albumCommentActivityView.viewAddCommentProgress(true);
        BaseNetworkApi.submitImageComment(PrefUtils.getBrandToken(context), imageId, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumImgCommentResponse -> {
                    albumCommentActivityView.viewPhotoComment(albumImgCommentResponse.data);
                    albumCommentActivityView.viewAddCommentProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable.toString());
                    albumCommentActivityView.viewAddCommentProgress(false);
                });

    }
}
