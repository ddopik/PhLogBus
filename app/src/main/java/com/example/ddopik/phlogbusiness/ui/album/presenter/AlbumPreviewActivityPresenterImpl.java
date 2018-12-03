package com.example.ddopik.phlogbusiness.ui.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.Utiltes.ErrorUtils;
import com.example.ddopik.phlogbusiness.Utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.album.view.AlbumPreviewActivityView;
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
    public void getSelectedSearchAlbum(String albumID,String pageNum) {
        albumPreviewActivityView.viewAlbumPreviewProgress(true);
        BaseNetworkApi.getSearchSelectedAlbum(PrefUtils.getUserToken(context),albumID, pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumPreviewResponse -> {
                    if (albumPreviewResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        albumPreviewActivityView.viewAlumPreview(albumPreviewResponse.data);
                    } else {
                        ErrorUtils.setError(context, TAG, albumPreviewResponse.msg, albumPreviewResponse.state);
                    }
                    albumPreviewActivityView.viewAlbumPreviewProgress(false);
                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable.toString());
                    albumPreviewActivityView.viewAlbumPreviewProgress(false);
                });


    }




}
