package com.example.ddopik.phlogbusiness.ui.album.presenter;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

public interface AllAlbumImgActivityPresenter {

    void getAlbumImages(int albumId,int  pageNum);

    void likeImage(int imageId);

 }
