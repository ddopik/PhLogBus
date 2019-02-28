package com.example.ddopik.phlogbusiness.ui.commentimage.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.*;
import com.example.ddopik.phlogbusiness.base.widgets.CustomAutoCompleteTextView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.CommentsAdapter;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ImageCommentsData;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.SubmitImageCommentData;
import com.example.ddopik.phlogbusiness.ui.commentimage.presenter.ReplayCommentPresenter;
import com.example.ddopik.phlogbusiness.ui.commentimage.presenter.ReplayCommentPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.example.ddopik.phlogbusiness.utiltes.Constants.CommentListType.VIEW_REPLIES;

public class ReplayCommentActivity extends BaseActivity implements ReplayCommentActivityView {

    private String TAG = ReplayCommentActivity.class.getSimpleName();
    public static String COMMENT_IMAGE = "comment_image";
    public static String COMMENT_LIST_TYPE = "comment_list_type";
    protected static String REPLY_HEADER_COMMNET = "replay_header_comment";
    private Constants.CommentListType commentListType;
    private CustomRecyclerView repliesRv;
    private ProgressBar repliesProgressBar;
    private CustomTextView toolBarTitle;
    private ImageButton backBtn;
    private BaseImage previewImage;
    private Comment headerComment;
    private List<Comment> commentList = new ArrayList<>();
    private Mentions mentions = new Mentions();
    private CommentsAdapter commentsAdapter;
    private PagingController pagingController;
    private ReplayCommentPresenter replayCommentPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_replay);
        initPresenter();
        initView();
        initListener();

        if (getIntent().getParcelableExtra(REPLY_HEADER_COMMNET) != null && getIntent().getParcelableExtra(COMMENT_IMAGE) != null) {
            previewImage = getIntent().getParcelableExtra(COMMENT_IMAGE);
            headerComment = (Comment) getIntent().getParcelableExtra(REPLY_HEADER_COMMNET);
            commentListType = (Constants.CommentListType) getIntent().getSerializableExtra(COMMENT_LIST_TYPE);
            Comment comment=new Comment();

            commentList.add(headerComment);
            commentList.add(comment);
            commentsAdapter.notifyDataSetChanged();
            replayCommentPresenter.getReplies(headerComment.id, previewImage.id, 0);
        }



    }

    @Override
    protected void onNewIntent(Intent intent) {


        if (intent.getParcelableExtra(REPLY_HEADER_COMMNET) != null && getIntent().getParcelableExtra(COMMENT_IMAGE) != null) {
            previewImage = intent.getParcelableExtra(COMMENT_IMAGE);
            headerComment = intent.getParcelableExtra(REPLY_HEADER_COMMNET);
            commentListType = (Constants.CommentListType) intent.getSerializableExtra(COMMENT_LIST_TYPE);
            commentList.clear();
            Comment comment=new Comment();
             commentList.add(headerComment);
            commentList.add(comment);
            commentsAdapter.notifyDataSetChanged();
            replayCommentPresenter.getReplies(headerComment.id, previewImage.id, 0);

        }
    }

    @Override
    public void initView() {
        toolBarTitle = findViewById(R.id.toolbar_title);
        toolBarTitle.setText(getResources().getString(R.string.replies));
        backBtn = findViewById(R.id.back_btn);
        repliesProgressBar = findViewById(R.id.comment_replay_progress);
        repliesRv = findViewById(R.id.comment_replay_rv);
        commentsAdapter = new CommentsAdapter(previewImage, commentList, mentions, VIEW_REPLIES);
        repliesRv.setAdapter(commentsAdapter);


    }

    @Override
    public void initPresenter() {
        replayCommentPresenter = new ReplayCommentPresenterImpl(getBaseContext(), this);
    }

    private void initListener() {

        pagingController = new PagingController(repliesRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                replayCommentPresenter.getReplies(headerComment.id, previewImage.id, page);
            }
        };


        commentsAdapter.commentAdapterAction = new CommentsAdapter.CommentAdapterAction() {

            @Override
            public void onCommentAuthorIconClicked(BaseImage baseImage) {
                if (PrefUtils.getUserId(getBaseContext()).equals(String.valueOf(baseImage.photographer.id))) {
                    Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
                    intent.putExtra(UserProfileActivity.USER_ID, String.valueOf(baseImage.photographer.id));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    getBaseContext().startActivity(intent);
                }
            }

            @Override
            public void onImageLike(BaseImage baseImage) {

            }

            @Override
            public void onSubmitComment(String comment) {

                if (comment.length() > 0) {
                    replayCommentPresenter.submitReplayComment(String.valueOf(previewImage.id), String.valueOf(headerComment.id), comment);
                } else {
                    showToast(getResources().getString(R.string.comment_cant_not_be_null));
                }
            }

            @Override
            public void onAddToLightBox(BaseImage baseImage) {

            }

            @Override
            public void onAddToCartClick(BaseImage baseImage) {


            }

            @Override
            public void onImageRateClick(BaseImage baseImage, float rating) {


            }

            @Override
            public void onImageCommentClicked() {

            }

            @Override
            public void onReportClicked(BaseImage image) {


            }

            @Override
            public void onReplayClicked(Comment comment, Constants.CommentListType commentListType) {
                Intent intent = new Intent(getBaseContext(), ReplayCommentActivity.class);
                intent.putExtra(ReplayCommentActivity.COMMENT_IMAGE, previewImage);
                intent.putExtra(ReplayCommentActivity.COMMENT_LIST_TYPE, commentListType);
                intent.putExtra(ReplayCommentActivity.REPLY_HEADER_COMMNET, comment);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

            @Override
            public void onChooseWinnerClick(BaseImage previewImage, Consumer<Boolean> success) {

            }
        };
        backBtn.setOnClickListener(v -> {
            onBackPressed();

        });
    }


    @SuppressLint("CheckResult")
    @Override
    public void onCommentReplied(SubmitImageCommentData submitImageCommentData) {
        commentList.add(submitImageCommentData.comment);


        reSortMentionList(submitImageCommentData.mentions).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mentions -> {
                    this.mentions.business.addAll(mentions.business);
                    this.mentions.photographers.addAll(mentions.photographers);
                    commentsAdapter.notifyDataSetChanged();
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(this, TAG, throwable);
                });


        Utilities.hideKeyboard(this);
    }

    @Override
    public void viewReplies(ImageCommentsData imageCommentsData) {


//        if (newReplay) {
//            this.commentList.clear();
//            Comment userComment = new Comment();
//            commentList.add(userComment);/// acts As default for image Add comment
//        }
        this.commentList.addAll(commentList.size()-1, imageCommentsData.comments.commentList);

        if (imageCommentsData.mentions.business != null)
            this.mentions.business.addAll(imageCommentsData.mentions.business);
        if (imageCommentsData.mentions.photographers != null)
            this.mentions.photographers.addAll(imageCommentsData.mentions.photographers);


        commentsAdapter.notifyDataSetChanged();


        if (commentListType == Constants.CommentListType.REPLAY_ON_COMMENT) {
            if (commentList.size() == 1) {
                repliesRv.scrollToPosition(commentList.size() - 1);
                CustomAutoCompleteTextView customAutoCompleteTextView = (CustomAutoCompleteTextView) repliesRv.getChildAt(commentList.size() - 1).findViewById(R.id.img_send_comment_val);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(customAutoCompleteTextView, InputMethodManager.SHOW_IMPLICIT);
                customAutoCompleteTextView.requestFocus();
            } else {
                repliesRv.getLayoutManager().smoothScrollToPosition(repliesRv, null, commentList.size() - 1);
                RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        switch (newState) {
                            case SCROLL_STATE_IDLE:

                                //we reached the target position
                                if (repliesRv.getChildAt(repliesRv.getChildCount() - 1).findViewById(R.id.img_send_comment_val) != null) {
                                    CustomAutoCompleteTextView customAutoCompleteTextView = repliesRv.getChildAt(repliesRv.getChildCount() - 1).findViewById(R.id.img_send_comment_val);
                                    customAutoCompleteTextView.requestFocus();
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(customAutoCompleteTextView, InputMethodManager.SHOW_IMPLICIT);
                                    recyclerView.removeOnScrollListener(this);
                                }

                                break;
                        }
                    }
                };

                repliesRv.addOnScrollListener(onScrollListener);


            }

        }
    }

    @Override
    public void viewMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void viewRepliesProgress(boolean state) {

        if (state) {
            repliesProgressBar.setVisibility(View.VISIBLE);
        } else {
            repliesProgressBar.setVisibility(View.GONE);
        }

    }


    private Observable<Mentions> reSortMentionList(Mentions mentionsNew) {

        Mentions mentions = new Mentions();
        this.mentions.photographers.addAll(mentionsNew.photographers);
        this.mentions.business.addAll(mentionsNew.business);
        return Observable.fromCallable(() -> {


            for (Photographer newPhotoGrapher : mentionsNew.photographers) {

                for (int i = 0; i < this.mentions.photographers.size(); i++) {
                    if (mentions.photographers.get(i).id.equals(newPhotoGrapher.id)) {
                        break;
                    }
                    if (i == this.mentions.photographers.size() - 1) {
                        this.mentions.photographers.add(newPhotoGrapher);
                    }
                }


            }

            for (Business newBusiness : mentionsNew.business) {

                for (int i = 0; i < this.mentions.business.size(); i++) {
                    if (mentions.business.get(i).id.equals(newBusiness.id)) {
                        break;
                    }
                    if (i == this.mentions.business.size() - 1) {
                        this.mentions.business.add(newBusiness);
                    }
                }


            }
            return mentions;
        });

    }
}
