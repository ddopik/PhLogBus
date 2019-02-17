package com.example.ddopik.phlogbusiness.ui.album.presenter;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public interface AlbumPreviewActivityPresenter {


    void getSelectedSearchAlbum(int albumID, String pageNum);

    void getAlbumPreviewImages(int albumId, int page);

}
