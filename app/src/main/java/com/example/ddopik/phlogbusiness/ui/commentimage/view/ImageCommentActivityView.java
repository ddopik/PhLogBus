package com.example.ddopik.phlogbusiness.ui.commentimage.view;


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

    void viewImageLikedStatus(boolean state);

    void viewOnImageCommented(Comment comment);

    void onImagedAddedToCart(boolean state);

    void viewImageRateStatus(ImageRateResponse imageRateResponse);

    void viewMessage(String msg);
}
