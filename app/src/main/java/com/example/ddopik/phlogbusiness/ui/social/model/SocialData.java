package com.example.ddopik.phlogbusiness.ui.social.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class SocialData {
    @SerializedName("entity_id")
    @Expose
    public int entityId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("gotoListImgs_Or_singleImg_screen")
    @Expose
    public Boolean gotoListImgsOrSingleImgScreen;
    @SerializedName("entites")
    @Expose
    public List<Entite> entites = null;
    @SerializedName("album_name ")
    @Expose
    public String albumName;

}
