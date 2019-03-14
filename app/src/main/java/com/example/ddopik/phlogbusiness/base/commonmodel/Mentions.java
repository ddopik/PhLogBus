package com.example.ddopik.phlogbusiness.base.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Mentions implements Parcelable {
    @SerializedName("photographers")
    @Expose
    public List<Photographer> photographers = new ArrayList<>();
    @SerializedName("business")
    @Expose
    public List<Business> business = new ArrayList<>();

    public Mentions() {
    }

    public static final Creator<Mentions> CREATOR = new Creator<Mentions>() {
        @Override
        public Mentions createFromParcel(Parcel in) {
            return new Mentions(in);
        }

        @Override
        public Mentions[] newArray(int size) {
            return new Mentions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(photographers);
        dest.writeTypedList(business);
    }

    protected Mentions(Parcel parcel) {
        parcel.readTypedList(photographers, Photographer.CREATOR);
        parcel.readTypedList(business, Business.CREATOR);
    }
}
