package com.example.ddopik.phlogbusiness.ui.search.album.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public class SearchFilter {
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("options")
    @Expose
    public List<FilterOption> options = null;

}
