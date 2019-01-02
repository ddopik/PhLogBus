package com.example.ddopik.phlogbusiness.ui.search.album.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public class FilterOption {

    @SerializedName("options")
    @Expose
    public String id;


    @SerializedName("name")
    @Expose
    public String name;


    public boolean isSelected = false;


}
