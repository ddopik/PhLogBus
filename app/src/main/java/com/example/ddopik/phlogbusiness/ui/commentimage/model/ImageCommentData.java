package com.example.ddopik.phlogbusiness.ui.commentimage.model;

 import com.example.ddopik.phlogbusiness.base.commonmodel.Comment;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class ImageCommentData {
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("caption")
    @Expose
    public String caption;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("thumbnail_url")
    @Expose
    public String thumbnailUrl;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("comments")
    @Expose
    public List<Comment> comments = null;
}
