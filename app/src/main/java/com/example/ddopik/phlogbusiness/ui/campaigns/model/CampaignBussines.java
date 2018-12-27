package com.example.ddopik.phlogbusiness.ui.campaigns.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/9/2018.
 */
public class CampaignBussines {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("user_name")
    @Expose
    public String userName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;



}
