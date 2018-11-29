package com.example.ddopik.phlogbusiness.ui.campaigns.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/2/2018.
 */
public class HomeCampaign {


    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("business_id")
    @Expose
    public Integer businessId;
    @SerializedName("image_cover")
    @Expose
    public String imageCover;
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("title_en")
    @Expose
    public String titleEn;
    @SerializedName("title_ar")
    @Expose
    public String titleAr;
    @SerializedName("descrption_en")
    @Expose
    public String descrptionEn;
    @SerializedName("descrption_ar")
    @Expose
    public String descrptionAr;
    @SerializedName("number_images")
    @Expose
    public String numberImages;
    @SerializedName("prize")
    @Expose
    public String prize;
    @SerializedName("is_publish")
    @Expose
    public String isPublish;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public String deletedAt;
    @SerializedName("left_days")
    @Expose
    public String leftDays;
    @SerializedName("business")
    @Expose
    public CampaignBussines business;




}
