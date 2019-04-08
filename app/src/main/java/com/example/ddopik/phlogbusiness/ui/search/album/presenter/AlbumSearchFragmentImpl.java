package com.example.ddopik.phlogbusiness.ui.search.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.example.ddopik.phlogbusiness.base.commonmodel.Filter;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.search.album.view.AlbumSearchFragmentView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void getAlbumSearch(String key, List<Filter> filterList, String page) {


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

        albumSearchFragmentView.showFilterSearchProgress(true);
        BaseNetworkApi.getSearchAlbum(key, filtersMap,String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumSearchResponse -> {
                    albumSearchFragmentView.viewSearchAlbum(albumSearchResponse.data);
                    albumSearchFragmentView.showFilterSearchProgress(false);
                    if (albumSearchResponse.data.nextPageUrl != null) {
                        albumSearchFragmentView.setNextPageUrl(Utilities.getNextPageNumber(context, albumSearchResponse.data.nextPageUrl));

                    } else {
                        albumSearchFragmentView.setNextPageUrl(null);
                    }
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    albumSearchFragmentView.showFilterSearchProgress(false);
                });
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
