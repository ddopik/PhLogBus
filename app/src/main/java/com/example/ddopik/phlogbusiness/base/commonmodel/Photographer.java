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
public class Photographer extends MentionedUser implements Parcelable {

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
    @SerializedName("image_cover")
    @Expose
    public String imageCover;
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
    @SerializedName("followings_count")
    @Expose
    public Integer followingCount;
    @SerializedName("photos_count")
    @Expose
    public Integer photosCount;


    @SerializedName("country")
    @Expose
    public transient Country country;
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
    public boolean isFollow;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.isPhoneVerified);
        dest.writeString(this.userName);
        dest.writeValue(this.isEmailVerified);
        dest.writeString(this.mobile);
        dest.writeString(this.createdAt);
        dest.writeString(this.mobileModel);
        dest.writeString(this.facebookId);
        dest.writeString(this.token);
        dest.writeString(this.password);
        dest.writeString(this.fullName);
        dest.writeString(this.updatedAt);
        dest.writeString(this.imageProfile);
        dest.writeString(this.imageCover);
        dest.writeString(this.mobileOs);
        dest.writeString(this.hash);
        dest.writeString(this.email);
        dest.writeString(this.countryId);
        dest.writeValue(this.id);
        dest.writeValue(this.followersCount);
        dest.writeValue(this.followingCount);
        dest.writeValue(this.photosCount);
        dest.writeFloat(this.rate);
        dest.writeString(this.level);
        dest.writeList(this.earnings);
        dest.writeByte(this.isFollow ? (byte) 1 : (byte) 0);
    }

    protected Photographer(Parcel in) {
        this.isPhoneVerified = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.userName = in.readString();
        this.isEmailVerified = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.mobile = in.readString();
        this.createdAt = in.readString();
        this.mobileModel = in.readString();
        this.facebookId = in.readString();
        this.token = in.readString();
        this.password = in.readString();
        this.fullName = in.readString();
        this.updatedAt = in.readString();
        this.imageProfile = in.readString();
        this.imageCover = in.readString();
        this.mobileOs = in.readString();
        this.hash = in.readString();
        this.email = in.readString();
        this.countryId = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.followersCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.followingCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.photosCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.rate = in.readFloat();
        this.level = in.readString();
        this.earnings = new ArrayList<Earning>();
        in.readList(this.earnings, Earning.class.getClassLoader());
        this.isFollow = in.readByte() != 0;
    }

    public static final Creator<Photographer> CREATOR = new Creator<Photographer>() {
        @Override
        public Photographer createFromParcel(Parcel source) {
            return new Photographer(source);
        }

        @Override
        public Photographer[] newArray(int size) {
            return new Photographer[size];
        }
    };
}