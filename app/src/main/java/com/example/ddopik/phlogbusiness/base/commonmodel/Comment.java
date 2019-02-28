package com.example.ddopik.phlogbusiness.base.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class Comment implements Parcelable {


    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("comment")
    @Expose
    public String comment;

    @SerializedName("photographer")
    @Expose
    public Photographer photographer;


    @SerializedName("parent_id")
    @Expose
    public String parentId;

    @SerializedName("replies_count")
    @Expose
    public Integer repliesCount;


    @SerializedName("business")
    @Expose
    public Business business;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.updatedAt);
        dest.writeString(this.createdAt);
        dest.writeString(this.comment);
        dest.writeParcelable(this.photographer, flags);
        dest.writeString(this.parentId);
        dest.writeValue(this.repliesCount);
        dest.writeParcelable(this.business, flags);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.updatedAt = in.readString();
        this.createdAt = in.readString();
        this.comment = in.readString();
        this.photographer = in.readParcelable(Photographer.class.getClassLoader());
        this.parentId = in.readString();
        this.repliesCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.business = in.readParcelable(Business.class.getClassLoader());
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
