package com.example.ddopik.phlogbusiness.base.commonmodel;

import com.example.ddopik.phlogbusiness.ui.album.model.ComentedUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class Comment {
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("comment")
    @Expose
    public String comment;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("photographer")
    @Expose
    public Photographer photographer;


}
