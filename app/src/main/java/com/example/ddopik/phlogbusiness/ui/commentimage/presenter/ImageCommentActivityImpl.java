package com.example.ddopik.phlogbusiness.ui.commentimage.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.ImageCommentActivityView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class ImageCommentActivityImpl implements ImageCommentActivityPresenter {

    private String TAG = ImageCommentActivityImpl.class.getSimpleName();
    private Context context;
    private ImageCommentActivityView imageCommentActivityView;

    public ImageCommentActivityImpl(Context context, ImageCommentActivityView imageCommentActivityView) {
        this.context = context;
        this.imageCommentActivityView = imageCommentActivityView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getImageComments(String imageId, String page) {
        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.getImageComments(imageId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumImgCommentResponse -> {
                    imageCommentActivityView.viewPhotoComment(albumImgCommentResponse.data);
                    imageCommentActivityView.viewImageProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    imageCommentActivityView.viewImageProgress(false);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void submitComment(String imageId, String comment) {
        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.submitImageComment(imageId, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(submitImageCommentResponse -> {
                    imageCommentActivityView.viewMessage("Comment Submitted");
                    imageCommentActivityView.viewOnImageCommented(submitImageCommentResponse.data.comment);
                    imageCommentActivityView.viewImageProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    imageCommentActivityView.viewImageProgress(false);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void likePhoto(BaseImage baseImage) {
        imageCommentActivityView.viewImageProgress(true);


        if (baseImage.isLiked) {
            BaseNetworkApi.unlikeImage(String.valueOf(baseImage.id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseStateResponse -> {
                        imageCommentActivityView.viewImageProgress(false);
                        imageCommentActivityView.viewImageLikedStatus(false);
                    }, throwable -> {
                        imageCommentActivityView.viewImageProgress(false);
                        CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    });
        } else {
            BaseNetworkApi.likeImage(String.valueOf(baseImage.id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseStateResponse -> {
                        imageCommentActivityView.viewImageProgress(false);
                        imageCommentActivityView.viewImageLikedStatus(true);
                    }, throwable -> {
                        imageCommentActivityView.viewImageProgress(false);
                        CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    });
        }

    }

    @SuppressLint("CheckResult")
    @Override
    public void rateImage(BaseImage baseImage, float rate) {
        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.rateImage(String.valueOf(baseImage.id), String.valueOf((int) Math.round(rate)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageRateResponse -> {
                            imageCommentActivityView.onImageRate(imageRateResponse.baseImage);
                            imageCommentActivityView.viewImageProgress(false);
                        }
                        , throwable -> {

                            imageCommentActivityView.viewImageProgress(false);
                            CustomErrorUtil.Companion.setError(context, TAG, throwable);
                        });


    }

    @SuppressLint("CheckResult")
    @Override
    public void addImageToCart(int imageId) {
        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.addImageToCart(String.valueOf(imageId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addImageToCartResponse -> {
                    imageCommentActivityView.onImagedAddedToCart(true);
                    imageCommentActivityView.viewImageProgress(false);
                }, throwable -> {
                    imageCommentActivityView.onImagedAddedToCart(true);
                    imageCommentActivityView.viewImageProgress(false);
                });


    }
}
