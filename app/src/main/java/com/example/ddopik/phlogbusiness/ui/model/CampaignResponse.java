package com.example.ddopik.phlogbusiness.ui.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class CampaignResponse {
    @SerializedName("current_page")
    @Expose
    public Integer currentPage;
    @SerializedName("data")
    @Expose
    public List<Campaign> data = null;
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
    public Object nextPageUrl;
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
