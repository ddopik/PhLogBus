package com.example.ddopik.phlogbusiness.ui.album.view;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public interface AllAlbumImgActivityView {
    void showMessage(String msg);

    void viewAlbumImageList(List<BaseImage> albumImgList);

    void viewAlbumImageListProgress(boolean state);

    void onImageAddedToCard(BaseImage baseImage, boolean state);

    void onImagePhotoGrapherFollowed(BaseImage baseImage, boolean state);

    void onImagePhotoGrapherLiked(int photoID, boolean state);

    void navigateToImageDetails(BaseImage baseImage);

}
