package com.example.ddopik.phlogbusiness.ui.commentimage.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.Mentions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageCommentsData {

    @SerializedName("comments")
    @Expose
    public ImageCommentsObj comments;
    @SerializedName("mentions")
    @Expose
    public Mentions mentions;

    @SerializedName("first_page_url")
    @Expose
    public String firstPageUrl;
    @SerializedName("from")
    @Expose
    public Integer from;
    @SerializedName("last_page")
    @Expose
    public Integer lastPage;
    @SerializedName("last_page_url")
    @Expose
    public String lastPageUrl;
    @SerializedName("next_page_url")
    @Expose
    public String nextPageUrl;
    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("per_page")
    @Expose
    public Integer perPage;
    @SerializedName("prev_page_url")
    @Expose
    public Object prevPageUrl;
    @SerializedName("to")
    @Expose
    public Integer to;
    @SerializedName("total")
    @Expose
    public Integer total;
}
