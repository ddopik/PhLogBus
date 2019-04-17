package com.example.ddopik.phlogbusiness.ui.splash.model;

import com.google.gson.annotations.SerializedName;

public class CheckVersionReponse {
    private @SerializedName("data") CheckVersionData data;

    public CheckVersionData getData() {
        return data;
    }

    public void setData(CheckVersionData data) {
        this.data = data;
    }
}
