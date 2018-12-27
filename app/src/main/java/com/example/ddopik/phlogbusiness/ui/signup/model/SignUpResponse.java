package com.example.ddopik.phlogbusiness.ui.signup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignUpResponse {

    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("msg")
    @Expose
    public String message;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("email")
    @Expose
    public List<String> email = null;
}
