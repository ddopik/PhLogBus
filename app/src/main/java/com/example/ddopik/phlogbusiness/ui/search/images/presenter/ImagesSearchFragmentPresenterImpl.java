package com.example.ddopik.phlogbusiness.ui.search.images.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.base.commonmodel.Filter;
import com.example.ddopik.phlogbusiness.ui.search.images.view.ImagesSearchFragmentView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void getSearchImages(String key, List<Filter> filterList, int page) {
        imagesSearchFragmentView.viewImagesSearchProgress(true);

        int filterCount=0;
        Map<String,String> filtersMap=new HashMap<String, String>();
        for (int i=0;i<filterList.size();i++){
            for (int x=0;x<filterList.get(i).options.size();x++){
                if (filterList.get(i).options.get(x).isSelected) {
                    filtersMap.put("filter["+filterCount+"]",filterList.get(i).options.get(x).id.toString());
                    filterCount ++;
                }
            }

        }


        BaseNetworkApi.getSearchImages( key, filtersMap,String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(imagesSearchResponse -> {
                    imagesSearchFragmentView.viewImagesSearchProgress(false);
                    imagesSearchFragmentView.viewImagesSearchImages(imagesSearchResponse.data);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    imagesSearchFragmentView.viewImagesSearchProgress(false);
                });

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
                    Log.e(TAG, "getFilters() --->Error " + throwable.getMessage());
                    imagesSearchFragmentView.viewImagesSearchProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });


    }

    @Override
    public Map<String,String> getFilter(List<Filter> filterList){
        int filterCount=0;
        Map<String,String> filtersMap=new HashMap<String, String>();
        for (int i = 0; i< filterList.size(); i++){
            for (int x = 0; x< filterList.get(i).options.size(); x++){
                if (filterList.get(i).options.get(x).isSelected) {
                    filtersMap.put("filter["+filterCount+"]", filterList.get(i).options.get(x).id.toString());
                    filterCount ++;
                }
            }

        }
        return filtersMap;
    }
}
