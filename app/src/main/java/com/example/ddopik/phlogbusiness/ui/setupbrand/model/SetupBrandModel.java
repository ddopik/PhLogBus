package com.example.ddopik.phlogbusiness.ui.setupbrand.model;

import com.google.gson.annotations.SerializedName;

public class SetupBrandModel {

    public transient String thumbnail;

    public transient String cover;

    public transient boolean thumbnailChanged;

    public transient boolean coverChanged;

    @SerializedName("name_ar")
    public String arabicBrandName;

    @SerializedName("name_en")
    public String englishBrandName;

    @SerializedName("industry_id")
    public Integer industryId;

    @SerializedName("brand_phone")
    public String phone;

    @SerializedName("brand_address")
    public String address;

    @SerializedName("brand_email")
    public String email;

    @SerializedName("website")
    public String webSite;

    @SerializedName("description")
    public String desc;

}
