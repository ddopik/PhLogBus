package com.example.ddopik.phlogbusiness.ui.commentimage.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SocialAutoCompleteData {
    @SerializedName("photographers")
    @Expose
    public List<Photographer> photographers;
    @SerializedName("businesses")
    @Expose
    public List<Business> businesses;
}
