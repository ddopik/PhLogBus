package com.example.ddopik.phlogbusiness.ui.album.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class AlbumPreviewResponse {
    @SerializedName("data")
    @Expose
    public AlbumPreviewResponseData data;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("state")
    @Expose
    public String state;
}
