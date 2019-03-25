package com.example.ddopik.phlogbusiness.ui.album.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageDetailsResponse {
    @SerializedName("data")
    @Expose
    public BaseImage data;
}
