package com.example.ddopik.phlogbusiness.ui.social.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class SocialResponse {

    @SerializedName("data")
    @Expose
    public List<SocialData> data = null;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("state")
    @Expose
    public String state;

}
