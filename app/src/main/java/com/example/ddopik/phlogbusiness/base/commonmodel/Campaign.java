package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
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

    @SerializedName("is_joined")
    @Expose
    public boolean isJoined;
    @SerializedName("photos_count")
    @Expose
    public int photosCount;

    @SerializedName("winners_count")
    public int winnerCount;

    @SerializedName("brand_image_cover")
    public String brandImageCover;

    @SerializedName("status_string")
    public String statusString;

    @SerializedName("joined_photographers_count")
    public int joinedPhotographersCount;

    @SerializedName("won_photos_count")
    public int wonPhotosCount;

}
