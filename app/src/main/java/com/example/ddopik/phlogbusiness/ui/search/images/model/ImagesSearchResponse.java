package com.example.ddopik.phlogbusiness.ui.search.images.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public class ImagesSearchResponse {
    @SerializedName("data")
    @Expose
    public ImagesSearchData data;

}
