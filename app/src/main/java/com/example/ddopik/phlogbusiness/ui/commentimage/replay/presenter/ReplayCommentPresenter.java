package com.example.ddopik.phlogbusiness.ui.commentimage.replay.presenter;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

public interface ReplayCommentPresenter {
    void getReplies(int commentId, int imageId,String page);
    void submitReplayComment(String imageId,String parentReplayId,String comment);

}
