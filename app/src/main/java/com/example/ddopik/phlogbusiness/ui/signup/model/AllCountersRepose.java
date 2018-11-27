package com.example.ddopik.phlogbusiness.ui.signup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllCountersRepose {


    @SerializedName("countries")
    @Expose
    public List<Country> countries = null;
    @SerializedName("state")
    @Expose
    public String state;
}
