package com.example.ddopik.phlogbusiness.ui.campaigns.inner.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.view.CampaignInnerActivityView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public class CampaignInnerPresenterImpl implements CampaignInnerPresenter {

    private String TAG = CampaignInnerPresenterImpl.class.getSimpleName();

    private Context context;
    private CampaignInnerActivityView campaignInnerActivityView;


    public CampaignInnerPresenterImpl(Context context, CampaignInnerActivityView campaignInnerActivityView) {
        this.context = context;
        this.campaignInnerActivityView = campaignInnerActivityView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getCampaignDetails(String campaignID) {
        BaseNetworkApi.getCampaignDetails(PrefUtils.getBrandToken(context), campaignID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campaignInnerResponse -> {

                            if (campaignInnerResponse.campaign != null) {
                                campaignInnerActivityView.viewCampaignTitle(campaignInnerResponse.campaign.titleEn);
                                campaignInnerActivityView.viewCampaignLeftDays(String.valueOf(campaignInnerResponse.campaign.daysLeft));
                                campaignInnerActivityView.viewCampaignHeaderImg(campaignInnerResponse.campaign.imageCover);
                                campaignInnerActivityView.viewCampaignHostedBy(campaignInnerResponse.campaign.business.firstName +" "+campaignInnerResponse.campaign.business.lastName);
//                                campaignInnerActivityView.viewCampaignMissionDescription(campaignInnerResponse.campaign.descrptionEn, -1);
                                campaignInnerActivityView.setCampaign(campaignInnerResponse.campaign);
                            }
                        },
                        throwable -> Log.e(TAG, "getCampaignDetails() --->Error " + throwable.getMessage()));
    }


}
