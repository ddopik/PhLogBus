package com.example.ddopik.phlogbusiness.ui.commentimage.model;

import com.example.ddopik.phlogbusiness.base.commonmodel.Mentions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentRepliesData {
    @SerializedName("comments")
    @Expose
    public CommentsRepliesData comments;
    @SerializedName("mentions")
    @Expose
    public Mentions mentions;
}
