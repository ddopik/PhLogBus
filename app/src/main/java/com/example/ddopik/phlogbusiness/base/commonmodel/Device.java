package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("device_id")
    public final String id;
    @SerializedName("status")
    public final int status;
    @SerializedName("firebase_token")
    public final String firebaseToken;

    public Device(String id, boolean status, String firebaseToken) {
        this.id = id;
        this.status = status ? Constants.STATUS_LOGGED_IN : Constants.STATUS_LOGGED_OUT;
        this.firebaseToken = firebaseToken;
    }
}