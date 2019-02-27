package com.example.ddopik.phlogbusiness.ui.commentimage.presenter;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

public interface ReplayCommentPresenter {
    void getReplies(int commentId, int imageId,int page);
    void submitReplayComment(String imageId,String parentReplayId,String comment);
    void getMentionedUser(String key);

}
