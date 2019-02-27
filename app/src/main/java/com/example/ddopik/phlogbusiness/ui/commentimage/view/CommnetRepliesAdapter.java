package com.example.ddopik.phlogbusiness.ui.commentimage.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Comment;
import com.example.ddopik.phlogbusiness.base.commonmodel.MentionedUser;
import com.example.ddopik.phlogbusiness.base.commonmodel.Mentions;
import com.example.ddopik.phlogbusiness.base.widgets.CustomAutoCompleteTextView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.ui.album.presenter.CommentAdapterPresenter;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.CommentsAdapter;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

import java.util.ArrayList;
import java.util.List;

public class CommnetRepliesAdapter extends RecyclerView.Adapter<CommnetRepliesAdapter.RepliesViewHolder> {

    private String TAG = CommnetRepliesAdapter.class.getSimpleName();
    private Context context;
    private BaseImage previewImage;
    private List<Comment> commentList;
    private Mentions mentions;
    private LayoutInflater layoutInflater;
    private List<MentionedUser> mentionedUserList = new ArrayList<>();
    private MentionsAutoCompleteAdapter mentionsAutoCompleteAdapter;
    private CommentAdapterPresenter commentAdapterPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();


    public CommentsAdapter.CommentAdapterAction commentAdapterAction;

    private int COMMENT = 1;
    private int ADD_COMMENT = 2;
    public int REPLAY_POSITION = -1;
    private final String USER_MENTION_IDENTIFIER = "%";
    private DisposableObserver<TextViewTextChangeEvent> searchQuery;

    public CommnetRepliesAdapter(BaseImage previewImage, List<Comment> commentList, Mentions mentions, LayoutInflater layoutInflater) {
        this.commentList = commentList;
        this.previewImage = previewImage;
        this.mentions = mentions;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public RepliesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);


        if (i == commentList.size()) {
            return new RepliesViewHolder(layoutInflater.inflate(R.layout.view_holder_image_send_comment, viewGroup, false), ADD_COMMENT);
        } else {
            return new RepliesViewHolder(layoutInflater.inflate(R.layout.view_holder_image_comment, viewGroup, false), COMMENT);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RepliesViewHolder repliesViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == (commentList.size() - 1)) {
            return ADD_COMMENT; //---> normal Comment viewHolder
        } else {
            return COMMENT; //--->  Comment Cell
        }
    }

    class RepliesViewHolder extends RecyclerView.ViewHolder {
        ///Comment_value Cell
        CardView parentCommentView;
        CustomTextView commentVal, commentAuthorName, imageCommentReplayBtn;
        ImageView commentAuthorImg;
        //SendCommentCell
        CustomAutoCompleteTextView sendCommentImgVal;
        ImageButton sendCommentBtn;

        public RepliesViewHolder(View view, int type) {
            super(view);
            if (type == COMMENT) {
                parentCommentView = view.findViewById(R.id.comment_parent_view);
                commentVal = view.findViewById(R.id.comment_val);
                commentAuthorImg = view.findViewById(R.id.commentAuthorImg);
                commentAuthorName = view.findViewById(R.id.comment_author);
                imageCommentReplayBtn = view.findViewById(R.id.image_comment_replay_btn);
            } else if (type == ADD_COMMENT) {
                sendCommentImgVal = view.findViewById(R.id.img_send_comment_val);
                sendCommentBtn = view.findViewById(R.id.send_comment_btn);
            }

        }
    }
}
