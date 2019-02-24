package com.example.ddopik.phlogbusiness.ui.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivityView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.facebook.GraphRequest.TAG;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class AllAlbumImgPresnterImpl implements AllAlbumImgPresnter {

    private Context context;
    public AllAlbumImgActivityView allAlbumImgActivityView;

    public AllAlbumImgPresnterImpl(Context context, AllAlbumImgActivityView allAlbumImgActivityView) {
        this.context = context;
        this.allAlbumImgActivityView = allAlbumImgActivityView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void deleteImage(BaseImage baseImage) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);
        BaseNetworkApi.deleteImage(String.valueOf(baseImage.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseStateResponse -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    allAlbumImgActivityView.onImagePhotoGrapherDeleted(baseImage,true);
                }, throwable -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void likePhoto(String photoId) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);
        BaseNetworkApi.likePhotoGrapherPhotoPhoto(photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseStateResponse -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                }, throwable -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void saveToProfileImage(BaseImage baseImage) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);
        BaseNetworkApi.savePhoto(baseImage.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(savePhotoResponse -> {
                    allAlbumImgActivityView.onImageSavedToProfile(baseImage, true);
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                }, throwable -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);

                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void unSaveToProfileImage(BaseImage baseImage) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);
        BaseNetworkApi.unSavePhoto(baseImage.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(savePhotoResponse -> {
                    allAlbumImgActivityView.onImageSavedToProfile(baseImage, false);
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                }, throwable -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);

                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void followImagePhotoGrapher(BaseImage baseImage) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);

        BaseNetworkApi.followUser(String.valueOf(baseImage.photographer.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    allAlbumImgActivityView.onImagePhotoGrapherFollowed(baseImage, true);
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                });

    }

}
