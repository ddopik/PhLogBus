package com.example.ddopik.phlogbusiness.ui.album.view;


import com.example.ddopik.phlogbusiness.base.commonmodel.Comment;

import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public interface ImageCommentActivityView {
    void viewAddCommentProgress(Boolean state);
    void viewPhotoComment(List<Comment> commentList);
    void viewMessage(String msg);
}
