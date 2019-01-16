package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class Comment {


    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("comment")
    @Expose
    public String comment;

    @SerializedName("photographer")
    @Expose
    public Photographer photographer;


    @SerializedName("parent_id")
    @Expose
    public String parentId;

    @SerializedName("replies_count")
    @Expose
    public Integer repliesCount;


    @SerializedName("business")
    @Expose
    public Business business;

}
