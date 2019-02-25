package com.example.ddopik.phlogbusiness.ui.commentimage.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.*;
import com.example.ddopik.phlogbusiness.base.widgets.CustomAutoCompleteTextView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.view.AddToLightBoxDialogFragment;
import com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.CommentsAdapter;
import com.example.ddopik.phlogbusiness.ui.cart.view.CartActivity;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ImageCommentsData;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ReportReason;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.SubmitImageCommentData;
import com.example.ddopik.phlogbusiness.ui.commentimage.presenter.ImageCommentActivityImpl;
import com.example.ddopik.phlogbusiness.ui.commentimage.presenter.ImageCommentActivityPresenter;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class ImageCommentActivity extends BaseActivity implements ImageCommentActivityView {

    public static String IMAGE_DATA = "image_data";
    public static String SHOULD_SHOW_CHOOSE_WINNER = "choose_winner";
    public static String CAMPAIGN_ID = "campaign_id";
    public static final int ImageComment_REQUEST_CODE = 1396;
    private CustomTextView toolBarTitle;
    private ImageButton backBtn;
    private BaseImage previewImage;
    private boolean shouldShowChooseWinnerButton;
    private int campaignId;
    private List<Photographer> photographerList = new ArrayList<Photographer>();
    private List<Business> businessList = new ArrayList<Business>();

    private FrameLayout addCommentProgress;
    private CustomRecyclerView commentsRv;
    private List<Comment> commentList = new ArrayList<>();
    private Mentions mentions = new Mentions();
    private CommentsAdapter commentsAdapter;
    private PagingController pagingController;
    private ImageCommentActivityPresenter imageCommentActivityPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getParcelableExtra(IMAGE_DATA) != null) {
            setContentView(R.layout.activity_image_commnet);
            previewImage = getIntent().getExtras().getParcelable(IMAGE_DATA);
            shouldShowChooseWinnerButton = getIntent().getBooleanExtra(SHOULD_SHOW_CHOOSE_WINNER, false);
            campaignId = getIntent().getIntExtra(CAMPAIGN_ID, -1);
            initPresenter();
            initView();
            initListener();
        }


    }


    @Override
    public void initView() {
        toolBarTitle = findViewById(R.id.toolbar_title);
        backBtn = findViewById(R.id.back_btn);

        addCommentProgress = findViewById(R.id.add_comment_progress);

        commentsRv = findViewById(R.id.comment_rv);
        toolBarTitle.setText(previewImage.albumName);
        //force adapter to start to render Add commentView
        Comment userComment = new Comment();
        commentList.add(userComment); /// acts As default for image Header
        commentList.add(userComment);/// acts As default for image Add comment

        commentsAdapter = new CommentsAdapter(previewImage, commentList, mentions);
        commentsAdapter.setShouldShowChooseWinnerButton(shouldShowChooseWinnerButton);
        commentsRv.setAdapter(commentsAdapter);
        imageCommentActivityPresenter.getImageComments(String.valueOf(previewImage.id), "0");

    }

    @Override
    public void initPresenter() {
        imageCommentActivityPresenter = new ImageCommentActivityImpl(this, this);
    }


    private void initListener() {

        pagingController = new PagingController(commentsRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                imageCommentActivityPresenter.getImageComments(String.valueOf(previewImage.id), String.valueOf(page));
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
                if (baseImage.isLiked) {
                    imageCommentActivityPresenter.unLikePhoto(baseImage);
                } else {
                    imageCommentActivityPresenter.likePhoto(baseImage);
                }
            }

            @Override
            public void onSubmitComment(String comment) {

                if (comment.length() > 0) {
                    imageCommentActivityPresenter.submitComment(String.valueOf(previewImage.id), comment);

                } else {
                    showToast(getResources().getString(R.string.comment_cant_not_be_null));
                }
            }

            @Override
            public void onAddToLightBox(BaseImage baseImage) {
                AddToLightBoxDialogFragment addToLightBoxDialogFragment = AddToLightBoxDialogFragment.getInstance(baseImage);
                ///change "lightBox icon state" after image get Added
                addToLightBoxDialogFragment.onLighBoxImageComplete = state -> {
                    if (state) {
                        previewImage.isSaved = true;
                    } else {
                        previewImage.isSaved = false;
                    }
                    commentsAdapter.notifyDataSetChanged();
                };
                addToLightBoxDialogFragment.show(getSupportFragmentManager(), AllAlbumImgActivity.class.getSimpleName());
            }

            @Override
            public void onAddToCartClick(BaseImage baseImage) {
                if (baseImage.isCart != null && baseImage.isCart) {
                    Intent intent = new Intent(ImageCommentActivity.this, CartActivity.class);
                    startActivity(intent);
                } else {
                    imageCommentActivityPresenter.addImageToCart(previewImage.id);
                }

            }

            @Override
            public void onImageRateClick(BaseImage baseImage, float rating) {

                if (!previewImage.isRated) {
                    imageCommentActivityPresenter.rateImage(baseImage, rating);
                } else {
                    showToast(getResources().getString(R.string.image_already_rated));
                }

            }

            @Override
            public void onImageCommentClicked() {
                if (commentList.size() == 2) {
                    commentsRv.scrollToPosition(commentList.size() - 1);
                    CustomAutoCompleteTextView customAutoCompleteTextView = (CustomAutoCompleteTextView) commentsRv.getChildAt(commentList.size() - 1).findViewById(R.id.img_send_comment_val);
                    customAutoCompleteTextView.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(customAutoCompleteTextView, InputMethodManager.SHOW_IMPLICIT);
//
                } else {
                    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                            switch (newState) {
                                case SCROLL_STATE_IDLE:
                                    //we reached the target position
                                    int xx = commentsRv.getChildCount();
                                    showToast("-->" + xx);
                                    CustomAutoCompleteTextView customAutoCompleteTextView = (CustomAutoCompleteTextView) commentsRv.getChildAt(xx - 1).findViewById(R.id.img_send_comment_val);
                                    customAutoCompleteTextView.requestFocus();
//
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(customAutoCompleteTextView, InputMethodManager.SHOW_IMPLICIT);
                                    recyclerView.removeOnScrollListener(this);
                                    break;
                            }
                        }
                    };

                    commentsRv.addOnScrollListener(onScrollListener);
                    commentsRv.getLayoutManager().smoothScrollToPosition(commentsRv, null, commentList.size() - 1);


                }
            }

            @Override
            public void onReportClicked(BaseImage image) {
                if (ImageCommentActivity.this.reasons == null) {
                    imageCommentActivityPresenter.getReportReasons(reasons -> {
                        ImageCommentActivity.this.reasons = reasons;
                        showReportFragment();
                    });
                } else {
                    showReportFragment();
                }
            }

            @Override
            public void onChooseWinnerClick(BaseImage image, Consumer<Boolean> success) {
                if (campaignId != -1) {
                    imageCommentActivityPresenter.chooseWinner(campaignId, image, state -> {
                        if (state)
                            showToast(getString(R.string.image_choosen_winner));
                        success.accept(state);
                    });
                }
            }
        };


        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(IMAGE_DATA, previewImage);
            setResult(RESULT_OK, intent);
            finish();

        });
    }

    private void showReportFragment() {
        ReportImageFragment.newInstance(reasons, getOnSubmitReportListener())
                .show(getSupportFragmentManager(), null);
    }

    private ReportImageFragment.OnSubmitClickListener getOnSubmitReportListener() {
        return (fragment, model, success) -> {
            model.imageId = previewImage.id;
            imageCommentActivityPresenter.submitReport(aBoolean -> {
                success.accept(aBoolean);
                if (!aBoolean)
                    showToast(getString(R.string.report_failed));
                else
                    showToast(getString(R.string.report_success));
            }, model);
        };
    }

    private List<ReportReason> reasons;

    @Override
    public void viewPhotoComment(ImageCommentsData imageCommentsData) {


// (1) is A default value to view AddComment layout in case there is now Comments
        this.commentList.addAll(1, imageCommentsData.comments.commentList);

        if (imageCommentsData.mentions.business != null)
            this.mentions.business.addAll(imageCommentsData.mentions.business);
        if (imageCommentsData.mentions.photographers != null)
            this.mentions.photographers.addAll(imageCommentsData.mentions.photographers);

        commentsAdapter.notifyDataSetChanged();

    }

    private void updateMentionedUserList(SubmitImageCommentData submitImageCommentData) {


        if (submitImageCommentData.mentions.business != null) {

            for (Business newBusiness : submitImageCommentData.mentions.business) {
                for (int i = 0; i < mentions.business.size(); i++) {
                    if (mentions.business.get(i).id.equals(newBusiness.id)) {
                        continue;
                    }
                    if (i == (mentions.business.size() - 1) && !mentions.business.get(i).id.equals(newBusiness.id))
                        mentions.business.add(newBusiness);
                }
            }


        }

        if (submitImageCommentData.mentions.photographers != null) {

            for (Photographer newPhotographer : submitImageCommentData.mentions.photographers) {
                for (int i = 0; i < mentions.photographers.size(); i++) {
                    if (mentions.photographers.get(i).id.equals(newPhotographer.id)) {
                        continue;
                    }
                    if (i == (mentions.photographers.size() - 1) && !mentions.photographers.get(i).id.equals(newPhotographer.id))
                        mentions.photographers.add(newPhotographer);
                }
            }


        }


        commentsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onImageLiked(BaseImage baseImage) {
        previewImage = baseImage;
        commentsAdapter.notifyDataSetChanged();


    }

    @Override
    public void onImageCommented(SubmitImageCommentData commentData) {
        // (1) is A default value to view AddComment layout in case there is now Comments
        this.commentList.add(1, commentData.comment);
        updateMentionedUserList(commentData);
        commentsAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewOnImagedAddedToCart(boolean state) {

        if (state) {
            previewImage.isCart = true;
        }
        commentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewOnImageRate(BaseImage baseImage) {
        if (baseImage != null) {
            previewImage.rate = baseImage.rate;
            previewImage.isRated = baseImage.isRated;
            commentsAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void viewImageProgress(Boolean state) {
        if (state) {
            addCommentProgress.setVisibility(View.VISIBLE);
        } else {
            addCommentProgress.setVisibility(View.GONE);
        }
    }


    @Override
    public void viewMessage(String msg) {
        showToast(msg);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(IMAGE_DATA, previewImage);
        setResult(RESULT_OK, intent);
        finish();
    }


}