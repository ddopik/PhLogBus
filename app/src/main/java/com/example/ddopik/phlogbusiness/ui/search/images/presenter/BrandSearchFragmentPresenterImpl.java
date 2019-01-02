package com.example.ddopik.phlogbusiness.ui.search.images.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.ui.search.images.view.ImagesSearchFragmentView;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public class BrandSearchFragmentPresenterImpl implements BrandSearchFragmentPresenter {


    private String TAG = BrandSearchFragmentPresenterImpl.class.getSimpleName();

    private Context context;
    private ImagesSearchFragmentView imagesSearchFragmentView;


    public BrandSearchFragmentPresenterImpl(Context context, ImagesSearchFragmentView imagesSearchFragmentView) {
        this.context = context;
        this.imagesSearchFragmentView = imagesSearchFragmentView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getSearchBrand(String key, int page) {
        imagesSearchFragmentView.viewImagesSearchProgress(true);
//        BaseNetworkApi.getBrandSearch( key, page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(brandSearchResponse -> {
//                    imagesSearchFragmentView.viewImagesSearchProgress(false);
//                    imagesSearchFragmentView.viewImagesSearchItems(brandSearchResponse.data.data);
//                }, throwable -> {
//                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
//                    imagesSearchFragmentView.viewImagesSearchProgress(false);
//                });

    }

}
