package com.example.ddopik.phlogbusiness.ui.userprofile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/10/2018.
 */
public class UserProfileData {

    @SerializedName("follower_count")
    @Expose
    public String followerCount;
    @SerializedName("following_count")
    @Expose
    public String followingCount;
    @SerializedName("image_photographer_count")
    @Expose
    public String imagePhotographerCount;
    @SerializedName("image_profile")
    @Expose
    public String imageProfile;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("user_name_id")
    @Expose
    public String userNameId;
    @SerializedName("rate")
    @Expose
    public Integer rate;
    @SerializedName("level")
    @Expose
    public String level;
    @SerializedName("follow")
    @Expose
    public String follow;


}
