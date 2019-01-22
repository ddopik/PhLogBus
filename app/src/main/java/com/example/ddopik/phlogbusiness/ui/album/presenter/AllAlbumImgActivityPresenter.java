package com.example.ddopik.phlogbusiness.ui.album.presenter;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

public interface AllAlbumImgActivityPresenter {

    void getAlbumImages(int albumId,int  pageNum);
    void addImageToCart(int imageId);

    void likeImage(int imageId);
    void unLikeImage(int imageId);

 }
