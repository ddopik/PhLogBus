package com.example.ddopik.phlogbusiness.ui.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.album.view.AlbumPreviewActivityView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class AlbumPreviewActivityPresenterImpl implements AlbumPreviewActivityPresenter {

    private String TAG = AlbumPreviewActivityPresenterImpl.class.getSimpleName();
    private Context context;
    private AlbumPreviewActivityView albumPreviewActivityView;

    public AlbumPreviewActivityPresenterImpl(Context context, AlbumPreviewActivityView albumPreviewActivityView) {
        this.context = context;
        this.albumPreviewActivityView = albumPreviewActivityView;
    }

    @SuppressLint("CheckResult")


    @Override
    public void getAlbumDetails(int albumID, String pageNum) {
        albumPreviewActivityView.viewAlbumPreviewProgress(true);
        BaseNetworkApi.getAlbumDetails( String.valueOf(albumID), pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumPreviewResponse -> {
                    albumPreviewActivityView.viewAlumPreviewData(albumPreviewResponse.data);
                    albumPreviewActivityView.viewAlbumPreviewProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable.toString());
                    albumPreviewActivityView.viewAlbumPreviewProgress(false);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void getAlbumPreviewImages(int albumId,int  pageNum) {
        BaseNetworkApi.getAlbumImagesPreview( String.valueOf(albumId),String.valueOf( pageNum))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumPreviewImagesResponse -> {
                    albumPreviewActivityView.viwAlbumPreviewImages(albumPreviewImagesResponse.data.imagesList);
                    albumPreviewActivityView.viewAlbumPreviewProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable.toString());
                    albumPreviewActivityView.viewAlbumPreviewProgress(false);
                });

    }
}
