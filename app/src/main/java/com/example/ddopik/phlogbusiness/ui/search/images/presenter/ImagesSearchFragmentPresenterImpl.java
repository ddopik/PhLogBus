package com.example.ddopik.phlogbusiness.ui.search.images.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.search.images.view.ImagesSearchFragmentView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public class ImagesSearchFragmentPresenterImpl implements ImagesSearchFragmentPresenter {


    private String TAG = ImagesSearchFragmentPresenterImpl.class.getSimpleName();

    private Context context;
    private ImagesSearchFragmentView imagesSearchFragmentView;


    public ImagesSearchFragmentPresenterImpl(Context context, ImagesSearchFragmentView imagesSearchFragmentView) {
        this.context = context;
        this.imagesSearchFragmentView = imagesSearchFragmentView;
    }


    @SuppressLint("CheckResult")
    @Override
    public void getSearchFilters() {
        imagesSearchFragmentView.viewImagesSearchProgress(true);
        BaseNetworkApi.getFilters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchAlbumResponse -> {
                    imagesSearchFragmentView.viewSearchFilters(searchAlbumResponse.data);
                    imagesSearchFragmentView.viewImagesSearchProgress(false);
                }, throwable -> {
                     imagesSearchFragmentView.viewImagesSearchProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });


    }


    @SuppressLint("CheckResult")
    @Override
    public void getSearchImages(String key, int page) {
        imagesSearchFragmentView.viewImagesSearchProgress(true);
        BaseNetworkApi.getSearchImages( key, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imagesSearchResponse -> {
                    imagesSearchFragmentView.viewImagesSearchProgress(false);
                    imagesSearchFragmentView.viewImagesSearchItems(imagesSearchResponse.data.imageList);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    imagesSearchFragmentView.viewImagesSearchProgress(false);
                });

    }

}
