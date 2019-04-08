package com.example.ddopik.phlogbusiness.ui.search.profile.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 11/1/2018.
 */
public class ProfileSearchData {
    @SerializedName("current_page")
    @Expose
    public String currentPage;
    @SerializedName("data")
    @Expose
    public List<Photographer> data =new ArrayList<>();
    @SerializedName("first_page_url")
    @Expose
    public String firstPageUrl;
    @SerializedName("from")
    @Expose
    public String from;
    @SerializedName("last_page")
    @Expose
    public String lastPage;
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
    public String perPage;
    @SerializedName("prev_page_url")
    @Expose
    public String prevPageUrl;
    @SerializedName("to")
    @Expose
    public String to;
    @SerializedName("total")
    @Expose
    public String total;
}
