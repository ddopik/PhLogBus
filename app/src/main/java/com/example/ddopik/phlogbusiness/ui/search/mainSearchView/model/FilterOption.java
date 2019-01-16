package com.example.ddopik.phlogbusiness.ui.search.mainSearchView.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public class FilterOption {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("filter_group_id")
    @Expose
    public Integer filterGroupId;
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

    public boolean isSelected;

}
