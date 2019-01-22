package com.example.ddopik.phlogbusiness.ui.campaigns.inner.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public class CampaignInnerResponse {

    @SerializedName("data")
    public Campaign campaign;
    @SerializedName("state")
    @Expose
    public String state;

}
