package com.example.ddopik.phlogbusiness.ui.commentimage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Comment;
import com.example.ddopik.phlogbusiness.base.commonmodel.MentionedUser;
import com.example.ddopik.phlogbusiness.base.commonmodel.Mentions;
import com.example.ddopik.phlogbusiness.base.widgets.CustomAutoCompleteTextView;
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
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
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

    private String TAG = ReplayCommentActivity.class.getSimpleName();
    public static String COMMENT_ID = "comment_id";
    public static String COMMENT_IMAGE = "commnet_image";
    private CustomRecyclerView repliesRv;
    private CustomAutoCompleteTextView sendCommentImgVal;
    private ImageButton sendCommentBtn;
    private ProgressBar repliesProgressBar;
    private BaseImage previewImage;
    private int commentId;
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


        if (getIntent().getIntExtra(COMMENT_ID, 0) > 0 && getIntent().getParcelableExtra(COMMENT_IMAGE) != null) {
            previewImage = getIntent().getParcelableExtra(COMMENT_IMAGE);
            commentId = getIntent().getIntExtra(COMMENT_ID, 0);
            replayCommentPresenter.getReplies(getIntent().getIntExtra(COMMENT_ID, 0), previewImage.id, 0);

        }

    }

    @Override
    public void initView() {
        repliesRv = findViewById(R.id.comment_replay_rv);
        sendCommentImgVal = findViewById(R.id.img_send_comment_val);
        repliesProgressBar = findViewById(R.id.comment_replay_progress);
        sendCommentBtn = findViewById(R.id.send_comment_btn);
        commentsAdapter = new CommentsAdapter(previewImage, commentList, mentions, Constants.CommnetListType.REPLIES_LIST);
        repliesRv.setAdapter(commentsAdapter);


        ////////
        mentionsAutoCompleteAdapter = new MentionsAutoCompleteAdapter(getBaseContext(), R.layout.view_holder_mentioned_user, mentionedUserList);
        mentionsAutoCompleteAdapter.setNotifyOnChange(true);
        sendCommentImgVal.setAdapter(mentionsAutoCompleteAdapter);
        sendCommentImgVal.setThreshold(0);


    }

    @Override
    public void initPresenter() {
        replayCommentPresenter = new ReplayCommentPresenterImpl(getBaseContext(), this);
    }

    private void initListener() {


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

            if (sendCommentImgVal.length() > 0) {
                String comment = sendCommentImgVal.prepareCommentToSend();
                replayCommentPresenter.submitReplayComment(String.valueOf(previewImage.id), String.valueOf(commentId), comment);
            } else {
                showToast(getResources().getString(R.string.comment_cant_not_be_null));
            }
        });


        mentionsAutoCompleteAdapter.onUserClicked = socialUser -> {
        };


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
        Utilities.hideKeyboard(this);
        sendCommentImgVal.getText().clear();
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
    public void viewReplies(ImageCommentsData imageCommentsData) {
        this.commentList.addAll(imageCommentsData.comments.commentList);
        if (imageCommentsData.mentions.business != null)
            this.mentions.business.addAll(imageCommentsData.mentions.business);
        if (imageCommentsData.mentions.photographers != null)
            this.mentions.photographers.addAll(imageCommentsData.mentions.photographers);

        commentsAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewMentionedUsers(List<MentionedUser> mentionedUserList) {
        this.mentionedUserList.clear();
        this.mentionedUserList.addAll(mentionedUserList);
        mentionsAutoCompleteAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void viewRepliesProgress(boolean state) {

    }
}
