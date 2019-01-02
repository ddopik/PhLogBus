package com.example.ddopik.phlogbusiness.ui.search.album.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public class SearchFiltersResponse {

    @SerializedName("data")
    @Expose
    public List<SearchFilter> data = null;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("state")
    @Expose
    public String state;

}
