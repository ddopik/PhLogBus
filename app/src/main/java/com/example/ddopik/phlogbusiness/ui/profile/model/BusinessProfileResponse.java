package com.example.ddopik.phlogbusiness.ui.profile.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessProfileResponse {
    @SerializedName("data")
    @Expose
    public Business brand;
}
