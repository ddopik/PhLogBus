package com.example.ddopik.phlogbusiness.ui.userprofile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.ErrorUtils;
import com.example.ddopik.phlogbusiness.Utiltes.PrefUtils;
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
        BaseNetworkApi.getUserProfile(PrefUtils.getUserToken(context), userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfileResponse -> {
                    if (userProfileResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        UserProfileData userProfileData = userProfileResponse.data;
                        userProfileActivityView.viewUserProfileUserName(userProfileData.userName);
                        userProfileActivityView.viewUserProfileFullName(userProfileData.fullName);
                        userProfileActivityView.viewUserProfileRating(userProfileData.rate);
                        userProfileActivityView.viewUserProfileLevel("000");
                        userProfileActivityView.viewUserProfileProfileImg(userProfileData.imageProfile);
                        userProfileActivityView.viewUserProfileFollowersCount(userProfileData.followerCount);
                        userProfileActivityView.viewUserProfileFollowingCount(userProfileData.followingCount);
                        userProfileActivityView.viewUserProfilePhotosCount(userProfileData.imagePhotographerCount);
                    } else {
                        userProfileActivityView.showMessage(userProfileResponse.data.toString());
                        ErrorUtils.setError(context, TAG, userProfileResponse.msg, userProfileResponse.state);

                    }

                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable.getMessage());
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void getUserPhotos(String userID, int page) {
        userProfileActivityView.viewUserPhotosProgress(true);
        BaseNetworkApi.getUserProfilePhotos(PrefUtils.getUserToken(context), userID, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userPhotosResponse -> {
                    userProfileActivityView.viewUserPhotosProgress(false);
                    if (userPhotosResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        userProfileActivityView.viewUserPhotos(userPhotosResponse.data.data);
                    } else {
                        ErrorUtils.setError(context, TAG, userPhotosResponse.msg, userPhotosResponse.state);
                    }

                }, throwable -> {
                    userProfileActivityView.viewUserPhotosProgress(false);
                    ErrorUtils.setError(context, TAG, throwable.getMessage());
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void followUser(String userId) {
        BaseNetworkApi.followUser(PrefUtils.getUserToken(context), userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    if (followUserResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        userProfileActivityView.showMessage(context.getResources().getString(R.string.following_state) +" "+ followUserResponse.data);
                    } else {
                        ErrorUtils.setError(context, TAG, followUserResponse.msg, followUserResponse.state);
                    }
                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable.getMessage());
                });
    }
}
