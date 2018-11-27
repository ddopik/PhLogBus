package com.example.ddopik.phlogbusiness.ui.signup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {
    @Expose
    public Integer id;
    @SerializedName("name_en")
    @Expose
    public String nameEn;
    @SerializedName("name_ar")
    @Expose
    public String nameAr;
    @SerializedName("created_at")
    @Expose
    public Object createdAt;
    @SerializedName("updated_at")
    @Expose
    public Object updatedAt;
    @SerializedName("deleted_at")
    @Expose
    public Object deletedAt;
}
