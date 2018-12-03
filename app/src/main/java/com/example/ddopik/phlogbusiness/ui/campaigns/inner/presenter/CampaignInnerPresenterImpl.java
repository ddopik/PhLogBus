package com.example.ddopik.phlogbusiness.ui.campaigns.inner.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.example.ddopik.phlogbusiness.Utiltes.PrefUtils;
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
        BaseNetworkApi.getCampaignDetails(PrefUtils.getUserToken(context), campaignID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campaignInnerResponse -> {
                            if (campaignInnerResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                                campaignInnerActivityView.viewCampaignTitle(campaignInnerResponse.campaign.titleEn);
                                campaignInnerActivityView.viewCampaignLeftDays(campaignInnerResponse.campaign.leftDays);
                                campaignInnerActivityView.viewCampaignHeaderImg(campaignInnerResponse.campaign.imageCover);
                                campaignInnerActivityView.viewCampaignHostedBy(campaignInnerResponse.business.userName);
                                campaignInnerActivityView .viewCampaignMissionDescription(campaignInnerResponse.campaign.descrptionEn,campaignInnerResponse.campaign.numberImages);
                            }
                        },
                        throwable -> Log.e(TAG, "getCampaignDetails() --->Error " + throwable.getMessage()));
    }




}
