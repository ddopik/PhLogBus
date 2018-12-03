package com.example.ddopik.phlogbusiness.ui.album.view;


import com.example.ddopik.phlogbusiness.ui.album.model.AlbumImgCommentData;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public interface AlbumCommentActivityView {
    void viewAddCommentProgress(Boolean state);
    void viewPhotoComment(AlbumImgCommentData albumImgCommentData);
}
