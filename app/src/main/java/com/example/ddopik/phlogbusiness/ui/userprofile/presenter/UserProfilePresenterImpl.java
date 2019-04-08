package com.example.ddopik.phlogbusiness.ui.userprofile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.userprofile.model.UserProfileData;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivityView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import io.reactivex.Observable;
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

                    Photographer userProfileData = userProfileResponse.data;
                    userProfileActivityView.viewUserProfileUserName(userProfileData.userName);
                    userProfileActivityView.viewUserProfileFullName(userProfileData.fullName);
                    userProfileActivityView.viewUserProfileRating(userProfileData.rate);
                    userProfileActivityView.viewUserProfileLevel(userProfileData.level);
                    userProfileActivityView.viewUserProfileProfileImg(userProfileData.imageProfile);
                        userProfileActivityView.setUserCoverImg(userProfileData.imageCover); // TODO: where's the user cover image??
                    userProfileActivityView.viewUserProfileFollowersCount("" + userProfileData.followersCount);
                    userProfileActivityView.viewUserProfileFollowingCount("" + userProfileData.followingCount);
                    userProfileActivityView.viewUserProfilePhotosCount("" + userProfileData.photosCount);
                    userProfileActivityView.setIsFollowing(userProfileData.isFollow);

                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void getUserPhotos(String userID, String page) {
        userProfileActivityView.viewUserPhotosProgress(true);
        BaseNetworkApi.getUserProfilePhotos(userID, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userPhotosResponse -> {
                    userProfileActivityView.viewUserPhotosProgress(false);
                    userProfileActivityView.viewUserPhotos(userPhotosResponse.data.data);
                    if (userPhotosResponse.data.nextPageUrl != null) {
                        userProfileActivityView.setNextPageUrl(Utilities.getNextPageNumber(context, userPhotosResponse.data.nextPageUrl));
                    } else {
                        userProfileActivityView.setNextPageUrl(null);
                    }

                }, throwable -> {
                    userProfileActivityView.viewUserPhotosProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public Observable<UserProfileData> followUser(String userId) {
        return BaseNetworkApi.followUser(userId)
                .map(response -> response.data);
    }

    @SuppressLint("CheckResult")
    @Override
    public Observable<UserProfileData> unfollow(String userID) {
        return BaseNetworkApi.unfollowUser(userID)
                .map(response -> response.data);
    }
}
