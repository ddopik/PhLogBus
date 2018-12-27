package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class Campaign {


    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("descrption_en")
    @Expose
    public String descrptionEn;
    @SerializedName("image_cover")
    @Expose
    public String imageCover;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("max_images_per_photographer")
    @Expose
    public Integer maxImagesPerPhotographer;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("max_images")
    @Expose
    public Integer maxImages;
    @SerializedName("title_en")
    @Expose
    public String titleEn;
    @SerializedName("prize")
    @Expose
    public String prize;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("business")
    @Expose
    public Business business;
    @SerializedName("tags")
    @Expose
    public List<Tag> tags = null;
    @SerializedName("days_left")
    @Expose
    public Integer daysLeft;

}
