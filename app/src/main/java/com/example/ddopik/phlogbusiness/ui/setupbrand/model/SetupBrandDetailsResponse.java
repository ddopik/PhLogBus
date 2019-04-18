package com.example.ddopik.phlogbusiness.ui.setupbrand.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.google.gson.annotations.SerializedName;

public class SetupBrandDetailsResponse {
    @SerializedName("data")
    private Business business;

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
}
