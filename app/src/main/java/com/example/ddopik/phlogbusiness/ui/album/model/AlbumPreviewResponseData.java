package com.example.ddopik.phlogbusiness.ui.album.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class AlbumPreviewResponseData {
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("photos_count")
    @Expose
    public Integer photosCount;
    @SerializedName("preview")
    @Expose
    public String preview;

}
