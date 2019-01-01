package com.example.ddopik.phlogbusiness.ui.lightbox.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.LightBox;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrandLightBoxResponse {
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("data")
    @Expose
    public List<LightBox> data = null;
    @SerializedName("state")
    @Expose
    public String state;

}
