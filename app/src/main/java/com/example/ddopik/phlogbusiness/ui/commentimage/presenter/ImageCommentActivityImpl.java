package com.example.ddopik.phlogbusiness.ui.commentimage.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.androidnetworking.error.ANError;
import com.example.ddopik.phlogbusiness.base.commonmodel.*;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ReportModel;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ReportReason;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.ImageCommentActivityView;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import com.google.gson.Gson;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class ImageCommentActivityImpl implements ImageCommentActivityPresenter {

    private String TAG = ImageCommentActivityImpl.class.getSimpleName();
    private Context context;
    private ImageCommentActivityView imageCommentActivityView;

    private static final String SAVED = "Saved";

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
                    if (albumImgCommentResponse.data.nextPageUrl != null) {
                        imageCommentActivityView.setNextPageUrl(Utilities.getNextPageNumber(context, albumImgCommentResponse.data.nextPageUrl));

                    } else {
                        imageCommentActivityView.setNextPageUrl(null);
                    }

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
                    imageCommentActivityView.onImageCommented(submitImageCommentResponse.data);
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


        BaseNetworkApi.likeImage(String.valueOf(baseImage.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(likeImageResponse -> {
                    imageCommentActivityView.onImageLiked(likeImageResponse.data);
                    imageCommentActivityView.viewImageProgress(false);
                }, throwable -> {
                    imageCommentActivityView.viewImageProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });


    }

    @SuppressLint("CheckResult")
    @Override
    public void unLikePhoto(BaseImage baseImage) {
        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.unlikeImage(String.valueOf(baseImage.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(likeImageResponse -> {
                    imageCommentActivityView.onImageLiked(likeImageResponse.data);
                    imageCommentActivityView.viewImageProgress(false);
                }, throwable -> {
                    imageCommentActivityView.viewImageProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void rateImage(BaseImage baseImage, float rate) {
        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.rateImage(String.valueOf(baseImage.id), String.valueOf(Math.round(rate)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageRateResponse -> {
                            imageCommentActivityView.viewOnImageRate(imageRateResponse.baseImage);
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
                    imageCommentActivityView.viewOnImagedAddedToCart(true);
                    imageCommentActivityView.viewImageProgress(false);
                }, throwable -> {
                    imageCommentActivityView.viewOnImagedAddedToCart(true);
                    imageCommentActivityView.viewImageProgress(false);
                });


    }

    @SuppressLint("CheckResult")
    @Override
    public void getReportReasons(Consumer<List<ReportReason>> consumer) {
        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.getReportReasons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reportReasonsResponse -> {
                    imageCommentActivityView.viewImageProgress(false);
                    if (reportReasonsResponse != null && reportReasonsResponse.getData() != null)
                        consumer.accept(reportReasonsResponse.getData());
                }, throwable -> {
                    imageCommentActivityView.viewImageProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void submitReport(Consumer<Boolean> success, ReportModel model) {
        BaseNetworkApi.submitReport(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (s != null) {
                        success.accept(true);
                    } else success.accept(false);
                }, throwable -> {
                    success.accept(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void chooseWinner(int campaignId, BaseImage image, Consumer<Boolean> success) {
        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.chooseWinner(campaignId, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    imageCommentActivityView.viewImageProgress(false);
                    if (response.msg.equals(SAVED)) success.accept(true);
                    else success.accept(false);
                }, throwable -> {
                    imageCommentActivityView.viewImageProgress(false);
                    success.accept(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void getImageDetails(int imageId) {

        imageCommentActivityView.viewImageProgress(true);
        BaseNetworkApi.getPhotoGrapherPhotoDetails(String.valueOf(imageId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imageDetailsResponse -> {
                    imageCommentActivityView.viewImageDetails(imageDetailsResponse.data);
                    imageCommentActivityView.viewImageProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    imageCommentActivityView.viewImageProgress(false);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void getMentionedUser(String key) {
        BaseNetworkApi.getSocialAutoComplete(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socialAutoCompleteResponse -> {

                    List<MentionedUser> mentionedUserList = new ArrayList<>();
                    if (socialAutoCompleteResponse.data.photographers != null) {


                        for (Photographer photographer : socialAutoCompleteResponse.data.photographers) {
                            MentionedUser mentionedUser = new MentionedUser();

                            mentionedUser.mentionedUserId = photographer.id;
                            mentionedUser.mentionedUserName = photographer.fullName;
                            mentionedUser.mentionedImage = photographer.imageProfile;
                            mentionedUser.mentionedType = Constants.UserType.USER_TYPE_PHOTOGRAPHER;
                            mentionedUserList.add(mentionedUser);
                        }
                    }
                    if (socialAutoCompleteResponse.data.businesses != null) {
                        for (Business business : socialAutoCompleteResponse.data.businesses) {
                            MentionedUser mentionedUser = new MentionedUser();
                            mentionedUser.mentionedUserId = business.id;
                            mentionedUser.mentionedUserName = business.firstName + " " + business.lastName;
                            mentionedUser.mentionedImage = business.thumbnail;
                            mentionedUser.mentionedType = Constants.UserType.USER_TYPE_BUSINESS;
                            mentionedUserList.add(mentionedUser);
                        }
                    }
                    imageCommentActivityView.viewMentionedUsers(mentionedUserList);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }

}
