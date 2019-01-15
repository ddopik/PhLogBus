package com.example.ddopik.phlogbusiness.ui.commentimage.model;

 import com.example.ddopik.phlogbusiness.base.commonmodel.Comment;
 import com.example.ddopik.phlogbusiness.base.commonmodel.Mentions;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class SubmitImageCommentData {
    @SerializedName("comment")
    @Expose
    public Comment comment;
    @SerializedName("mentions")
    @Expose
    public Mentions mentions;
}
