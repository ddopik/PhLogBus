package com.example.ddopik.phlogbusiness.ui.userprofile.presenter;

import com.example.ddopik.phlogbusiness.ui.userprofile.model.UserProfileData;
import io.reactivex.Observable;

public interface UserProfilePresenter {

    void getUserProfileData(String userID);

    void getUserPhotos(String userID, int page);

    Observable<UserProfileData> followUser(String userId);

    Observable<UserProfileData> unfollow(String userID);
}
