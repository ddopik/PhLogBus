package com.example.ddopik.phlogbusiness.ui.album.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class UserComment {

    @SerializedName("comment_id")
    @Expose
    public String commentId;
    @SerializedName("comment_text")
    @Expose
    public String commentText;
    @SerializedName("comment_user")
    @Expose
    public ComentedUser comentedUser;


}
