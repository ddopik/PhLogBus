package com.example.ddopik.phlogbusiness.ui.album.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Comment;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.album.presenter.ImageCommentActivityImpl;
import com.example.ddopik.phlogbusiness.ui.album.presenter.ImageCommentActivityPresenter;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.CommentsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class ImageCommentActivity extends BaseActivity implements ImageCommentActivityView {

    public static String IMAGE_DATA = "image_data";
    private CustomTextView toolBarTitle;
    private ImageButton backBtn;
    private BaseImage baseImage;

    private ProgressBar addCommentProgress;
    private CustomRecyclerView commentsRv;
    private List<Comment> userCommentList = new ArrayList<>();
    private CommentsAdapter commentsAdapter;
    private PagingController pagingController;
    private ImageCommentActivityPresenter imageCommentActivityPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getParcelableExtra(IMAGE_DATA) != null) {
            setContentView(R.layout.activity_image_commnet);
            baseImage = getIntent().getExtras().getParcelable(IMAGE_DATA);
            initPresenter();
            initView();
            initListener();
        }

    }


    @Override
    public void initView() {
        toolBarTitle = findViewById(R.id.toolbar_title);
        backBtn=findViewById(R.id.back_btn);

        addCommentProgress = findViewById(R.id.add_comment_progress);
        commentsRv = findViewById(R.id.comment_rv);
        toolBarTitle.setText(baseImage.albumName);
        //force adapter to start to render Add commentView
        Comment userComment = new Comment();
        userCommentList.add(userComment); /// acts As default for image Header
        userCommentList.add(userComment);/// acts As default for image Add comment

        commentsAdapter = new CommentsAdapter(baseImage, userCommentList);
        commentsRv.setAdapter(commentsAdapter);
        imageCommentActivityPresenter.getImageComments(String.valueOf(baseImage.id), "0");
    }

    @Override
    public void initPresenter() {
        imageCommentActivityPresenter = new ImageCommentActivityImpl(this, this);
    }


    private void initListener() {

        pagingController = new PagingController(commentsRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                imageCommentActivityPresenter.getImageComments(String.valueOf(baseImage.id), String.valueOf(page));
            }
        };


        commentsAdapter.commentAdapterAction = new CommentsAdapter.CommentAdapterAction() {
            @Override
            public void onImageLike(BaseImage baseImage) {
                imageCommentActivityPresenter.likePhoto(String.valueOf(baseImage.id));
            }

            @Override
            public void onCommentImageSubmit(String comment) {

                if (comment.length() > 0) {
                    imageCommentActivityPresenter.submitComment(String.valueOf(baseImage.id), comment);
                } else {
                    showToast(getResources().getString(R.string.comment_cant_not_be_null));
                }
             }
        };


        backBtn.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void viewPhotoComment(List<Comment> commentList) {
        this.userCommentList.addAll(commentList);
        commentsAdapter.notifyDataSetChanged();
    }



    @Override
    public void viewAddCommentProgress(Boolean state) {
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
}