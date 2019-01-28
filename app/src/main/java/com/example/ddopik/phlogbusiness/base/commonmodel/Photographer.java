package com.example.ddopik.phlogbusiness.base.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class Photographer extends MentionedUser implements  Parcelable  {

    public Photographer(){

    }

    @SerializedName("is_phone_verified")
    @Expose
    public Boolean isPhoneVerified;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("is_email_verified")
    @Expose
    public Boolean isEmailVerified;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("mobile_model")
    @Expose
    public String mobileModel;
    @SerializedName("facebook_id")
    @Expose
    public String facebookId;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("image_profile")
    @Expose
    public String imageProfile;
    @SerializedName("mobile_os")
    @Expose
    public String mobileOs;
    @SerializedName("hash")
    @Expose
    public String hash;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("country_id")
    @Expose
    public String countryId;
    @SerializedName("id")
    @Expose
    public Integer id;



    @SerializedName("followers_count")
    @Expose
    public Integer followersCount;
    @SerializedName("following_count")
    @Expose
    public Integer followingCount;
    @SerializedName("photos_count")
    @Expose
    public Integer photosCount;


    @SerializedName("country")
    @Expose
    public transient String country;
    @SerializedName("rate")
    @Expose
    public float rate;
    @SerializedName("level")
    @Expose
    public String level;
    @SerializedName("earnings")
    @Expose
    public List<Earning> earnings = null;

    @SerializedName("is_follow")
    @Expose
    public Boolean isFollow;

    protected Photographer(Parcel in) {
        byte isPhoneVerifiedVal = in.readByte();
        isPhoneVerified = isPhoneVerifiedVal == 0x02 ? null : isPhoneVerifiedVal != 0x00;
        userName = in.readString();
        byte isEmailVerifiedVal = in.readByte();
        isEmailVerified = isEmailVerifiedVal == 0x02 ? null : isEmailVerifiedVal != 0x00;
        mobile = in.readString();
        createdAt = in.readString();
        mobileModel = in.readString();
        facebookId = in.readString();
        token = in.readString();
        password = in.readString();
        fullName = in.readString();
        updatedAt = in.readString();
        imageProfile = in.readString();
        mobileOs = in.readString();
        hash = in.readString();
        email = in.readString();
        countryId = in.readString();
        id = in.readByte() == 0x00 ? null : in.readInt();
        followersCount = in.readByte() == 0x00 ? null : in.readInt();
        followingCount = in.readByte() == 0x00 ? null : in.readInt();
        photosCount = in.readByte() == 0x00 ? null : in.readInt();
        country = in.readString();
        rate = in.readFloat();
        level = in.readString();
        if (in.readByte() == 0x01) {
            earnings = new ArrayList<Earning>();
            in.readList(earnings, Earning.class.getClassLoader());
        } else {
            earnings = null;
        }
        byte isFollowVal = in.readByte();
        isFollow = isFollowVal == 0x02 ? null : isFollowVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (isPhoneVerified == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isPhoneVerified ? 0x01 : 0x00));
        }
        dest.writeString(userName);
        if (isEmailVerified == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isEmailVerified ? 0x01 : 0x00));
        }
        dest.writeString(mobile);
        dest.writeString(createdAt);
        dest.writeString(mobileModel);
        dest.writeString(facebookId);
        dest.writeString(token);
        dest.writeString(password);
        dest.writeString(fullName);
        dest.writeString(updatedAt);
        dest.writeString(imageProfile);
        dest.writeString(mobileOs);
        dest.writeString(hash);
        dest.writeString(email);
        dest.writeString(countryId);
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        if (followersCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(followersCount);
        }
        if (followingCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(followingCount);
        }
        if (photosCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(photosCount);
        }
        dest.writeString(country);
        dest.writeFloat(rate);
        dest.writeString(level);
        if (earnings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(earnings);
        }
        if (isFollow == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isFollow ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Photographer> CREATOR = new Creator<Photographer>() {
        @Override
        public Photographer createFromParcel(Parcel in) {
            return new Photographer(in);
        }

        @Override
        public Photographer[] newArray(int size) {
            return new Photographer[size];
        }
    };
}