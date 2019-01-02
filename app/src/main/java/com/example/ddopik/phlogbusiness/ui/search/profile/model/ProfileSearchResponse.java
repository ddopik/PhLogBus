package com.example.ddopik.phlogbusiness.ui.search.profile.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 11/1/2018.
 */
public class ProfileSearchResponse {


    @SerializedName("data")
    @Expose
    public List<Photographer> data = null;

}
