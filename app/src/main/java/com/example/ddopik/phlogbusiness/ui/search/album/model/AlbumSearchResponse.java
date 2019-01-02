package com.example.ddopik.phlogbusiness.ui.search.album.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class AlbumSearchResponse {

    @SerializedName("data")
    @Expose
    public AlbumSearchData data;

}
