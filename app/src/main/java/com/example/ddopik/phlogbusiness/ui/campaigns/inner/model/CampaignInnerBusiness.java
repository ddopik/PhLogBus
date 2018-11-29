package com.example.ddopik.phlogbusiness.ui.campaigns.inner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public class CampaignInnerBusiness {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name_en")
    @Expose
    public String nameEn;
    @SerializedName("image_cover")
    @Expose
    public String imageCover;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("credit_card_id")
    @Expose
    public String creditCardId;
    @SerializedName("phone")
    @Expose
    public Integer phone;
    @SerializedName("name_ar")
    @Expose
    public String nameAr;
    @SerializedName("is_brand")
    @Expose
    public String isBrand;
    @SerializedName("description_en")
    @Expose
    public String descriptionEn;
    @SerializedName("description_ar")
    @Expose
    public String descriptionAr;
    @SerializedName("website")
    @Expose
    public String website;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("industry_id")
    @Expose
    public String industryId;
    @SerializedName("user_names_id")
    @Expose
    public String userNamesId;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public String deletedAt;
    @SerializedName("user_name")
    @Expose
    public String userName;
}
