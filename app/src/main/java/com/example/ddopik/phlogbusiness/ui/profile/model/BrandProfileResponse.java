package com.example.ddopik.phlogbusiness.ui.profile.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.Brand;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandProfileResponse {
    @SerializedName("data")
    @Expose
    public Brand brand;
}
