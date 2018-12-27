package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class Brand {

    @SerializedName("image_cover")
    @Expose
    public String imageCover;
    @SerializedName("is_phone_verified")
    @Expose
    public Boolean isPhoneVerified;
    @SerializedName("website")
    @Expose
    public String website;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("is_email_verified")
    @Expose
    public Boolean isEmailVerified;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("brand_status")
    @Expose
    public Integer brandStatus;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("followers_count")
    @Expose
    public Integer followersCount;
    @SerializedName("following_count")
    @Expose
    public Integer followingCount;
    @SerializedName("industry")
    @Expose
    public Industry industry;
    @SerializedName("is_brand")
    @Expose
    public Boolean isBrand;
    @SerializedName("is_brand_text")
    @Expose
    public String isBrandText;
    @SerializedName("rate")
    @Expose
    public Integer rate;
    @SerializedName("level")
    @Expose
    public Integer level;
}
