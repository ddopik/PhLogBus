package com.example.ddopik.phlogbusiness.ui.album.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.album.model.AlbumGroup;
import com.example.ddopik.phlogbusiness.ui.album.model.AlbumPreviewResponseData;
import com.example.ddopik.phlogbusiness.ui.album.presenter.AlbumPreviewActivityPresenter;
import com.example.ddopik.phlogbusiness.ui.album.presenter.AlbumPreviewActivityPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.AlbumAdapter;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;

import java.util.ArrayList;
import java.util.List;

import static com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity.*;


/**
 * Created by abdalla_maged on 11/4/2018.
 */
public class AlbumPreviewActivity extends BaseActivity implements AlbumPreviewActivityView {


    public static final String ALBUM_PREVIEW_ID="album_preview_id";
    private int albumID;
    private List<AlbumGroup> albumGroupList=new ArrayList<>();
    private List<BaseImage> allAlbumImg = new ArrayList<>();
     private AlbumAdapter albumAdapter;
     private ImageView albumPreviewImg;
    private ProgressBar albumPreviewProgress;
    private CustomRecyclerView albumRv;
    private PagingController pagingController;
    private AlbumPreviewActivityPresenter albumPreviewActivityPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_preview);
        if (getIntent().getIntExtra(ALBUM_PREVIEW_ID, 0) != 0) {
            albumID = getIntent().getIntExtra(ALBUM_PREVIEW_ID, 0);
            initPresenter();
            initView();
            initListener();
        }

    }




    @Override
    public void initView() {
        albumPreviewImg=findViewById(R.id.album_preview_img);
        albumRv = findViewById(R.id.album_rv);
        albumPreviewProgress = findViewById(R.id.user_profile_progress_bar);

        // Set adapter object.
        albumAdapter = new AlbumAdapter(getBaseContext(), albumGroupList);
        albumRv.setAdapter(albumAdapter);
        albumPreviewActivityPresenter.getSelectedSearchAlbum(albumID,"0");
        albumPreviewActivityPresenter.getAlbumPreviewImages(albumID, 0);

    }

    @Override
    public void initPresenter() {
        albumPreviewActivityPresenter = new AlbumPreviewActivityPresenterImpl(getBaseContext(), this);
    }

    private void initListener() {


        pagingController = new PagingController(albumRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                albumPreviewActivityPresenter.getAlbumPreviewImages(albumID, page);
            }
        };

        albumAdapter.onAlbumImageClicked = albumImg -> {

            Intent intent =new Intent(this,AllAlbumImgActivity.class);
             intent.putExtra(ALBUM_ID,albumID);
            intent.putExtra(SELECTED_IMG_ID, albumImg.id);
             intent.putParcelableArrayListExtra(ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) allAlbumImg);
             startActivity(intent);

        };
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }



    @Override
    public void viewAlumPreviewData(AlbumPreviewResponseData albumPreviewResponseData) {



        GlideApp.with(this)
                .load(albumPreviewResponseData.preview)
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .into(albumPreviewImg);

        albumAdapter.notifyDataSetChanged();
    }

    @Override
    public void viwAlbumPreviewImages(List<BaseImage> baseImageList) {

        if (baseImageList.size() > 0){

            allAlbumImg.addAll(baseImageList);


            AlbumGroup singleGroup = new AlbumGroup();
            int albumGroupCounter = 0;

            for (int i = 0; i < allAlbumImg.size(); i++) {
                albumGroupCounter++;
                singleGroup.albumGroupList.add(allAlbumImg.get(i));

                if (albumGroupCounter == 4 || i == allAlbumImg.size() - 1) {
                    albumGroupList.add(singleGroup);
                    albumGroupCounter = 0;
                    singleGroup = new AlbumGroup();
                }


            }

            albumAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void viewAlbumPreviewProgress(boolean state) {
        if (state) {
            albumPreviewProgress.setVisibility(View.VISIBLE);

        } else {
            albumPreviewProgress.setVisibility(View.GONE);

        }

    }
}
