package com.example.ddopik.phlogbusiness.ui.social.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ddopik.phlogbusiness.base.commonmodel.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class SocialData  implements Parcelable{

    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("story_id")
    @Expose
    public Integer storyId;

    @SerializedName("entity_id")
    @Expose
    public int entityId;

    @SerializedName("display_type")
    @Expose
    public String displayType;

    @SerializedName("profiles")
    @Expose
    public List<Photographer> profiles = null;
    @SerializedName("albums")
    @Expose
    public List<Album> albums;
    @SerializedName("campaigns")
    @Expose
    public List<Campaign> campaigns;
    @SerializedName("brands")
    @Expose
    public List<Business> brands;
    @SerializedName("photos")
    @Expose
    public List<BaseImage> photos;

    public SocialData() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.source);
        dest.writeString(this.title);
        dest.writeValue(this.storyId);
        dest.writeInt(this.entityId);
        dest.writeString(this.displayType);
        dest.writeTypedList(this.profiles);
        dest.writeList(this.albums);
        dest.writeList(this.campaigns);
        dest.writeTypedList(this.brands);
        dest.writeTypedList(this.photos);
    }

    protected SocialData(Parcel in) {
        this.source = in.readString();
        this.title = in.readString();
        this.storyId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.entityId = in.readInt();
        this.displayType = in.readString();
        this.profiles = in.createTypedArrayList(Photographer.CREATOR);
        this.albums = new ArrayList<Album>();
        in.readList(this.albums, Album.class.getClassLoader());
        this.campaigns = new ArrayList<Campaign>();
        in.readList(this.campaigns, Campaign.class.getClassLoader());
        this.brands = in.createTypedArrayList(Business.CREATOR);
        this.photos = in.createTypedArrayList(BaseImage.CREATOR);
    }

    public static final Creator<SocialData> CREATOR = new Creator<SocialData>() {
        @Override
        public SocialData createFromParcel(Parcel source) {
            return new SocialData(source);
        }

        @Override
        public SocialData[] newArray(int size) {
            return new SocialData[size];
        }
    };
}
