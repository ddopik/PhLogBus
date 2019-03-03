package com.example.ddopik.phlogbusiness.ui.commentimage.replay.view;

import com.example.ddopik.phlogbusiness.base.commonmodel.MentionedUser;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ImageCommentsData;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.SubmitImageCommentData;

import java.util.List;

public interface ReplayCommentActivityView {
    void viewReplies(ImageCommentsData imageCommentsData);

    void viewRepliesProgress(boolean state);

    void viewMessage(String msg);


    void onCommentReplied(SubmitImageCommentData submitImageCommentData);


}
