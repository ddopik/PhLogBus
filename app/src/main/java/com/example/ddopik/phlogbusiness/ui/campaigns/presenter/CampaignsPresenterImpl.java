package com.example.ddopik.phlogbusiness.ui.campaigns.presenter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.campaigns.completed.view.CompleteCampaignsFragmentView;
import com.example.ddopik.phlogbusiness.ui.campaigns.completed.presenter.CompleteCampaignPresenter;
import com.example.ddopik.phlogbusiness.ui.campaigns.draft.view.DraftCampaignsFragmentView;
import com.example.ddopik.phlogbusiness.ui.campaigns.draft.presenter.DraftCampaignsPresenter;
import com.example.ddopik.phlogbusiness.ui.campaigns.running.presenter.RunningCampaignPresenter;
import com.example.ddopik.phlogbusiness.ui.campaigns.running.view.RunningCampaignFragmentView;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;

import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class CampaignsPresenterImpl implements DraftCampaignsPresenter,CompleteCampaignPresenter,RunningCampaignPresenter {


    private static final String TAG = CampaignsPresenterImpl.class.getSimpleName();
    private Context context;
    public CampaignsPresenterImpl(Context context) {
        this.context = context;
    }


    @SuppressLint("CheckResult")
    public void joinCampaign(String campaignID) {
//        BaseNetworkApi.followCampaign(campaignID)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(followCampaignResponse -> {
//                    campaignFragmentView.showMessage(context.getResources().getString(R.string.campaign_followed));
//                }, throwable -> {
//                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
//                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCompletedCampaign(String page,CompleteCampaignsFragmentView completeCampaignsFragmentView) {
        completeCampaignsFragmentView.showAllCompletedCampaignProgress(true);
        BaseNetworkApi.getAllCompleteCampaign(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campaignResponse -> {
                    completeCampaignsFragmentView.showAllCompletedCampaignProgress(false);
                    completeCampaignsFragmentView.viewAllCompletedCampaign(campaignResponse.data.data);
                    if (campaignResponse.data.nextPageUrl != null) {
                        completeCampaignsFragmentView.setNextPageUrl(Utilities.getNextPageNumber(context, campaignResponse.data.nextPageUrl));

                    } else {
                        completeCampaignsFragmentView.setNextPageUrl(null);
                    }
                 }, throwable -> {
                    completeCampaignsFragmentView.showAllCompletedCampaignProgress(false);
                    CustomErrorUtil.Companion.setError(context,TAG,throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getDraftCampaign(String page, DraftCampaignsFragmentView draftCampaignsFragmentView) {
        draftCampaignsFragmentView.showDraftCampaignProgress(true);
        BaseNetworkApi.getAllDraftCampaign(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campaignResponse -> {
                    draftCampaignsFragmentView.showDraftCampaignProgress(false);
                    draftCampaignsFragmentView.viewDraftCampaign(campaignResponse.data.data);
                    if (campaignResponse.data.nextPageUrl != null) {
                        draftCampaignsFragmentView.setNextPageUrl(Utilities.getNextPageNumber(context, campaignResponse.data.nextPageUrl));

                    } else {
                        draftCampaignsFragmentView.setNextPageUrl(null);
                    }

                }, throwable -> {
                    draftCampaignsFragmentView.showDraftCampaignProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getRunningCampaign(String page,RunningCampaignFragmentView runningCampaignFragmentView) {
        runningCampaignFragmentView.showAllCampaignProgress(true);
        BaseNetworkApi.getAllRunningCampaign(page, PrefUtils.getBrandToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campaignResponse -> {
                    runningCampaignFragmentView.showAllCampaignProgress(false);
                    runningCampaignFragmentView.viewAllCampaign(campaignResponse.data.data);
                    if (campaignResponse.data.nextPageUrl != null) {
                        runningCampaignFragmentView.setNextPageUrl(Utilities.getNextPageNumber(context, campaignResponse.data.nextPageUrl));

                    } else {
                        runningCampaignFragmentView.setNextPageUrl(null);
                    }


                }, throwable -> {
                    runningCampaignFragmentView.showAllCampaignProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }


}
