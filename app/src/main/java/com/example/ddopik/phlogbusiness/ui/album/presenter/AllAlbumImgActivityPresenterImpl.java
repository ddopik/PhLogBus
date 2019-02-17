package com.example.ddopik.phlogbusiness.ui.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivityView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AllAlbumImgActivityPresenterImpl implements AllAlbumImgActivityPresenter {

    public static String TAG=AllAlbumImgActivityPresenterImpl.class.getSimpleName();

    private AllAlbumImgActivityView allAlbumImgActivityView;
    private Context context;

    public AllAlbumImgActivityPresenterImpl(AllAlbumImgActivityView allAlbumImgActivityView, Context context){
        this.context=context;
        this.allAlbumImgActivityView=allAlbumImgActivityView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getAlbumImages(int albumId, int pageNum) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);
        BaseNetworkApi.getAlbumImagesPreview( String.valueOf(albumId),String.valueOf( pageNum))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumPreviewImagesResponse -> {
                    allAlbumImgActivityView.viewAlbumImageList(albumPreviewImagesResponse.data.imagesList);
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                });
    }
}
