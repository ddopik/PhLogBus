package com.example.ddopik.phlogbusiness.ui.search.profile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.search.profile.view.ProfileSearchFragmentView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 11/1/2018.
 */
public class ProfileSearchPresenterImpl implements ProfileSearchPresenter {

    private String TAG = ProfileSearchPresenterImpl.class.getSimpleName();

    private Context context;
    private ProfileSearchFragmentView profileSearchFragmentView;

    public ProfileSearchPresenterImpl(Context context, ProfileSearchFragmentView profileSearchFragmentView) {
        this.context = context;
        this.profileSearchFragmentView = profileSearchFragmentView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getProfileSearchList(String key, String page) {
        profileSearchFragmentView.viewProfileSearchProgress(true);
        BaseNetworkApi.getProfileSearch(key, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profileSearchResponse -> {
                    profileSearchFragmentView.viewProfileSearchProgress(false);
                    profileSearchFragmentView.viewProfileSearchItems(profileSearchResponse.data);
                    if (profileSearchResponse.data.nextPageUrl != null) {
                        profileSearchFragmentView.setNextPageUrl(Utilities.getNextPageNumber(context, profileSearchResponse.data.nextPageUrl));

                    } else {
                        profileSearchFragmentView.setNextPageUrl(null);
                    }
                }, throwable -> {
                    profileSearchFragmentView.viewProfileSearchProgress(false);
                    CustomErrorUtil.Companion.setError(context,TAG,throwable);
                });
    }
}
