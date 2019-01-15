package com.example.ddopik.phlogbusiness.ui.commentimage.view;


import com.example.ddopik.phlogbusiness.base.commonmodel.Comment;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ImageCommentResponse;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ImageRateResponse;

import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public interface ImageCommentActivityView {
    void viewImageProgress(Boolean state);
    void viewPhotoComment(List<Comment> commentList);
//    void viewHeaderImageProgress(boolean state);

    void viewImageLikedStatus(boolean state);

    void ViewImageCommentStatus(ImageCommentResponse imageCommentResponse);

    void ViewImageRateStatus(ImageRateResponse imageRateResponse);

    void viewMessage(String msg);
}
