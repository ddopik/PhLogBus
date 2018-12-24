package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class BaseErrorResponse {
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("code")
    @Expose
    public String code;
}
