package com.example.ddopik.phlogbusiness.ui.album.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class AlbumImgCommentResponse {
    @SerializedName("data")
    @Expose
    public AlbumImgCommentData data;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("state")
    @Expose
    public String state;

}
