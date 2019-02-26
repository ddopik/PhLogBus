package com.example.ddopik.phlogbusiness.ui.commentimage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Comment;
import com.example.ddopik.phlogbusiness.base.commonmodel.MentionedUser;
import com.example.ddopik.phlogbusiness.base.commonmodel.Mentions;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.CommentsAdapter;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ImageCommentsData;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.SubmitImageCommentData;
import com.example.ddopik.phlogbusiness.ui.commentimage.presenter.ReplayCommentPresenter;
import com.example.ddopik.phlogbusiness.ui.commentimage.presenter.ReplayCommentPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReplayCommentActivity extends BaseActivity implements ReplayCommentActivityView {

    public static String COMMENT_ID = "comment_id";
    private CustomRecyclerView repliesRv;
    private ProgressBar repliesProgressBar;
    private BaseImage previewImage;
    private List<Comment> commentList = new ArrayList<>();
    private Mentions mentions = new Mentions();
    private CommentsAdapter commentsAdapter;
    private MentionsAutoCompleteAdapter mentionsAutoCompleteAdapter;
    private List<MentionedUser> mentionedUserList = new ArrayList<>();
    private DisposableObserver<TextViewTextChangeEvent> searchQuery;
    private CompositeDisposable disposable = new CompositeDisposable();

    private PagingController pagingController;
    private ReplayCommentPresenter replayCommentPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_replay);
        initPresenter();
        initView();
        initListener();
        replayCommentPresenter.getReplies(370, 216, 0);
        if (getIntent().getIntExtra(COMMENT_ID, 0) > 0) {
//            replayCommentPresenter.getReplies(getIntent().getIntExtra(COMMENT_ID, 0), previewImage, 0);

        }

    }

    @Override
    public void initView() {
        repliesRv = findViewById(R.id.comment_replay_rv);
        repliesProgressBar = findViewById(R.id.comment_replay_progress);
        commentsAdapter = new CommentsAdapter(previewImage, commentList, mentions,Constants.CommnetListType.REPLIES_LIST);
        repliesRv.setAdapter(commentsAdapter);


        ////////
        mentionsAutoCompleteAdapter = new MentionsAutoCompleteAdapter(getBaseContext(), R.layout.view_holder_mentioned_user, mentionedUserList);
        mentionsAutoCompleteAdapter.setNotifyOnChange(true);
        commentViewHolder.sendCommentImgVal.setAdapter(mentionsAutoCompleteAdapter);
        commentViewHolder.sendCommentImgVal.setThreshold(0);


        commentViewHolder.sendCommentImgVal.setOnItemClickListener((parent, view, position, id) -> {
            commentViewHolder.sendCommentImgVal.handleMentionedCommentBody(position, mentionedUserList);

        });


        if (searchQuery == null) {
            searchQuery = getSearchTagQuery(commentViewHolder.sendCommentImgVal);
            commentViewHolder.sendCommentImgVal.addTextChangedListener(new TextWatcher() {
                int cursorPosition = commentViewHolder.sendCommentImgVal.getSelectionStart();

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    int mentionIdentifierCharPosition = commentViewHolder.sendCommentImgVal.getText().toString().indexOf("@", cursorPosition - 2);
                    if ((mentionIdentifierCharPosition + 1) >= commentViewHolder.sendCommentImgVal.getText().toString().length() || mentionIdentifierCharPosition == -1) {
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

                    RxTextView.textChangeEvents(commentViewHolder.sendCommentImgVal)
                            .skipInitialValue()
                            .debounce(900, TimeUnit.MILLISECONDS)
                            .distinctUntilChanged()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(searchQuery)
            );
        }

        if (commentAdapterAction != null) {
            commentViewHolder.sendCommentBtn.setOnClickListener(v -> {
                String comment = commentViewHolder.sendCommentImgVal.prepareCommentToSend();
                commentAdapterAction.onSubmitComment(comment);
                commentViewHolder.sendCommentImgVal.getText().clear();
            });
        }


        mentionsAutoCompleteAdapter.onUserClicked = socialUser -> {
        };



    }

    @Override
    public void initPresenter() {
        replayCommentPresenter = new ReplayCommentPresenterImpl(getBaseContext(), this);
    }

    private void initListener() {

        pagingController = new PagingController(repliesRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
//                replayCommentPresenter.getReplies(getIntent().getIntExtra(COMMENT_ID, 0), previewImage, page);
                replayCommentPresenter.getReplies(370, 216, page);
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
//                    replayCommentPresenter.submitReplayComment(String.valueOf(previewImage.id), papapa, comment);
                    replayCommentPresenter.submitReplayComment("216", "370", comment);

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
            public void onReplayClicked(int commentID) {

            }

            @Override
            public void onChooseWinnerClick(BaseImage previewImage, Consumer<Boolean> success) {

            }
        };
    }


    @Override
    public void onCommentReplied(SubmitImageCommentData submitImageCommentData) {

    }

    @Override
    public void viewReplies(ImageCommentsData imageCommentsData) {
        this.commentList.addAll(imageCommentsData.comments.commentList);
        if (imageCommentsData.mentions.business != null)
            this.mentions.business.addAll(imageCommentsData.mentions.business);
        if (imageCommentsData.mentions.photographers != null)
            this.mentions.photographers.addAll(imageCommentsData.mentions.photographers);

        commentsAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void viewRepliesProgress(boolean state) {

    }
}
