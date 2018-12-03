package com.example.ddopik.phlogbusiness.ui.brand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 11/12/2018.
 */
public class BrandInnerResponse {
    @SerializedName("data")
    @Expose
    public BrandInnerData data;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("state")
    @Expose
    public String state;
}
