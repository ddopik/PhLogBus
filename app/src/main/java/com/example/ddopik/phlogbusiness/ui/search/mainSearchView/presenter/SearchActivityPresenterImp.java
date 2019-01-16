package com.example.ddopik.phlogbusiness.ui.search.mainSearchView.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.view.SearchActivityView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchActivityPresenterImp implements SearchActivityPresenter {

    private SearchActivityView searchActivityView;
    private Context context;
    public SearchActivityPresenterImp(Context context,SearchActivityView searchActivityView){

        this.searchActivityView=searchActivityView;
        this.context=context;

    }


    private String TAG=SearchActivityPresenterImp.class.getSimpleName();
    @SuppressLint("CheckResult")
    @Override
    public void getSearchFilters() {

        BaseNetworkApi.getFilters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchFiltersResponse -> {
                    searchActivityView.setFilters(searchFiltersResponse.data);
                 }, throwable -> {
                     CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });


    }


}
