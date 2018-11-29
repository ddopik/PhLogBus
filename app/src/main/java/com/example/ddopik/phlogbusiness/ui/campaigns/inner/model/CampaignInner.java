package com.example.ddopik.phlogbusiness.ui.campaigns.inner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public class CampaignInner {
    @SerializedName("id")
    @Expose
    public Integer id;
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
    public Integer numberImages;
    @SerializedName("prize")
    @Expose
    public String prize;
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
}
