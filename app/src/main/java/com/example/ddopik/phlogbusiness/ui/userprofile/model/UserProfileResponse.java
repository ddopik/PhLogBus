package com.example.ddopik.phlogbusiness.ui.userprofile.model;

 import com.example.ddopik.phlogbusiness.base.BaseApiResponse;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by $USER_NAME on 9/30/2018.
 */
public class UserProfileResponse extends BaseApiResponse {
    @SerializedName("data")
    @Expose
    public UserProfileData data;
}
