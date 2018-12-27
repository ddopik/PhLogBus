package com.example.ddopik.phlogbusiness.ui.brand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 11/12/2018.
 */
public class BrandInnerData {

    @SerializedName("industry")
    @Expose
    public BrandIndustry industry;
    @SerializedName("descrption")
    @Expose
    public String descrption;
    @SerializedName("website")
    @Expose
    public String website;
    @SerializedName("mail")
    @Expose
    public String mail;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("cover_image")
    @Expose
    public String coverImage;
    @SerializedName("name_en")
    @Expose
    public String nameEn;
    @SerializedName("number_of_followers")
    @Expose
    public Integer numberOfFollowers;
    @SerializedName("id")
    @Expose
    public Integer id;
}
