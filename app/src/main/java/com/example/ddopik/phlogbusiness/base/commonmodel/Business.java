package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class Business {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("image_cover")
    @Expose
    public String imageCover;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("full_name")
    @Expose
    public String fullName;
    @SerializedName("web_site")
    @Expose
    public String website;
    @SerializedName("industry")
    @Expose
    public Industry industry;
}
