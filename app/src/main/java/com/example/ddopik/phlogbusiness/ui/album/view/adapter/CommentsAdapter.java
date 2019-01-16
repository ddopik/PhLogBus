package com.example.ddopik.phlogbusiness.ui.album.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Comment;
import com.example.ddopik.phlogbusiness.base.commonmodel.Tag;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
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
    private int HEAD = 0;
    private int COMMENT = 1;
    private int ADD_COMMENT = 2;


    public CommentsAdapter(BaseImage previewImage, List<Comment> commentList) {
        this.commentList = commentList;
        this.previewImage = previewImage;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);


        if (i == HEAD) {
            return new CommentViewHolder(layoutInflater.inflate(R.layout.view_holder_comment_start_item, viewGroup, false), HEAD);
        } else if (i == commentList.size()) {
            return new CommentViewHolder(layoutInflater.inflate(R.layout.view_holder_image_send_comment, viewGroup, false), ADD_COMMENT);
        } else {
            return new CommentViewHolder(layoutInflater.inflate(R.layout.view_holder_image_comment, viewGroup, false), COMMENT);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {

        int x = getItemViewType(i);

        if (getItemViewType(i) == HEAD) {

            GlideApp.with(context)
                    .load(previewImage.url)
                    .error(R.drawable.default_error_img)
                    .placeholder(R.drawable.default_place_holder)
                    .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                    .into(commentViewHolder.commentImg);

            String tagS = "";
            if (previewImage.tags != null)
                for (Tag tag : previewImage.tags) {
                    tagS = tagS + " #" + tag.name;
                }
            commentViewHolder.commentPreviewImgTags.setText(tagS);
            if (previewImage.likesCount != null)
                commentViewHolder.imgLikeVal.setText(new StringBuilder().append(previewImage.likesCount).append(" ").append(context.getResources().getString(R.string.like)).toString());
            if (previewImage.commentsCount != null)
                commentViewHolder.imgCommentVal.setText(new StringBuilder().append(previewImage.commentsCount).append(" ").append(context.getResources().getString(R.string.comment)).toString());
            if (commentAdapterAction != null) {
                commentViewHolder.imageCommentBtn.setOnClickListener(v -> {
                    commentViewHolder.imgCommentVal.requestFocus();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                });


                if (previewImage.isLiked) {
                    commentViewHolder.imageLikeBtn.setImageResource(R.drawable.ic_like_off);
                } else {
                    commentViewHolder.imageLikeBtn.setImageResource( R.drawable.ic_like_on);
                }

                commentViewHolder.imageLikeBtn.setOnClickListener(v -> {

                    commentAdapterAction.onImageLike(previewImage);
                });
            }

        } else if (getItemViewType(i) == COMMENT) {


            if (commentList.get(i).business != null) {

                if(commentList.get(i).business.thumbnail !=null)
                    GlideApp.with(context)
                            .load(commentList.get(i).business.thumbnail)
                            .error(R.drawable.default_error_img)
                            .placeholder(R.drawable.default_place_holder)
                            .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                            .apply(RequestOptions.circleCropTransform())
                            .into(commentViewHolder.commentAuthorImg);
                commentViewHolder.commentAuthorName.setText(commentList.get(i).business.firstName +" "+commentList.get(i).business.lastName);


            }else if (commentList.get(i).photographer != null){
                if(commentList.get(i).photographer.imageProfile !=null)
                    GlideApp.with(context)
                            .load(commentList.get(i).photographer.imageProfile)
                            .error(R.drawable.default_error_img)
                            .placeholder(R.drawable.default_place_holder)
                            .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                            .apply(RequestOptions.circleCropTransform())
                            .into(commentViewHolder.commentAuthorImg);
                commentViewHolder.commentAuthorName.setText(commentList.get(i).photographer.fullName);

            }
            commentViewHolder.commentVal.setText(commentList.get(i).comment);

        } else if (getItemViewType(i) == ADD_COMMENT) {
            if (commentAdapterAction != null) {
                commentViewHolder.sendCommentBtn.setOnClickListener(v -> {
                    commentAdapterAction.onSubmitComment(commentViewHolder.sendCommentImgVal.getText().toString());
                    commentViewHolder.sendCommentImgVal.getText().clear();
                });
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD; //-->For Image Header Preview
        } else if (position == (commentList.size() - 1)) {
            return ADD_COMMENT; //---> normal Comment viewHolder
        } else {
            return COMMENT; //--->  Comment Cell
        }
    }


    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        CustomTextView imgLikeVal, imgCommentVal, commentPreviewImgTags;
        ImageView commentImg;
        ImageButton imageLikeBtn, imageCommentBtn;
        ///Comment_value Cell
        TextView commentVal;
        CustomTextView commentAuthorName;
        ImageView commentAuthorImg;
        //SendCommentCell
        EditText sendCommentImgVal;
        ImageButton sendCommentBtn;


        CommentViewHolder(View view, int type) {
            super(view);
            if (type == HEAD) {

                commentImg = view.findViewById(R.id.comment_preview_img);
                commentPreviewImgTags = view.findViewById(R.id.comment_preview_img_tag);
                imageLikeBtn = view.findViewById(R.id.comment_preview_img_like_btn);
                imageCommentBtn = view.findViewById(R.id.comment_preview_img_comment_btn);

                imgLikeVal = view.findViewById(R.id.comment_preview_img_like_val);
                imgCommentVal = view.findViewById(R.id.comment_preview_img_comment_val);
            } else if (type == COMMENT) {
                commentVal = view.findViewById(R.id.comment_val);
                commentAuthorImg = view.findViewById(R.id.commentAuthorImg);
                commentAuthorName = view.findViewById(R.id.comment_author);
            } else if (type == ADD_COMMENT) {
                sendCommentImgVal = view.findViewById(R.id.img_send_comment_val);
                sendCommentBtn = view.findViewById(R.id.send_comment_btn);
            }

        }

    }

    public interface CommentAdapterAction {
        void onImageLike(BaseImage baseImage);


        void onSubmitComment(String comment);
    }
}
