package com.example.softmills.phlog.ui.search.view.album.model

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by abdalla_maged on 11/6/2018.
 */
class AlbumSearch {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("photos")
    @Expose
    var photos: MutableList<BaseImage>? = null
    @SerializedName("photos_count")
    @Expose
    var photosCount: Int? = null


}
