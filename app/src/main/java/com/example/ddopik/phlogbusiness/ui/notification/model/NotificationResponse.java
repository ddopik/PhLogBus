package com.example.ddopik.phlogbusiness.ui.notification.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class NotificationResponse {
    @SerializedName("data")
    @Expose
    public NotificationData data;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("state")
    @Expose
    public String state;
}
