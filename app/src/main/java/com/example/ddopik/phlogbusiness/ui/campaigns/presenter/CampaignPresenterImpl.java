package com.example.ddopik.phlogbusiness.ui.campaigns.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.ddopik.phlogbusiness.Utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.campaigns.CampaignFragmentView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/2/2018.
 */
public class CampaignPresenterImpl implements CampaignPresenter {

    private static final String TAG = CampaignPresenterImpl.class.getSimpleName();
    private CampaignFragmentView campaignFragmentView;
    private Context context;

    public CampaignPresenterImpl(Context context, CampaignFragmentView campaignFragmentView) {
        this.campaignFragmentView = campaignFragmentView;
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getAllCampaign(int page) {
        campaignFragmentView.showAllCampaginProgress(true);
        BaseNetworkApi.getAllCampaign(PrefUtils.getUserToken(context),page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campaignResponse -> {
                    campaignFragmentView.showAllCampaginProgress(false);
                    if (campaignResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        campaignFragmentView.viewAllCampaign(campaignResponse.data.data);
                    } else {
                        campaignFragmentView.showAllCampaginProgress(false);
                        Log.e(TAG, "getAllCampaign --->()" + campaignResponse.state);
                    }
                }, throwable -> {
                    campaignFragmentView.showAllCampaginProgress(false);
                    Log.e(TAG, "getAllCampaign --->()" + throwable.getMessage());
                });
    }
}
