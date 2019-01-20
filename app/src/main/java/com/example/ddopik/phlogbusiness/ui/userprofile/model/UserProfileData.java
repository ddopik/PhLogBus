package com.example.ddopik.phlogbusiness.ui.userprofile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 10/10/2018.
 */
public class UserProfileData {

    @SerializedName("country")
    private Object country;

    @SerializedName("is_follow")
    private boolean isFollow;

    @SerializedName("level")
    private Object level;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("mobile")
    private Object mobile;

    @SerializedName("followings_count")
    private int followingsCount;

    @SerializedName("earnings")
    private List<Object> earnings;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("rate")
    private float rate;

    @SerializedName("image_profile")
    private String imageProfile;

    @SerializedName("followers_count")
    private int followersCount;

    @SerializedName("id")
    private int id;

    @SerializedName("email")
    private String email;

    @SerializedName("photos_count")
    private int photosCount;

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public Object getLevel() {
        return level;
    }

    public void setLevel(Object level) {
        this.level = level;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public void setFollowingsCount(int followingsCount) {
        this.followingsCount = followingsCount;
    }

    public List<Object> getEarnings() {
        return earnings;
    }

    public void setEarnings(List<Object> earnings) {
        this.earnings = earnings;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhotosCount() {
        return photosCount;
    }

    public void setPhotosCount(int photosCount) {
        this.photosCount = photosCount;
    }
}
