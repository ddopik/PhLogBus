package com.example.ddopik.phlogbusiness.ui.campaigns.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 11/15/2018.
 */
public class FollowBrandResponse {
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("state")
    @Expose
    public String state;

}
