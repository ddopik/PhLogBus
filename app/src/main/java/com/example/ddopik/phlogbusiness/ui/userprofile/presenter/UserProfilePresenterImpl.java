package com.example.ddopik.phlogbusiness.ui.userprofile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.userprofile.model.UserProfileData;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivityView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserProfilePresenterImpl implements UserProfilePresenter {

    private String TAG = UserProfilePresenterImpl.class.getSimpleName();
    private Context context;
    private UserProfileActivityView userProfileActivityView;

    public UserProfilePresenterImpl(Context context, UserProfileActivityView userProfileActivityView) {
        this.context = context;
        this.userProfileActivityView = userProfileActivityView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getUserProfileData(String userID) {
        BaseNetworkApi.getUserProfile(PrefUtils.getBrandToken(context), userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfileResponse -> {

                        UserProfileData userProfileData = userProfileResponse.data;
                        userProfileActivityView.viewUserProfileUserName(userProfileData.userName);
                        userProfileActivityView.viewUserProfileFullName(userProfileData.fullName);
                        userProfileActivityView.viewUserProfileRating(userProfileData.rate);
                        userProfileActivityView.viewUserProfileLevel("000");
                        userProfileActivityView.viewUserProfileProfileImg(userProfileData.imageProfile);
                        userProfileActivityView.viewUserProfileFollowersCount(userProfileData.followerCount);
                        userProfileActivityView.viewUserProfileFollowingCount(userProfileData.followingCount);
                        userProfileActivityView.viewUserProfilePhotosCount(userProfileData.imagePhotographerCount);


                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable.getMessage());
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void getUserPhotos(String userID, int page) {
        userProfileActivityView.viewUserPhotosProgress(true);
        BaseNetworkApi.getUserProfilePhotos(PrefUtils.getBrandToken(context), userID, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userPhotosResponse -> {
                    userProfileActivityView.viewUserPhotosProgress(false);

                        userProfileActivityView.viewUserPhotos(userPhotosResponse.data.data);


                }, throwable -> {
                    userProfileActivityView.viewUserPhotosProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable.getMessage());
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void followUser(String userId) {
        BaseNetworkApi.followUser(PrefUtils.getBrandToken(context), userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {

                        userProfileActivityView.showMessage(context.getResources().getString(R.string.following_state) +" "+ followUserResponse.data);

                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable.getMessage());
                });
    }
}
