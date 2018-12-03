package com.example.ddopik.phlogbusiness.ui.album.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 11/7/2018.
 */
public class AllAblbumListParcable implements Parcelable {

    public List<AlbumImg> albumImgList;



    public AllAblbumListParcable(){
        albumImgList=new ArrayList<>();
    }

    public AllAblbumListParcable(Parcel in) {

        albumImgList = new ArrayList<AlbumImg>();
        in.readTypedList(albumImgList, AlbumImg.CREATOR);
    }

    public static final Creator<AllAblbumListParcable> CREATOR = new Creator<AllAblbumListParcable>() {
        @Override
        public AllAblbumListParcable createFromParcel(Parcel in) {
            return new AllAblbumListParcable(in);
        }

        @Override
        public AllAblbumListParcable[] newArray(int size) {
            return new AllAblbumListParcable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel outParcel, int flags) {

        outParcel.writeTypedList(albumImgList);
    }

    public List<AlbumImg> getAlbumImgList() {
        return albumImgList;
    }

    public void setAlbumImgList(List<AlbumImg> albumImgList) {
        this.albumImgList = albumImgList;
    }
}
