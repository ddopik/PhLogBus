package com.example.ddopik.phlogbusiness.ui.album.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.AllAlbumImgAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public class AllAlbumImgActivity extends BaseActivity implements AllAlbumImgActivityView {


    public static String ALBUM_ID = "album_id";
    public static String ALL_ALBUM_IMAGES = "album_list";
    public static String SELECTED_IMG_ID = "selected_img_id";
    private String albumId;
    private AllAlbumImgAdapter allAlbumImgAdapter;
    private List<BaseImage> albumImgList = new ArrayList<>();
    private ProgressBar albumImgProgress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_album_img);


        initPresenter();
        initView();
        initListener();


    }


    @Override
    public void initView() {
        if (getIntent().getParcelableArrayListExtra(ALL_ALBUM_IMAGES) != null && getIntent().getStringExtra(ALBUM_ID) != null) {
            this.albumId = getIntent().getStringExtra(ALBUM_ID);
            this.albumImgList = getIntent().<BaseImage>getParcelableArrayListExtra(ALL_ALBUM_IMAGES);
            String selectedPosition = getIntent().getStringExtra(SELECTED_IMG_ID);

        allAlbumImgAdapter = new AllAlbumImgAdapter(albumImgList);
        albumImgProgress = findViewById(R.id.album_img_list_progress_bar);
        CustomRecyclerView allAlbumImgRv = findViewById(R.id.album_img_list_rv);
        allAlbumImgRv.setAdapter(allAlbumImgAdapter);


            for (int i=0;i<albumImgList.size();i++){
                if(albumImgList.get(i).id.equals(selectedPosition) ){
                    allAlbumImgRv.getLayoutManager().scrollToPosition(i);
                    break;
                }
            }


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

    }

    private void initListener() {

        allAlbumImgAdapter.onAlbumImgClicked = new AllAlbumImgAdapter.OnAlbumImgClicked() {
            @Override
            public void onAlbumImgClick(BaseImage albumImg) {

                Intent intent=new Intent(getBaseContext(),AlbumCommentActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAlbumImgLikeClick(BaseImage albumImg) {
                showToast("like");
            }

            @Override
            public void onAlbumImgCommentClick(BaseImage albumImg) {
                Intent intent=new Intent(getBaseContext(),AlbumCommentActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAlbumImgDownloadClick(BaseImage albumImg) {

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
