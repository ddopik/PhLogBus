package com.example.ddopik.phlogbusiness.ui.commentimage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Comment;
import com.example.ddopik.phlogbusiness.base.commonmodel.Mentions;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.view.AddToLightBoxDialogFragment;
import com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.CommentsAdapter;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ImageCommentsData;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ImageRateResponse;
import com.example.ddopik.phlogbusiness.ui.commentimage.presenter.ImageCommentActivityImpl;
import com.example.ddopik.phlogbusiness.ui.commentimage.presenter.ImageCommentActivityPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class ImageCommentActivity extends BaseActivity implements ImageCommentActivityView {

    public static String IMAGE_DATA = "image_data";
    public static final int ImageComment_REQUEST_CODE = 1396;
    private CustomTextView toolBarTitle;
    private ImageButton backBtn;
    private BaseImage previewImage;

    private FrameLayout addCommentProgress;
    private CustomRecyclerView commentsRv;
    private List<Comment> commentList = new ArrayList<>();
    private Mentions mentions=new Mentions();
    private CommentsAdapter commentsAdapter;
    private PagingController pagingController;
    private ImageCommentActivityPresenter imageCommentActivityPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getParcelableExtra(IMAGE_DATA) != null) {
            setContentView(R.layout.activity_image_commnet);
            previewImage = getIntent().getExtras().getParcelable(IMAGE_DATA);
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

        commentsAdapter = new CommentsAdapter(previewImage, commentList,mentions);
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
            public void onImageLike(BaseImage baseImage) {
                imageCommentActivityPresenter.likePhoto(baseImage);
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
                AddToLightBoxDialogFragment addToLightBoxDialogFragment=  AddToLightBoxDialogFragment.getInstance(baseImage);
                ///change "lightBox icon state" after image get Added
                addToLightBoxDialogFragment.onLighBoxImageComplete= state -> {
                  if (state){
                     previewImage.isSaved=true;
                  } else {
                      previewImage.isSaved=false;
                  }
                    commentsAdapter.notifyDataSetChanged();
                };
                addToLightBoxDialogFragment.show(getSupportFragmentManager(), AllAlbumImgActivity.class.getSimpleName());
            }

            @Override
            public void onAddToCartClick(BaseImage baseImage) {
                if (baseImage.isCart){
                    showToast("view in cart");
                }else {
                    imageCommentActivityPresenter.addImageToCart(previewImage.id);
                }

            }
        };


        backBtn.setOnClickListener(v -> {
            Intent intent =new Intent();
            intent.putExtra(IMAGE_DATA, previewImage);
            setResult(RESULT_OK, intent);
            finish();

        });
    }

    @Override
    public void viewPhotoComment(ImageCommentsData imageCommentsData) {


// (1) is A default value to view AddComment layout in case there is now Comments
        this.commentList.addAll(1, imageCommentsData.comments.commentList);

        if(imageCommentsData.mentions.business !=null)
            this.mentions.business.addAll(imageCommentsData.mentions.business);
        if(imageCommentsData.mentions.photographers !=null)
            this.mentions.photographers.addAll(imageCommentsData.mentions.photographers);

        commentsAdapter.notifyDataSetChanged();

    }


    @Override
    public void viewImageLikedStatus(boolean state) {
        previewImage.isLiked = state;
        if (state) {
            previewImage.likesCount++;
        } else {
            previewImage.likesCount--;
        }
        commentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewOnImageCommented(Comment comment) {
        // (1) is A default value to view AddComment layout in case there is now Comments
        this.commentList.add(1, comment);
        commentsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onImagedAddedToCart(boolean state) {

        if (state) {
            previewImage.isCart = true;
        }
        commentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewImageRateStatus(ImageRateResponse imageRateResponse) {

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