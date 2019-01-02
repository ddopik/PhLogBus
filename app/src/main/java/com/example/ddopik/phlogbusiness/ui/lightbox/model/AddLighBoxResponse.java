package com.example.ddopik.phlogbusiness.ui.lightbox.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddLighBoxResponse {
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("state")
    @Expose
    public String state;
}
