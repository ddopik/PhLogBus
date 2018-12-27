package com.example.ddopik.phlogbusiness.ui.campaigns.inner.model;

 import com.example.ddopik.phlogbusiness.base.commonmodel.ImageObj;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public class CampaignInnerPhoto extends ImageObj {

    @SerializedName("join_campaign_id")
    @Expose
    public Integer joinCampaignId;
    @SerializedName("image_photographer_id")
    @Expose
    public Integer imagePhotographerId;
    @SerializedName("is_won")
    @Expose
    public Integer isWon;


}
