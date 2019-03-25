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
public class AllAlbumImgPresenterImpl implements AllAlbumImgPresenter {

    private Context context;
    public AllAlbumImgActivityView allAlbumImgActivityView;

    public AllAlbumImgPresenterImpl(Context context, AllAlbumImgActivityView allAlbumImgActivityView) {
        this.context = context;
        this.allAlbumImgActivityView = allAlbumImgActivityView;
    }


    @SuppressLint("CheckResult")
    @Override
    public void likePhoto(BaseImage baseImage) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);


        if (baseImage.isLiked) {
            BaseNetworkApi.unlikeImage(String.valueOf(baseImage.id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseStateResponse -> {
                        allAlbumImgActivityView.viewAlbumImageListProgress(false);
                        allAlbumImgActivityView.onImagePhotoGrapherLiked(baseImage.id,false);
                    }, throwable -> {
                        allAlbumImgActivityView.viewAlbumImageListProgress(false);
                        CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    });
        } else {
            BaseNetworkApi.likeImage(String.valueOf(baseImage.id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseStateResponse -> {
                        allAlbumImgActivityView.viewAlbumImageListProgress(false);
                        allAlbumImgActivityView.onImagePhotoGrapherLiked(baseImage.id,true);
                    }, throwable -> {
                        allAlbumImgActivityView.viewAlbumImageListProgress(false);
                        CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    });
        }


    }


    @SuppressLint("CheckResult")
    @Override
    public void addAlbumImageToCart(BaseImage baseImage) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);

        if (baseImage.isCart) {
            BaseNetworkApi.removeCartItem(baseImage.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(savePhotoResponse -> {
                        allAlbumImgActivityView.onImageAddedToCard(baseImage, false);
                        allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    }, throwable -> {
                        allAlbumImgActivityView.viewAlbumImageListProgress(false);
                        CustomErrorUtil.Companion.setError(context, TAG, throwable);

                    });
        } else {
            BaseNetworkApi.addImageToCart(String.valueOf(baseImage.id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(savePhotoResponse -> {
                        allAlbumImgActivityView.onImageAddedToCard(baseImage, true);
                        allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    }, throwable -> {
                        allAlbumImgActivityView.viewAlbumImageListProgress(false);
                        CustomErrorUtil.Companion.setError(context, TAG, throwable);

                    });
        }


    }


    @SuppressLint("CheckResult")
    @Override
    public void followImagePhotoGrapher(BaseImage baseImage) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);

        if (baseImage.photographer.isFollow){
            BaseNetworkApi.unFollowUser(String.valueOf(baseImage.photographer.id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(followUserResponse -> {
                        allAlbumImgActivityView.onImagePhotoGrapherFollowed(baseImage, false);
                        allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    }, throwable -> {
                        CustomErrorUtil.Companion.setError(context, TAG, throwable);
                        allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    });
        }else {
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


    @SuppressLint("CheckResult")
    @Override
    public void getPhotoDetails(int imgId) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);
        BaseNetworkApi.getImageDetails(String.valueOf(imgId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumPreviewResponse -> {
                    allAlbumImgActivityView.navigateToImageDetails(albumPreviewResponse.data);
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                });

    }
}
