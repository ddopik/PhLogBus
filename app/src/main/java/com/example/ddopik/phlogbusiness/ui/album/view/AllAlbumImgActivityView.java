package com.example.ddopik.phlogbusiness.ui.album.view;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public interface AllAlbumImgActivityView  {
    void viewAlbumImageList(List<BaseImage> albumImgList);
    void viewAlbumImageListProgress(boolean state);
    void viewMessage(String msg);
}
