package com.example.ddopik.phlogbusiness.ui.album.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Comment;
import com.example.ddopik.phlogbusiness.base.commonmodel.Tag;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;


import java.util.List;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> commentList;
    private BaseImage previewImage;
    public CommentAdapterAction commentAdapterAction;


    public CommentsAdapter(BaseImage previewImage, List<Comment> commentList) {
        this.commentList = commentList;
        this.previewImage = previewImage;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (getItemViewType(i) == 0) {
            return new CommentViewHolder(layoutInflater.inflate(R.layout.view_holder_comment_start_item, viewGroup, false), 0);
        } else {
            return new CommentViewHolder(layoutInflater.inflate(R.layout.view_holder_image_comment, viewGroup, false), 1);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {


        if (getItemViewType(i) == 0) {
            if(previewImage.photographer !=null) {
                commentViewHolder.imageAuthorName.setText(previewImage.photographer.fullName);
                commentViewHolder.imageAuthorUserName.setText(previewImage.photographer.userName);
            }
            GlideApp.with(context)
                    .load(previewImage.url)
                    .error(R.drawable.default_error_img)
                    .placeholder(R.drawable.default_place_holder)
                    .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                    .into(commentViewHolder.commentImg);

            String tagS="";
            if(previewImage.tags !=null)
            for (Tag tag : previewImage.tags) {
                tagS=tagS +" #"+ tag.name ;
            }
            commentViewHolder.commentPreviewImgTags.setText(tagS);
            if (previewImage.likesCount != null)
                commentViewHolder.imgLikeVal.setText(new StringBuilder().append(previewImage.likesCount).append(" ").append(context.getResources().getString(R.string.like)).toString());
            if (previewImage.commentsCount != null)
                commentViewHolder.imgCommentVal.setText(new StringBuilder().append(previewImage.commentsCount).append(" ").append(context.getResources().getString(R.string.comment)).toString());
            if(commentAdapterAction !=null){
                commentViewHolder.imageCommentBtn.setOnClickListener(v->{
                    commentAdapterAction.onImageComment(previewImage);
                });
                commentViewHolder.imageLikeBtn.setOnClickListener(v->{
                    commentAdapterAction.onImageLike(previewImage);
                });
            }

        } else {
            if (commentList.get(i).id != null) {
                GlideApp.with(context)
                        .load(commentList.get(i).photographer.imageProfile)
                        .error(R.drawable.default_error_img)
                        .placeholder(R.drawable.default_place_holder)
                        .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                        .apply(RequestOptions.circleCropTransform())
                        .into(commentViewHolder.commentAuthorImg);

                commentViewHolder.commentVal.setText(commentList.get(i).comment);
            }
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0; //-->For Image Header Preview
        } else {
            return 1; //---> normal Comment Cell
        }
    }


    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView imageAuthorName, imageAuthorUserName, imgLikeVal, imgCommentVal,commentPreviewImgTags;
        ImageView commentImg;
        ImageButton imageLikeBtn, imageCommentBtn, ImgDownload;
        ///Comment Cell
        TextView commentVal;
        ImageView commentAuthorImg;

        CommentViewHolder(View view, int type) {
            super(view);
            if (type == 0) {
                imageAuthorName = view.findViewById(R.id.image_author_full_name);
                imageAuthorUserName = view.findViewById(R.id.image_author_user_name);
                commentImg = view.findViewById(R.id.comment_preview_img);
                commentPreviewImgTags=view.findViewById(R.id.comment_preview_img_tag);
                imageLikeBtn = view.findViewById(R.id.comment_preview_img_like_btn);
                imageCommentBtn = view.findViewById(R.id.comment_preview_img_comment_btn);
                ImgDownload = view.findViewById(R.id.comment_preview_img_download);
                imgLikeVal = view.findViewById(R.id.comment_preview_img_like_val);
                imgCommentVal = view.findViewById(R.id.comment_preview_img_comment_val);
            } else {
                commentVal = view.findViewById(R.id.comment_val);
                commentAuthorImg = view.findViewById(R.id.commentAuthorImg);
            }

        }

    }

    public interface CommentAdapterAction{
        void onImageLike(BaseImage baseImage);
        void onImageComment(BaseImage baseImage);
    }
}
