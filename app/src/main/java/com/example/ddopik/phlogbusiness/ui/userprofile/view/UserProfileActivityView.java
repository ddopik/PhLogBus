package com.example.ddopik.phlogbusiness.ui.userprofile.view;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

import java.util.List;

public interface UserProfileActivityView {

    void viewUserProfileLevel(String userLevel); //
    void viewUserProfileRating(float userRating); //
    void viewUserProfileProfileImg(String userImg); //
    void viewUserProfileFullName(String fullName); //
    void viewUserProfileUserName(String userName); //
    void viewUserProfilePhotosCount(String photosCount);//
    void viewUserProfileFollowersCount(String followersCount); //
    void viewUserProfileFollowingCount(String followingCount); //
    void viewUserPhotos(List<BaseImage> userPhotoList);
    void viewUserPhotosProgress(boolean state);
    void showMessage(String msg);

    void setIsFollowing(boolean follow);
    void setNextPageUrl(String page);

}
