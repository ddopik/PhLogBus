package com.example.ddopik.phlogbusiness.base.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class Business  extends MentionedUser implements Parcelable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("image_cover")
    @Expose
    public String imageCover;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
//    @SerializedName("full_name")
//    @Expose
//    public String fullName;
    @SerializedName("website")
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.imageCover);
        dest.writeString(this.thumbnail);
        dest.writeString(this.website);
        dest.writeParcelable(this.industry, flags);
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
        dest.writeString(this.brandThumbnail);
        dest.writeString(this.firstName);
        dest.writeString(this.email);
        dest.writeString(this.brandPhone);
        dest.writeString(this.brandAddress);
        dest.writeString(this.brandImageCover);
        dest.writeInt(this.level);
        dest.writeString(this.lastName);
        dest.writeInt(this.brandStatus);
        dest.writeInt(this.followingsCount);
        dest.writeString(this.phone);
        dest.writeInt(this.followersCount);
        dest.writeString(this.nameEn);
    }

    public Business() {
    }

    protected Business(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.imageCover = in.readString();
        this.thumbnail = in.readString();
        this.website = in.readString();
        this.industry = in.readParcelable(Industry.class.getClassLoader());
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
        this.brandThumbnail = in.readString();
        this.firstName = in.readString();
        this.email = in.readString();
        this.brandPhone = in.readString();
        this.brandAddress = in.readString();
        this.brandImageCover = in.readString();
        this.level = in.readInt();
        this.lastName = in.readString();
        this.brandStatus = in.readInt();
        this.followingsCount = in.readInt();
        this.phone = in.readString();
        this.followersCount = in.readInt();
        this.nameEn = in.readString();
    }

    public static final Parcelable.Creator<Business> CREATOR = new Parcelable.Creator<Business>() {
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
