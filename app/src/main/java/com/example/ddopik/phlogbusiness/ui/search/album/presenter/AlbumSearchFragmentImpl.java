package com.example.ddopik.phlogbusiness.ui.search.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.search.album.view.AlbumSearchFragmentView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public class AlbumSearchFragmentImpl implements AlbumSearchPresenter {


    private String TAG = AlbumSearchFragmentImpl.class.getSimpleName();

    private Context context;
    private AlbumSearchFragmentView albumSearchFragmentView;

    public AlbumSearchFragmentImpl(Context context, AlbumSearchFragmentView albumSearchFragmentView) {
        this.context = context;
        this.albumSearchFragmentView = albumSearchFragmentView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getSearchFilters() {
        albumSearchFragmentView.showFilterSearchProgress(true);
        BaseNetworkApi.getFilters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchAlbumResponse -> {
                    albumSearchFragmentView.viewSearchFilters(searchAlbumResponse.data);
                    albumSearchFragmentView.showFilterSearchProgress(false);
                }, throwable -> {
                    Log.e(TAG, "getFilters() --->Error " + throwable.getMessage());
                    albumSearchFragmentView.showFilterSearchProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });


    }

    @SuppressLint("CheckResult")
    @Override
    public void getAlbumSearch(String key, int page) {
        albumSearchFragmentView.showFilterSearchProgress(true);
        BaseNetworkApi.getSearchAlbum(key, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumSearchResponse -> {
                    albumSearchFragmentView.viewSearchAlbum(albumSearchResponse.data.data);
                    albumSearchFragmentView.showFilterSearchProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    albumSearchFragmentView.showFilterSearchProgress(false);
                });
    }
}
