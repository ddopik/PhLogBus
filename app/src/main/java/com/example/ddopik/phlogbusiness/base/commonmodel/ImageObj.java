package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class ImageObj {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("photographer_id")
    @Expose
    public Integer photographerId;
    @SerializedName("caption")
    @Expose
    public String caption;
    @SerializedName("is_for_sale")
    @Expose
    public String isForSale;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("can_sold_exclussive")
    @Expose
    public String canSoldExclussive;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public String deletedAt;


    @SerializedName("filters")
    @Expose
    public String filters;
    @SerializedName("thumbnail_url")
    @Expose
    public String thumbnailUrl;
    @SerializedName("url")
    @Expose
    public String url;

    @SerializedName("photographer")
    @Expose
    public Photographer photographer;
    @SerializedName("is_saved")
    @Expose
    public Boolean isSaved;
    @SerializedName("is_liked")
    @Expose
    public Boolean isLiked;
    @SerializedName("comments_count")
    @Expose
    public Integer commentsCount;
    @SerializedName("saves_count")
    @Expose
    public Integer savesCount;
    @SerializedName("likes_count")
    @Expose
    public Integer likesCount;
    @SerializedName("rate_total")
    @Expose
    public Integer rateTotal;
    @SerializedName("rate_sum")
    @Expose
    public String rateSum;

}
