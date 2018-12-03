package com.example.ddopik.phlogbusiness.ui.album.view;


import com.example.ddopik.phlogbusiness.ui.album.model.AlbumPreviewResponseData;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public interface AlbumPreviewActivityView {


    void viewAlumPreview(AlbumPreviewResponseData albumPreviewResponseData);

    void viewAlbumPreviewProgress(boolean state);
}
