package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Mentions {
    @SerializedName("photographers")
    @Expose
    public List<Photographer> photographers = new ArrayList<>();
    @SerializedName("business")
    @Expose
    public List<Business> business = new ArrayList<>();
}
