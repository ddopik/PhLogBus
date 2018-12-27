package com.example.ddopik.phlogbusiness.ui.signup.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseResponse;
import com.example.ddopik.phlogbusiness.base.commonmodel.Brand;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class UploadProfileImgResponse {

    @SerializedName("data")
    @Expose
    public Brand data;

}
