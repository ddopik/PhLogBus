package com.example.ddopik.phlogbusiness.ui.campaigns.model;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/2/2018.
 */
public class CampaignResponse {


        @SerializedName("data")
        @Expose
        public CampaignData data;
}
