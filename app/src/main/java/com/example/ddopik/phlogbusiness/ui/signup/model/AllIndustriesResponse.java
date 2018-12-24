package com.example.ddopik.phlogbusiness.ui.signup.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.Industry;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllIndustriesResponse {


    @SerializedName("countries")
    @Expose
    public List<Industry> industryList = null;
    @SerializedName("state")
    @Expose
    public String state;
}
