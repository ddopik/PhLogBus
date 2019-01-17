package com.example.ddopik.phlogbusiness.ui.album.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.*;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> commentList;
    private Mentions mentions;


    private BaseImage previewImage;
    public CommentAdapterAction commentAdapterAction;
    private int HEAD = 0;
    private int COMMENT = 1;
    private int ADD_COMMENT = 2;


    public CommentsAdapter(BaseImage previewImage, List<Comment> commentList, Mentions mentions) {
        this.commentList = commentList;
        this.previewImage = previewImage;
        this.mentions = mentions;
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
                commentViewHolder.imgLikeNum.setText(new StringBuilder().append(previewImage.likesCount).append(" ").append(context.getResources().getString(R.string.like)).toString());
            if (previewImage.commentsCount != null)
                commentViewHolder.imgCommentNum.setText(new StringBuilder().append(previewImage.commentsCount).append(" ").append(context.getResources().getString(R.string.comment)).toString());
            if (commentAdapterAction != null) {
                commentViewHolder.imageCommentBtn.setOnClickListener(v -> {
                    commentViewHolder.imgCommentNum.requestFocus();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                });


                if (previewImage.isLiked) {
                    commentViewHolder.imageLikeBtn.setImageResource(R.drawable.ic_like_off);
                } else {
                    commentViewHolder.imageLikeBtn.setImageResource(R.drawable.ic_like_on);
                }

                commentViewHolder.imageLikeBtn.setOnClickListener(v -> {

                    commentAdapterAction.onImageLike(previewImage);
                });
            }

        } else if (getItemViewType(i) == COMMENT) {
            if (commentList.get(i).business != null) {

                if (commentList.get(i).business.thumbnail != null)
                    GlideApp.with(context)
                            .load(commentList.get(i).business.thumbnail)
                            .error(R.drawable.default_error_img)
                            .placeholder(R.drawable.default_place_holder)
                            .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                            .apply(RequestOptions.circleCropTransform())
                            .into(commentViewHolder.commentAuthorImg);
                commentViewHolder.commentAuthorName.setText(commentList.get(i).business.firstName + " " + commentList.get(i).business.lastName);


            } else if (commentList.get(i).photographer != null) {
                if (commentList.get(i).photographer.imageProfile != null)
                    GlideApp.with(context)
                            .load(commentList.get(i).photographer.imageProfile)
                            .error(R.drawable.default_error_img)
                            .placeholder(R.drawable.default_place_holder)
                            .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                            .apply(RequestOptions.circleCropTransform())
                            .into(commentViewHolder.commentAuthorImg);
                commentViewHolder.commentAuthorName.setText(commentList.get(i).photographer.fullName);

            }
            handleCommentBody(commentViewHolder, commentList.get(i));
        } else if (getItemViewType(i) == ADD_COMMENT) {
            if (commentAdapterAction != null) {
                commentViewHolder.sendCommentBtn.setOnClickListener(v -> {
                    commentAdapterAction.onSubmitComment(commentViewHolder.sendCommentImgVal.getText().toString());
                    commentViewHolder.sendCommentImgVal.getText().clear();
                });
            }
        }
    }


    private void handleCommentBody(CommentViewHolder commentViewHolder, Comment comment) {


        String commentFinalValue = comment.comment;
        List<String> authorsId = Utilities.getMentionsList(comment.comment);
        List<String> mentionsPhotoGrapherIdIdList = new ArrayList<>();
        List<String> mentionBusinessIdList = new ArrayList<>();


        //

        if (authorsId.size() > 0) {

            for (String authorId : authorsId) {
                String[] singleId = authorId.split("\\_");

                if (singleId[0].equals("0")) {
                    mentionsPhotoGrapherIdIdList.add(singleId[1]);
                } else if (singleId[0].equals("1")) {
                    mentionBusinessIdList.add(singleId[1]);
                }
            }


///////////////////////////////////////////
            for (String photoGrapherId : mentionsPhotoGrapherIdIdList) { ///Replacing All Occurrence of photoGrapherId with actualValue
                Photographer photographer = getMentionedPhotoGrapher(photoGrapherId);

                if (photographer != null) {

                    commentFinalValue = commentFinalValue.replace("@0_" + photoGrapherId, photographer.fullName + "_");
                    commentViewHolder.commentVal.setText(commentFinalValue);
                }
            }
///////////////////////////////////////////


            for (String businessId : mentionBusinessIdList) {///Replacing All Occurrence of businessId with actualValue
                if (getMentionedBusiness(businessId) != null) {
                    Business business = getMentionedBusiness(businessId);
                    if (business != null) {
                        commentFinalValue = commentFinalValue.replace("@1_" + businessId, business.firstName + " " + business.lastName + "_");
                        commentViewHolder.commentVal.setText(commentFinalValue);
                    }
                }
            }

////////////////////////////////////////////////////////////////


            for (String photographerId : mentionsPhotoGrapherIdIdList) {


                if (getMentionedPhotoGrapher(photographerId) != null) {
                    Photographer photographer = getMentionedPhotoGrapher(photographerId);
                    ClickableSpan noUnderLineClickSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View view) {
                            //Action here
                            Toast.makeText(context, photographer.id.toString(), Toast.LENGTH_SHORT)
                                    .show();
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                            ds.setColor(Color.BLUE); // specific color for this link
                        }
                    };


////////////////////////////////////////////////
                    String replacement = photographer.fullName + "_";
                    int replacementStart=commentFinalValue.indexOf(replacement)-1;
                    int replacementEnd=replacementStart+replacement.length();

                    commentFinalValue = commentFinalValue.replace(replacement, replacement.substring(0, replacement.length() - 1));

                    spannableString=new SpannableString(commentFinalValue);

                    makeLinks(commentViewHolder.commentVal, replacement,replacementStart ,replacementEnd,noUnderLineClickSpan, spannableString);




//                    makeLinks(commentViewHolder.commentVal, photographer.fullName + "_", noUnderLineClickSpan );
                }
            }


            //////////////////////////////////////////////////////////

            for (String businessId : mentionBusinessIdList) {
                if (getMentionedBusiness(businessId) != null) {
                    Business business = getMentionedBusiness(businessId);
                    ClickableSpan noUnderLineClickSpan2 = new ClickableSpan() {
                        @Override
                        public void onClick(View view) {
                            //Action here
                            Toast.makeText(context, business.id.toString(), Toast.LENGTH_SHORT)
                                    .show();
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setUnderlineText(false);
                            ds.setColor(Color.MAGENTA); // specific color for this link
                        }
                    };



                    String replacement = business.firstName + " " + business.lastName + "_";
                    int replacementStart=commentFinalValue.indexOf(replacement)-1;
                    int replacementEnd=replacementStart+replacement.length();

//                    commentFinalValue = commentFinalValue.replace(replacement, replacement.substring(0, replacement.length() - 1));
//
//                    spannableString=new SpannableString(commentFinalValue);
//
//                    makeLinks(commentViewHolder.commentVal, replacement,replacementStart ,replacementEnd,noUnderLineClickSpan2, spannableString);


//                    String target = business.firstName + " " + business.lastName + "_";
//                    commentFinalValue = commentFinalValue.replace(target, target.substring(0, target.length() - 1));
//                    makeLinks(commentViewHolder.commentVal, target, noUnderLineClickSpan2, spannableString);


//                    makeLinks(commentViewHolder.commentVal, new String[]{
//                            getMentionedBusiness(businessId).firstName + " " + getMentionedBusiness(businessId).lastName + "_"
//                    }, new ClickableSpan[]{
//                            noUnderLineClickSpan2
//                    });


//                    makeLinks(commentViewHolder.commentVal,
//
//                            business.firstName + " " + business.lastName + "_"
//                    ,
//                            noUnderLineClickSpan2
//                     );
//

                }


            }
        } else {
            commentViewHolder.commentVal.setText(commentFinalValue);
        }
    }


    /**
     * @param viewHolder      --->view holder contain comment value
     * @param replacement         --->replacment user_value will be replaced (?*_)
     * @param clickableSpan   --->link CallBack
     * @param commentFinalValue --->all text value insideViewHolder
     */
    private void makeLinks(TextView viewHolder, String replacement, List<HashMap<Integer,Integer>> photoGrapherRangeReplacement, int endIndexOfLink, ClickableSpan clickableSpan, String commentFinalValue) {




        SpannableString spannableString = new SpannableString(commentFinalValue);



        spannableString.setSpan(clickableSpan, startIndexOfLink + 1, endIndexOfLink, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);





        viewHolder.setText(spannableString);


//        int startIndexOfLink = viewHolder.getText().toString().indexOf(replacment);
//        int endIndexOfLink = startIndexOfLink + replacment.length() - 2;
//        // set replacment Span at
//        spannableString.setSpan(clickableSpan, startIndexOfLink + 1, endIndexOfLink, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//
//        viewHolder.setHighlightColor(Color.TRANSPARENT); // prevent TextView change background when highlight
//        viewHolder.setMovementMethod(LinkMovementMethod.getInstance());
//        viewHolder.setText(spannableString, TextView.BufferType.SPANNABLE);
    }


    /**
     * use this method to Span multiple Links in one row
     */
    private void makeLinks(TextView textView, String[] links, ClickableSpan[] clickableSpans) {
        SpannableString spannableString = new SpannableString(textView.getText());
        for (int i = 0; i < links.length; i++) {
            ClickableSpan clickableSpan = clickableSpans[i];

            String link = links[i];
            int startIndexOfLink = textView.getText().toString().indexOf(link);
            spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setHighlightColor(
                Color.TRANSPARENT); // prevent TextView change background when highlight
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

    private Photographer getMentionedPhotoGrapher(String userId) {

        if (mentions.photographers !=null)
        for (Photographer photographer : mentions.photographers) {
            if (photographer.id == Integer.parseInt(userId)) {
                return photographer;
            }
        }

        return null;
    }

    private Business getMentionedBusiness(String userId) {

        if (mentions.business !=null)
        for (Business business : mentions.business) {
            if (business.id == Integer.parseInt(userId)) {
                return business;
            }
        }
        return null;
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

        CustomTextView imgLikeNum, imgCommentNum, commentPreviewImgTags;
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

                imgLikeNum = view.findViewById(R.id.comment_preview_img_like_num);
                imgCommentNum = view.findViewById(R.id.comment_preview_img_comment_num);
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
