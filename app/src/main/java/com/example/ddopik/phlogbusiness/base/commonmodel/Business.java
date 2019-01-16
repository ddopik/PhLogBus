package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class Business implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("image_cover")
    @Expose
    public String imageCover;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("web_site")
    @Expose
    public String website;
    @SerializedName("industry")
    @Expose
    public Industry industry;

    @SerializedName("is_phone_verified")
    public boolean isPhoneVerified;

    @SerializedName("user_name")
    public String userName;

    @SerializedName("name_ar")
    public String nameAr;

    @SerializedName("is_email_verified")
    public boolean isEmailVerified;

    @SerializedName("description")
    public String description;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("is_brand")
    public boolean isBrand;

    @SerializedName("is_brand_text")
    public String isBrandText;

    @SerializedName("updated_at")
    public String updatedAt;

    @SerializedName("rate")
    public int rate;

    @SerializedName("brand_thumbnail")
    public String brandThumbnail;

    @SerializedName("first_name")
    public String firstName;

    @SerializedName("email")
    public String email;

    @SerializedName("brand_phone")
    public String brandPhone;

    @SerializedName("brand_address")
    public String brandAddress;

    @SerializedName("brand_image_cover")
    public String brandImageCover;

    @SerializedName("level")
    public int level;

    @SerializedName("last_name")
    public String lastName;

    @SerializedName("brand_status")
    public int brandStatus;

    @SerializedName("followings_count")
    public int followingsCount;

    @SerializedName("phone")
    public String phone;

    @SerializedName("followers_count")
    public int followersCount;

    @SerializedName("name_en")
    public String nameEn;
}
