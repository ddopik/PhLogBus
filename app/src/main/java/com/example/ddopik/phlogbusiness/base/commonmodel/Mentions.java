package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Mentions {
    @SerializedName("photographers")
    @Expose
    public List<Photographer> photographers = null;
    @SerializedName("business")
    @Expose
    public List<Business> business = null;
}
