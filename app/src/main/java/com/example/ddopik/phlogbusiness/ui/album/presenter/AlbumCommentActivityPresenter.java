package com.example.ddopik.phlogbusiness.ui.album.presenter;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface AlbumCommentActivityPresenter {
    void getImageComments(String imageId, String page);
    void submitComment(String imageId, String comment);
}
