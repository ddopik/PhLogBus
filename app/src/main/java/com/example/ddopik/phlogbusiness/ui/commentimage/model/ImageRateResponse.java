package com.example.ddopik.phlogbusiness.ui.commentimage.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageRateResponse   {
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("data")
    @Expose
    public BaseImage baseImage;

}
