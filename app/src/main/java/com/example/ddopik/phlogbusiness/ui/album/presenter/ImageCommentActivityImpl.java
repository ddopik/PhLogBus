package com.example.ddopik.phlogbusiness.ui.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.album.view.ImageCommentActivityView;
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
        imageCommentActivityView.viewAddCommentProgress(true);
        BaseNetworkApi.getImageComments(imageId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumImgCommentResponse -> {
                    imageCommentActivityView.viewPhotoComment(albumImgCommentResponse.data.comments);
                    imageCommentActivityView.viewAddCommentProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    imageCommentActivityView.viewAddCommentProgress(false);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void submitComment(String imageId, String comment) {
        imageCommentActivityView.viewAddCommentProgress(true);
        BaseNetworkApi.submitImageComment(imageId, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumImgCommentResponse -> {
                    imageCommentActivityView.viewMessage("Comment Submitted");
//                    imageCommentActivityView.viewPhotoComment(albumImgCommentResponse.data.comments);
                    imageCommentActivityView.viewAddCommentProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    imageCommentActivityView.viewAddCommentProgress(false);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void likePhoto(String imageId) {
        imageCommentActivityView.viewAddCommentProgress(true);
        BaseNetworkApi.likeImage(imageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseStateResponse -> {
                    imageCommentActivityView.viewAddCommentProgress(false);
                    imageCommentActivityView.viewMessage("Like");
                },throwable -> {
                    imageCommentActivityView.viewAddCommentProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }
}
