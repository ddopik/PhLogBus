package com.example.ddopik.phlogbusiness.ui.welcome.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WelcomeScreenResponse {



        @SerializedName("init_slider")
        @Expose
        public List<InitSlider> initSlider = null;
        @SerializedName("state")
        @Expose
        public String state;


}
