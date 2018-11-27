package com.example.ddopik.phlogbusiness.ui.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {



    @SerializedName("data")
    @Expose
    public LoginData loginData;
    @SerializedName("state")
    @Expose
    public String state;



}
