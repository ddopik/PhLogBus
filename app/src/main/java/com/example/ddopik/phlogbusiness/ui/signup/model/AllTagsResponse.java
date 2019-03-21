package com.example.ddopik.phlogbusiness.ui.signup.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.Tag;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllTagsResponse {
    @SerializedName("data")
    @Expose
    public List<Tag> data = null;
}
