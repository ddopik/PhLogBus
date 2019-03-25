package com.example.ddopik.phlogbusiness.base.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ddopik.phlogbusiness.ui.setupbrand.fragment.stoptwo.SelectFromStringListDialogFragment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Industry implements Parcelable, SelectFromStringListDialogFragment.Model, Serializable {

    @Expose
    public Integer id;
    @SerializedName("name_en")
    @Expose
    public String nameEn;
    @SerializedName("name_ar")
    @Expose
    public String nameAr;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public String deletedAt;

    @SerializedName("name")
    public String name;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.nameEn);
        dest.writeString(this.nameAr);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.deletedAt);
        dest.writeString(this.name);
    }

    public Industry() {
    }

    protected Industry(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nameEn = in.readString();
        this.nameAr = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.deletedAt = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Industry> CREATOR = new Parcelable.Creator<Industry>() {
        @Override
        public Industry createFromParcel(Parcel source) {
            return new Industry(source);
        }

        @Override
        public Industry[] newArray(int size) {
            return new Industry[size];
        }
    };

    @Override
    public String getStringForShowingInList() {
        return nameEn;
    }
}
