package com.example.ddopik.phlogbusiness.base.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class Earning implements Parcelable {


    public String earningId;
    public String earningImg;
    public String earningPrice;
    public String earningBuyer;
    public String earningSize;

    public Earning(){

    }
    public Earning(Parcel in) {
        earningId = in.readString();
        earningImg = in.readString();
        earningPrice = in.readString();
        earningBuyer = in.readString();
        earningSize = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(earningId);
        dest.writeString(earningImg);
        dest.writeString(earningPrice);
        dest.writeString(earningBuyer);
        dest.writeString(earningSize);
    }

    @SuppressWarnings("unused")
    public static final Creator<Earning> CREATOR = new Creator<Earning>() {
        @Override
        public Earning createFromParcel(Parcel in) {
            return new Earning(in);
        }

        @Override
        public Earning[] newArray(int size) {
            return new Earning[size];
        }
    };
}