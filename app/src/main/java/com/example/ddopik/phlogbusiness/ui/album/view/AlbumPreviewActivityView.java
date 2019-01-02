package com.example.ddopik.phlogbusiness.ui.album.view;


import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.ui.album.model.AlbumPreviewResponseData;

import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public interface AlbumPreviewActivityView {


    void viewAlumPreviewData(AlbumPreviewResponseData albumPreviewResponseData);

    void viwAlbumPreviewImages(List<BaseImage> baseImageList);

    void viewAlbumPreviewProgress(boolean state);
}
