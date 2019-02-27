package com.example.ddopik.phlogbusiness.ui.commentimage.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.*;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
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

public class ReplayCommentActivity extends BaseActivity implements ReplayCommentActivityView {

    private String TAG = ReplayCommentActivity.class.getSimpleName();
    public static String COMMENT_ID = "comment_id";
    public static String COMMENT_IMAGE = "commnet_image";
    private CustomRecyclerView repliesRv;
    private ProgressBar repliesProgressBar;
    private BaseImage previewImage;
    private int commentId;
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


        if (getIntent().getIntExtra(COMMENT_ID, 0) > 0 && getIntent().getParcelableExtra(COMMENT_IMAGE) != null) {
            previewImage = getIntent().getParcelableExtra(COMMENT_IMAGE);
            commentId = getIntent().getIntExtra(COMMENT_ID, 0);
            replayCommentPresenter.getReplies(getIntent().getIntExtra(COMMENT_ID, 0), previewImage.id, 0);

        }

    }

    @Override
    public void initView() {
         repliesProgressBar = findViewById(R.id.comment_replay_progress);
        repliesRv = findViewById(R.id.comment_replay_rv);

        Comment userComment = new Comment();
        commentList.add(userComment);/// acts As default for image Add comment
        commentsAdapter = new CommentsAdapter(previewImage, commentList, mentions, Constants.CommnetListType.REPLIES_LIST);
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
                replayCommentPresenter.getReplies(getIntent().getIntExtra(COMMENT_ID, 0), previewImage.id, page);
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
            public void onSubmitCommnet(String comment) {

                if (comment.length() > 0) {
                    replayCommentPresenter.submitReplayComment(String.valueOf(previewImage.id), String.valueOf(commentId), comment);
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


        this.commentList.addAll(this.commentList.size()-1,imageCommentsData.comments.commentList);

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
