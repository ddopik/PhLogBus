package com.example.ddopik.phlogbusiness.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged on 10/10/2018.
 */
public class BaseApiResponse {
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("msg")
    @Expose
    public String msg;

}
