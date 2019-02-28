package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("device_id")
    public final String id;
    @SerializedName("device_model")
    public final String model;
    @SerializedName("state")
    public final boolean state;
    @SerializedName("firebase_token")
    public final String firebaseToken;

    public Device(String id, String model, boolean state, String firebaseToken) {
        this.id = id;
        this.model = model;
        this.state = state;
        this.firebaseToken = firebaseToken;
    }
}
