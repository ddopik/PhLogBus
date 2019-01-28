package com.example.ddopik.phlogbusiness.ui.commentimage.presenter;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface ImageCommentActivityPresenter {
    void getImageComments(String imageId, String page);
    void likePhoto(BaseImage baseImage);
    void unLikePhoto(BaseImage baseImage);
    void addImageToCart(int imageId);
    void rateImage(BaseImage baseImage,float rate);
    void submitComment(String imageId, String comment);
}
