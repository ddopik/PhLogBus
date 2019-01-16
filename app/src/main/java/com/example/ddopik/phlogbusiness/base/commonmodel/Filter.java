package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.model.FilterOption;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public class Filter {




    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("system_name")
    @Expose
    public String systemName;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public Object deletedAt;
    @SerializedName("display_name")
    @Expose
    public String displayName;
    @SerializedName("filters")
    @Expose
    public List<FilterOption> options = null;

}
