package com.example.ddopik.phlogbusiness.ui.social.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class Entite  implements Parcelable{
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("display_type")
    @Expose
    public String displayType;
    @SerializedName("isIn")
    @Expose
    public Boolean isIn;
    @SerializedName("imgs")
    @Expose
    public List<String> imgs = null;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("gotoListImgs_Or_singleImg_screen")
    @Expose
    public Boolean gotoListImgsOrSingleImgScreen;
    @SerializedName("title_en")
    @Expose
    public String titleEn;
    @SerializedName("name_en")
    @Expose
    public String nameEn;
    @SerializedName("number_of_followers")
    @Expose
    public String numberOfFollowers;
    @SerializedName("description_en")
    @Expose
    public String descriptionEn;
    @SerializedName("industry_name")
    @Expose
    public String industryName;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("brandCoverimg")
    @Expose
    public String brandCoverimg;

    protected Entite(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        displayType = in.readString();
        byte isInVal = in.readByte();
        isIn = isInVal == 0x02 ? null : isInVal != 0x00;
        if (in.readByte() == 0x01) {
            imgs = new ArrayList<String>();
            in.readList(imgs, String.class.getClassLoader());
        } else {
            imgs = null;
        }
        fullName = in.readString();
        userName = in.readString();
        byte gotoListImgsOrSingleImgScreenVal = in.readByte();
        gotoListImgsOrSingleImgScreen = gotoListImgsOrSingleImgScreenVal == 0x02 ? null : gotoListImgsOrSingleImgScreenVal != 0x00;
        titleEn = in.readString();
        nameEn = in.readString();
        numberOfFollowers = in.readString();
        descriptionEn = in.readString();
        industryName = in.readString();
        thumbnail = in.readString();
        brandCoverimg = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(displayType);
        if (isIn == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isIn ? 0x01 : 0x00));
        }
        if (imgs == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(imgs);
        }
        dest.writeString(fullName);
        dest.writeString(userName);
        if (gotoListImgsOrSingleImgScreen == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (gotoListImgsOrSingleImgScreen ? 0x01 : 0x00));
        }
        dest.writeString(titleEn);
        dest.writeString(nameEn);
        dest.writeString(numberOfFollowers);
        dest.writeString(descriptionEn);
        dest.writeString(industryName);
        dest.writeString(thumbnail);
        dest.writeString(brandCoverimg);
    }

    @SuppressWarnings("unused")
    public static final Creator<Entite> CREATOR = new Creator<Entite>() {
        @Override
        public Entite createFromParcel(Parcel in) {
            return new Entite(in);
        }

        @Override
        public Entite[] newArray(int size) {
            return new Entite[size];
        }
    };
}

//public class Entite implements Parcelable {
//
//    public Integer id;
//
//    public String displayType;
//
//    public Boolean isIn;
//
//    public List<String> imgs = null;
//
//    public String fullName;
//
//    public String userName;
//
//    public Boolean gotoListImgsOrSingleImgScreen;
//
//    public String titleEn;
//
//    public String nameEn;
//
//    public String numberOfFollowers;
//
//    public String descriptionEn;
//
//    public String industryName;
//
//    public String thumbnail;
//
//
//    public String brandCoverimg;
//
//    protected Entite(Parcel in) {
//        id = in.readByte() == 0x00 ? null : in.readInt();
//        displayType = in.readString();
//        byte isInVal = in.readByte();
//        isIn = isInVal == 0x02 ? null : isInVal != 0x00;
//        if (in.readByte() == 0x01) {
//            imgs = new ArrayList<String>();
//            in.readList(imgs, String.class.getClassLoader());
//        } else {
//            imgs = null;
//        }
//        fullName = in.readString();
//        userName = in.readString();
//        byte gotoListImgsOrSingleImgScreenVal = in.readByte();
//        gotoListImgsOrSingleImgScreen = gotoListImgsOrSingleImgScreenVal == 0x02 ? null : gotoListImgsOrSingleImgScreenVal != 0x00;
//        titleEn = in.readString();
//        nameEn = in.readString();
//        numberOfFollowers = in.readString();
//        descriptionEn = in.readString();
//        industryName = in.readString();
//        thumbnail = in.readString();
//        brandCoverimg = in.readString();
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        if (id == null) {
//            dest.writeByte((byte) (0x00));
//        } else {
//            dest.writeByte((byte) (0x01));
//            dest.writeInt(id);
//        }
//        dest.writeString(displayType);
//        if (isIn == null) {
//            dest.writeByte((byte) (0x02));
//        } else {
//            dest.writeByte((byte) (isIn ? 0x01 : 0x00));
//        }
//        if (imgs == null) {
//            dest.writeByte((byte) (0x00));
//        } else {
//            dest.writeByte((byte) (0x01));
//            dest.writeList(imgs);
//        }
//        dest.writeString(fullName);
//        dest.writeString(userName);
//        if (gotoListImgsOrSingleImgScreen == null) {
//            dest.writeByte((byte) (0x02));
//        } else {
//            dest.writeByte((byte) (gotoListImgsOrSingleImgScreen ? 0x01 : 0x00));
//        }
//        dest.writeString(titleEn);
//        dest.writeString(nameEn);
//        dest.writeString(numberOfFollowers);
//        dest.writeString(descriptionEn);
//        dest.writeString(industryName);
//        dest.writeString(thumbnail);
//        dest.writeString(brandCoverimg);
//    }
//
//    @SuppressWarnings("unused")
//    public static final Parcelable.Creator<Entite> CREATOR = new Parcelable.Creator<Entite>() {
//        @Override
//        public Entite createFromParcel(Parcel in) {
//            return new Entite(in);
//        }
//
//        @Override
//        public Entite[] newArray(int size) {
//            return new Entite[size];
//        }
//    };
//}