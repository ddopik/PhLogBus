package com.example.ddopik.phlogbusiness.ui.userprofile.presenter;

public interface UserProfilePresenter {

    void getUserProfileData(String userID);

    void getUserPhotos(String userID, int page);

    void followUser(String userId);
}
