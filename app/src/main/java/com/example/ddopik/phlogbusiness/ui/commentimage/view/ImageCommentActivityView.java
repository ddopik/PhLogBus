package com.example.ddopik.phlogbusiness.ui.commentimage.view;


import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Comment;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ImageCommentsData;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ImageRateResponse;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public interface ImageCommentActivityView {
    void viewImageProgress(Boolean state);

    void viewPhotoComment(ImageCommentsData imageCommentsData);

    //    void viewHeaderImageProgress(boolean state);
    void onImageCommented(Comment comment);

    void onImageLiked(BaseImage baseImage);

    void onImagedAddedToCart(boolean state);

    void onImageRate(BaseImage baseImage);

    void viewMessage(String msg);
}
