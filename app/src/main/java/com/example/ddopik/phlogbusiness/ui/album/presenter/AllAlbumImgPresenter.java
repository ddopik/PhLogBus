package com.example.ddopik.phlogbusiness.ui.album.presenter;


import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

/**
 * Created by abdalla_maged On Dec,2018
 */
public interface AllAlbumImgPresenter {

    void likePhoto(BaseImage baseImage);

    void addAlbumImageToCart(BaseImage baseImage);

    void followImagePhotoGrapher(BaseImage baseImage);

    void getPhotoDetails(int imgId);
}
