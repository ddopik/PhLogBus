package com.example.ddopik.phlogbusiness.ui.album.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSmoothScroller;
import android.view.View;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.view.AddToLightBoxDialogFragment;
import com.example.ddopik.phlogbusiness.ui.album.presenter.AlbumPreviewActivityPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.album.presenter.AllAlbumImgActivityPresenter;
import com.example.ddopik.phlogbusiness.ui.album.presenter.AllAlbumImgActivityPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.AllAlbumImgAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public class AllAlbumImgActivity extends BaseActivity implements AllAlbumImgActivityView {


    public static String ALBUM_ID = "album_id";
    public static String SELECTED_IMG_ID = "selected_img_id";
    public static String CURRENT_PAGE = "current_page";
    private int albumId;
    private int selectedImageId;
    private int currentPage;
    private CustomRecyclerView allAlbumImgRv;
    private AllAlbumImgAdapter allAlbumImgAdapter;
    private List<BaseImage> albumImgList = new ArrayList<>();
    private ProgressBar albumImgProgress;
    private AllAlbumImgActivityPresenter allAlbumImgActivityPresenter;
    private PagingController pagingController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_album_img);

        if (getIntent().getIntExtra(ALBUM_ID, 0) != 0) {
            initPresenter();
            initView();
            initListener();

        }
    }


    @Override
    public void initView() {

        this.albumId = getIntent().getIntExtra(ALBUM_ID, 0);
        this.selectedImageId = getIntent().getIntExtra(SELECTED_IMG_ID, 0);
        this.currentPage = getIntent().getIntExtra(CURRENT_PAGE, 0);

        allAlbumImgAdapter = new AllAlbumImgAdapter(albumImgList);
        albumImgProgress = findViewById(R.id.album_img_list_progress_bar);
        allAlbumImgRv = findViewById(R.id.album_img_list_rv);
        allAlbumImgRv.setAdapter(allAlbumImgAdapter);

        for (int i = 1; i < currentPage; i++) {
            allAlbumImgActivityPresenter.getAlbumImages(albumId, i);
        }

    }

    @Override
    public void initPresenter() {
        allAlbumImgActivityPresenter = new AllAlbumImgActivityPresenterImpl(this, this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void initListener() {

        allAlbumImgAdapter.onAlbumImgClicked = new AllAlbumImgAdapter.OnAlbumImgClicked() {
            @Override
            public void onAlbumImgClick(BaseImage albumImg) {

                Intent intent = new Intent(getBaseContext(), AlbumCommentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

            @Override
            public void onAlbumImgLikeClick(BaseImage albumImg) {
                showToast("like");
            }

            @Override
            public void onAlbumImgCommentClick(BaseImage albumImg) {
                Intent intent = new Intent(getBaseContext(), AlbumCommentActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAlbumImgAddLightBoxClick(BaseImage albumImg) {
                AddToLightBoxDialogFragment.getInstance().show(getSupportFragmentManager(),AllAlbumImgActivity.class.getSimpleName());
            }

            @Override
            public void onAlbumImgToCartClick(BaseImage albumImg) {

            }
        };


        pagingController = new PagingController(allAlbumImgRv) {
            @Override
            public void getPagingControllerCallBack(int page) {

                if (currentPage < page)
                    allAlbumImgActivityPresenter.getAlbumImages(albumId, page);

            }
        };


    }

    @Override
    public void viewAlbumImageList(List<BaseImage> albumImgList) {


        this.albumImgList.addAll(albumImgList);
        allAlbumImgAdapter.notifyDataSetChanged();

        for (int i = 0; i < albumImgList.size(); i++) {
            if (albumImgList.get(i).id.equals(selectedImageId)) {
                allAlbumImgRv.getLayoutManager().scrollToPosition(i);
                break;
            }

        }


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


}
