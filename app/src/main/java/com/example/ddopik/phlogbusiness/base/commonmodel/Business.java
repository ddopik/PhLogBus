package com.example.ddopik.phlogbusiness.base.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Dec,2018
 */

public class Business extends MentionedUser implements Parcelable {

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;

    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("website")
    @Expose
    public String website;


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



    @SerializedName("first_name")
    public String firstName;

    @SerializedName("email")
    public String email;

    @SerializedName("brand_phone")
    public String brandPhone;

    @SerializedName("brand_address")
    public String brandAddress;



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

    @SerializedName("image_cover")
    @Expose
    public String imageCover;


    @SerializedName("is_follow")
    @Expose
    public Boolean isFollow;

    @SerializedName("industry")
    transient public Industry industry;

    public Business() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.thumbnail);
        dest.writeString(this.fullName);
        dest.writeString(this.website);
        dest.writeByte(this.isPhoneVerified ? (byte) 1 : (byte) 0);
        dest.writeString(this.userName);
        dest.writeString(this.nameAr);
        dest.writeByte(this.isEmailVerified ? (byte) 1 : (byte) 0);
        dest.writeString(this.description);
        dest.writeString(this.createdAt);
        dest.writeByte(this.isBrand ? (byte) 1 : (byte) 0);
        dest.writeString(this.isBrandText);
        dest.writeString(this.updatedAt);
        dest.writeInt(this.rate);
        dest.writeString(this.firstName);
        dest.writeString(this.email);
        dest.writeString(this.brandPhone);
        dest.writeString(this.brandAddress);
        dest.writeInt(this.level);
        dest.writeString(this.lastName);
        dest.writeInt(this.brandStatus);
        dest.writeInt(this.followingsCount);
        dest.writeString(this.phone);
        dest.writeInt(this.followersCount);
        dest.writeString(this.nameEn);
        dest.writeString(this.imageCover);
        dest.writeValue(this.isFollow);
    }

    protected Business(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.thumbnail = in.readString();
        this.fullName = in.readString();
        this.website = in.readString();
        this.isPhoneVerified = in.readByte() != 0;
        this.userName = in.readString();
        this.nameAr = in.readString();
        this.isEmailVerified = in.readByte() != 0;
        this.description = in.readString();
        this.createdAt = in.readString();
        this.isBrand = in.readByte() != 0;
        this.isBrandText = in.readString();
        this.updatedAt = in.readString();
        this.rate = in.readInt();
        this.firstName = in.readString();
        this.email = in.readString();
        this.brandPhone = in.readString();
        this.brandAddress = in.readString();
        this.level = in.readInt();
        this.lastName = in.readString();
        this.brandStatus = in.readInt();
        this.followingsCount = in.readInt();
        this.phone = in.readString();
        this.followersCount = in.readInt();
        this.nameEn = in.readString();
        this.imageCover = in.readString();
        this.isFollow = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<Business> CREATOR = new Creator<Business>() {
        @Override
        public Business createFromParcel(Parcel source) {
            return new Business(source);
        }

        @Override
        public Business[] newArray(int size) {
            return new Business[size];
        }
    };
}
