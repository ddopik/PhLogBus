package com.example.ddopik.phlogbusiness.ui.album.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.view.AddToLightBoxDialogFragment;
import com.example.ddopik.phlogbusiness.ui.album.presenter.AllAlbumImgActivityPresenter;
import com.example.ddopik.phlogbusiness.ui.album.presenter.AllAlbumImgActivityPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.album.presenter.AllAlbumImgPresnter;
import com.example.ddopik.phlogbusiness.ui.album.presenter.AllAlbumImgPresnterImpl;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.AllAlbumImgAdapter;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.ImageCommentActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public class AllAlbumImgActivity extends BaseActivity implements AllAlbumImgActivityView {


    public static String ALBUM_ID = "album_id";
    public static String ALL_ALBUM_IMAGES = "album_list";
    public static String SELECTED_IMG_ID = "selected_img_id";
    public static String LIST_TYPE = "list_type";
    private AllAlbumImgAdapter allAlbumImgAdapter;
    private List<BaseImage> albumImgList = new ArrayList<>();
    private ProgressBar albumImgProgress;
    private AllAlbumImgPresnter allAlbumImgPresnter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_album_img);
        initPresenter();
        initView();
    }


    @Override
    public void initView() {
        if (getIntent().getParcelableArrayListExtra(ALL_ALBUM_IMAGES) != null) {
            this.albumImgList = getIntent().<BaseImage>getParcelableArrayListExtra(ALL_ALBUM_IMAGES);
            int selectedPosition = getIntent().getIntExtra(SELECTED_IMG_ID,0);
            Constants.PhotosListType photosListType = (Constants.PhotosListType) getIntent().getSerializableExtra(LIST_TYPE);
            allAlbumImgAdapter = new AllAlbumImgAdapter(albumImgList, photosListType);
            albumImgProgress = findViewById(R.id.album_img_list_progress_bar);
            CustomRecyclerView allAlbumImgRv = findViewById(R.id.album_img_list_rv);
            allAlbumImgRv.setAdapter(allAlbumImgAdapter);


            for (int i=0;i<albumImgList.size();i++){
                if(albumImgList.get(i).id == selectedPosition) {
                    allAlbumImgRv.getLayoutManager().scrollToPosition(i);
                    break;
                }

            }

            initListener();
        }


//        RecyclerView.SmoothScroller smoothScroller = new
//                LinearSmoothScroller(getBaseContext()) {
//                    @Override
//                    protected int getVerticalSnapPreference() {
//                        return LinearSmoothScroller.SNAP_TO_ANY;
//                    }
//                };
//
//        smoothScroller.setTargetPosition(6);
//
//        allAlbumImgRv.getLayoutManager().startSmoothScroll(smoothScroller);

    }

    @Override
    public void initPresenter() {
        allAlbumImgPresnter=new AllAlbumImgPresnterImpl(this,this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void initListener() {

        allAlbumImgAdapter.onAlbumImgClicked = new AllAlbumImgAdapter.OnAlbumImgClicked() {
            @Override
            public void onAlbumImgClick(BaseImage albumImg) {
                Intent intent=new Intent(getBaseContext(),ImageCommentActivity.class);
                intent.putExtra(ImageCommentActivity.IMAGE_DATA,albumImg);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

            @Override
            public void onAlbumImgLikeClick(BaseImage albumImg) {
                allAlbumImgPresnter.likePhoto(String.valueOf(albumImg.id));
            }

            @Override
            public void onAlbumImgCommentClick(BaseImage albumImg) {
                Intent intent=new Intent(getBaseContext(),ImageCommentActivity.class);
                intent.putExtra(ImageCommentActivity.IMAGE_DATA,albumImg);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

            @Override
            public void onAlbumImgSaveClick(BaseImage albumImg) {

                if (albumImg.isSaved){
                    allAlbumImgPresnter.saveToProfileImage(albumImg);
                }else {
                    allAlbumImgPresnter.unSaveToProfileImage(albumImg);

                }
            }

            @Override
            public void onAlbumImgFollowClick(BaseImage albumImg) {
                allAlbumImgPresnter.followImagePhotoGrapher(albumImg);
            }
        };


    }

    @Override
    public void viewAlbumImageList(List<BaseImage> albumImgList) {

        this.albumImgList.clear();
        this.albumImgList.addAll(albumImgList);
        allAlbumImgAdapter.notifyDataSetChanged();

    }

    @Override
    public void onImageSavedToProfile(BaseImage baseImage, boolean state) {
        for (BaseImage mBaseImage: albumImgList){
            if (mBaseImage.id ==baseImage.id){
                mBaseImage.isSaved=state;
                break;
            }
        }

        allAlbumImgAdapter.notifyDataSetChanged();

    }

    @Override
    public void onImagePhotoGrapherFollowed(BaseImage baseImage, boolean state) {
        for (BaseImage mBaseImage: albumImgList){
            if (mBaseImage.id ==baseImage.id){
                mBaseImage.photographer.isFollow=state;
                break;
            }
        }
        allAlbumImgAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewAlbumImageListProgress(boolean state) {

        if (state) {
            albumImgProgress.setVisibility(View.VISIBLE);
        } else {
            albumImgProgress.setVisibility(View.GONE);
        }

    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }
}
