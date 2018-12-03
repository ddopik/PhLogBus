package com.example.ddopik.phlogbusiness.ui.album.view;

import com.example.ddopik.phlogbusiness.base.model.ImageObj;
import com.example.ddopik.phlogbusiness.ui.album.model.AlbumImg;

import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public interface AllAlbumImgActivityView  {
    void viewAlbumImageList(List<AlbumImg> albumImgList);
    void viewAlbumImageListProgress(boolean state);
}
