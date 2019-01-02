package com.example.ddopik.phlogbusiness.base.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class BaseImage implements Parcelable {

    public BaseImage(){

    }
    @SerializedName("caption")
    @Expose
    public String caption;

    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("thumbnail_url")
    @Expose
    public String thumbnailUrl;
    @SerializedName("url")
    @Expose
    public String url;

    @SerializedName("album_name")
    @Expose
    public String albumName;


    @SerializedName("like_count")
    @Expose
    public String likeCount;


    @SerializedName("comment_count")
    @Expose
    public String commentCount;

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("photographer")
    @Expose
    public Photographer photographer;
    @SerializedName("is_saved")
    @Expose
    public Boolean isSaved;
    @SerializedName("is_liked")
    @Expose
    public Boolean isLiked;
    @SerializedName("tags")
    @Expose
    public List<Tag> tags;

    protected BaseImage(Parcel in) {
        caption = in.readString();
        updatedAt = in.readString();
        createdAt = in.readString();
        location = in.readString();
        thumbnailUrl = in.readString();
        url = in.readString();
        albumName = in.readString();
        likeCount = in.readString();
        commentCount = in.readString();
        id = in.readByte() == 0x00 ? null : in.readInt();
        photographer = (Photographer) in.readValue(Photographer.class.getClassLoader());
        byte isSavedVal = in.readByte();
        isSaved = isSavedVal == 0x02 ? null : isSavedVal != 0x00;
        byte isLikedVal = in.readByte();
        isLiked = isLikedVal == 0x02 ? null : isLikedVal != 0x00;
        if (in.readByte() == 0x01) {
            tags = new ArrayList<Tag>();
            in.readList(tags, Tag.class.getClassLoader());
        } else {
            tags = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(caption);
        dest.writeString(updatedAt);
        dest.writeString(createdAt);
        dest.writeString(location);
        dest.writeString(thumbnailUrl);
        dest.writeString(url);
        dest.writeString(albumName);
        dest.writeString(likeCount);
        dest.writeString(commentCount);
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeValue(photographer);
        if (isSaved == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isSaved ? 0x01 : 0x00));
        }
        if (isLiked == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isLiked ? 0x01 : 0x00));
        }
        if (tags == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(tags);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BaseImage> CREATOR = new Parcelable.Creator<BaseImage>() {
        @Override
        public BaseImage createFromParcel(Parcel in) {
            return new BaseImage(in);
        }

        @Override
        public BaseImage[] newArray(int size) {
            return new BaseImage[size];
        }
    };
}
