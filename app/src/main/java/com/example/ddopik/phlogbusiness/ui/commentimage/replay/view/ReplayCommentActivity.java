package com.example.ddopik.phlogbusiness.ui.commentimage.replay.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.*;
import com.example.ddopik.phlogbusiness.base.widgets.CustomAutoCompleteTextView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.base.PagingController;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.CommentsAdapter;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ImageCommentsData;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.SubmitImageCommentData;
import com.example.ddopik.phlogbusiness.ui.commentimage.replay.presenter.ReplayCommentPresenter;
import com.example.ddopik.phlogbusiness.ui.commentimage.replay.presenter.ReplayCommentPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.ImageCommentActivity;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.ImageCommentActivityView;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.MentionsAutoCompleteAdapter;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.example.ddopik.phlogbusiness.utiltes.Constants.CommentListType.VIEW_REPLIES;

/**
 * Created by abdalla_maged on 11/6/2018.
 */

public class ReplayCommentActivity extends BaseActivity implements ReplayCommentActivityView {

    public static final String MENTIONS = "mention";
    private String TAG = ReplayCommentActivity.class.getSimpleName();
    public static String COMMENT_IMAGE = "comment_image";
    public static String COMMENT_LIST_TYPE = "comment_list_type";
    public static String REPLY_HEADER_COMMENT = "replay_header_comment";
    private Constants.CommentListType commentListType;
    private CustomRecyclerView repliesRv;
    private ProgressBar repliesProgressBar;
    private CustomTextView toolBarTitle;
    private ImageButton backBtn;
    private BaseImage previewImage;
    private  Comment headerComment;
    private List<Comment> commentList = new ArrayList<>();
    private Mentions mentions = new Mentions();
    private CommentsAdapter commentsAdapter;
    private PagingController pagingController;
    private String nextPageUrl = "1";
    private boolean isLoading;
    private ReplayCommentPresenter replayCommentPresenter;
    //SendCommentCell
    private CustomAutoCompleteTextView sendCommentImgVal;
    private ImageButton sendCommentBtn;
    private MentionsAutoCompleteAdapter mentionsAutoCompleteAdapter;
    private List<MentionedUser> mentionedUserList = new ArrayList<>();
    private DisposableObserver<TextViewTextChangeEvent> searchQuery;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_replay);


        if (getIntent().getParcelableExtra(REPLY_HEADER_COMMENT) != null && getIntent().getParcelableExtra(COMMENT_IMAGE) != null) {
            previewImage = getIntent().getParcelableExtra(COMMENT_IMAGE);
            mentions = getIntent().getParcelableExtra(MENTIONS);
            headerComment = (Comment) getIntent().getParcelableExtra(REPLY_HEADER_COMMENT);
            commentListType = (Constants.CommentListType) getIntent().getSerializableExtra(COMMENT_LIST_TYPE);


            initPresenter();
            initView();
            initListener();

            commentList.add(headerComment);


            commentsAdapter.notifyDataSetChanged();
            replayCommentPresenter.getReplies(headerComment.id, previewImage.id, 0);


        }


    }



    @Override
    public void initView() {
        toolBarTitle = findViewById(R.id.toolbar_title);
        sendCommentImgVal = findViewById(R.id.img_send_comment_val);
        sendCommentBtn = findViewById(R.id.send_comment_btn);
        toolBarTitle.setText(getResources().getString(R.string.replies));
        backBtn = findViewById(R.id.back_btn);
        repliesProgressBar = findViewById(R.id.comment_replay_progress);
        repliesRv = findViewById(R.id.comment_replay_rv);
        commentsAdapter = new CommentsAdapter(previewImage, commentList, mentions, VIEW_REPLIES);
        repliesRv.setAdapter(commentsAdapter);

        setReplyCommentView();
    }

    @Override
    public void initPresenter() {
        replayCommentPresenter = new ReplayCommentPresenterImpl(getBaseContext(), this);
    }

    private void initListener() {


        pagingController = new PagingController(repliesRv) {
            @Override
            protected void loadMoreItems() {
                replayCommentPresenter.getReplies(headerComment.id, previewImage.id, Integer.parseInt(nextPageUrl));
            }

            @Override
            public boolean isLastPage() {

                if (nextPageUrl == null) {
                    return true;
                } else {
                    return false;
                }

            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

        };
        commentsAdapter.commentAdapterAction = new CommentsAdapter.CommentAdapterAction() {

            @Override
            public void onCommentAuthorIconClicked(BaseImage baseImage) {
                if (PrefUtils.getBrandId(getBaseContext()).equals(String.valueOf(baseImage.photographer.id))) {
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
            public void onAddToLightBox(BaseImage baseImage) {

            }

            @Override
            public void onAddToCartClick(BaseImage baseImage) {


            }

            @Override
            public void onImageRateClick(BaseImage baseImage) {


            }

            @Override
            public void onImageCommentClicked() {

            }


            @Override
            public void onReplayClicked(Comment comment, Mentions mentions, Constants.CommentListType commentListType) {
                Intent intent = new Intent(getBaseContext(), ReplayCommentActivity.class);
                intent.putExtra(ReplayCommentActivity.COMMENT_IMAGE, previewImage);
                intent.putExtra(ReplayCommentActivity.COMMENT_LIST_TYPE, commentListType);
                intent.putExtra(ReplayCommentActivity.REPLY_HEADER_COMMENT, comment);
                intent.putExtra(ReplayCommentActivity.MENTIONS, mentions);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, ImageCommentActivity.REPLY_REQUEST_CODE);
            }
            @Override
            public void onReportClicked(BaseImage image) {

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
        headerComment.repliesCount++;
        previewImage.commentsCount++;
        Intent data = new Intent();
        data.putExtra(REPLY_HEADER_COMMENT, headerComment);
        data.putExtra(COMMENT_IMAGE, previewImage);
        setResult(RESULT_OK, data);
        commentList.add(commentList.size() , submitImageCommentData.comment);

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


        Collections.reverse(imageCommentsData.comments.commentList);
        this.commentList.addAll(commentList.size() , imageCommentsData.comments.commentList);

        if (imageCommentsData.mentions.business != null)
            this.mentions.business.addAll(imageCommentsData.mentions.business);
        if (imageCommentsData.mentions.photographers != null)
            this.mentions.photographers.addAll(imageCommentsData.mentions.photographers);


        commentsAdapter.notifyDataSetChanged();


        if (commentListType == Constants.CommentListType.REPLAY_ON_COMMENT) {
            if (commentList.size() == 1) {
                repliesRv.scrollToPosition(commentList.size() );
                CustomAutoCompleteTextView customAutoCompleteTextView = (CustomAutoCompleteTextView) repliesRv.getChildAt(commentList.size() - 1).findViewById(R.id.img_send_comment_val);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(customAutoCompleteTextView, InputMethodManager.SHOW_IMPLICIT);
                customAutoCompleteTextView.requestFocus();
            } else {
                repliesRv.getLayoutManager().smoothScrollToPosition(repliesRv, null, commentList.size() );
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
        isLoading = state;
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

    @Override
    public void setNextPageUrl(String page) {
        this.nextPageUrl = page;
    }

    private void setReplyCommentView() {
        mentionsAutoCompleteAdapter = new MentionsAutoCompleteAdapter(getBaseContext(), R.layout.view_holder_mentioned_user, mentionedUserList);
        mentionsAutoCompleteAdapter.setNotifyOnChange(true);
        sendCommentImgVal.setAdapter(mentionsAutoCompleteAdapter);



        sendCommentImgVal.setOnItemClickListener((parent, view, position, id) -> {
            sendCommentImgVal.handleMentionedCommentBody(position, mentionedUserList);

        });


        if (searchQuery == null) {
            searchQuery = getSearchTagQuery(sendCommentImgVal);
            sendCommentImgVal.addTextChangedListener(new TextWatcher() {
                int cursorPosition = sendCommentImgVal.getSelectionStart();

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    int mentionIdentifierCharPosition = sendCommentImgVal.getText().toString().indexOf("@", cursorPosition - 2);
                    if ((mentionIdentifierCharPosition + 1) >= sendCommentImgVal.getText().toString().length() || mentionIdentifierCharPosition == -1) {
                        mentionedUserList.clear();
                        mentionsAutoCompleteAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            disposable.add(

                    RxTextView.textChangeEvents(sendCommentImgVal)
                            .skipInitialValue()
                            .debounce(900, TimeUnit.MILLISECONDS)
                            .distinctUntilChanged()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(searchQuery)
            );
        }


        sendCommentBtn.setOnClickListener(v -> {
            String comment = sendCommentImgVal.prepareCommentToSend();
            if (comment.length() > 0) {
                replayCommentPresenter.submitReplayComment(String.valueOf(previewImage.id),String.valueOf(headerComment.id), comment);

            } else {
                showToast(getResources().getString(R.string.comment_cant_not_be_null));
            }

            sendCommentImgVal.getText().clear();

        });


        mentionsAutoCompleteAdapter.onUserClicked = socialUser -> {
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> getSearchTagQuery(AutoCompleteTextView autoCompleteTextView) {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                // user cleared search get default
                Log.e(TAG, "search string: " + autoCompleteTextView.getText().toString().trim());
                int cursorPosition = autoCompleteTextView.getSelectionStart();

                ///getting String value before cursor
                if (cursorPosition > 0) {
                    int searKeyPosition = autoCompleteTextView.getText().toString().lastIndexOf("@", cursorPosition);
                    if (searKeyPosition >= 0) {
                        replayCommentPresenter.getMentionedUser(autoCompleteTextView.getText().toString().substring(searKeyPosition + 1, autoCompleteTextView.getSelectionStart()));
                    }

                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void viewMentionedUsers(List<MentionedUser> mentionedUserList) {
        this.mentionedUserList.clear();
        this.mentionedUserList.addAll(mentionedUserList);
        mentionsAutoCompleteAdapter.notifyDataSetChanged();
    }

}
