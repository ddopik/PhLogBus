package com.example.ddopik.phlogbusiness.ui.userprofile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.userprofile.model.UserProfileData;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivityView;
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

                        UserProfileData userProfileData = userProfileResponse.data;
                        userProfileActivityView.viewUserProfileUserName(userProfileData.getUserName());
                        userProfileActivityView.viewUserProfileFullName(userProfileData.getFullName());
                        userProfileActivityView.viewUserProfileRating(userProfileData.getRate());
                        userProfileActivityView.viewUserProfileLevel(userProfileData.getLevel());
                        userProfileActivityView.viewUserProfileProfileImg(userProfileData.getImageProfile());
//                        userProfileActivityView.setUserCoverImg(userProfileData.) // TODO: where's the user cover image??
                        userProfileActivityView.viewUserProfileFollowersCount(""+userProfileData.getFollowersCount());
                        userProfileActivityView.viewUserProfileFollowingCount(""+userProfileData.getFollowingsCount());
                        userProfileActivityView.viewUserProfilePhotosCount(""+userProfileData.getPhotosCount());
                        userProfileActivityView.setIsFollowing(userProfileResponse.data.isFollow());

                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void getUserPhotos(String userID, int page) {
        userProfileActivityView.viewUserPhotosProgress(true);
        BaseNetworkApi.getUserProfilePhotos( userID, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userPhotosResponse -> {
                    userProfileActivityView.viewUserPhotosProgress(false);

                        userProfileActivityView.viewUserPhotos(userPhotosResponse.data.data);


                }, throwable -> {
                    userProfileActivityView.viewUserPhotosProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public Observable<UserProfileData> followUser(String userId) {
        return BaseNetworkApi.followUser( userId)
                .map(response -> response.data);
    }

    @SuppressLint("CheckResult")
    @Override
    public Observable<UserProfileData> unfollow(String userID) {
        return BaseNetworkApi.unfollowUser(userID)
                .map(response -> response.data);
    }
}
