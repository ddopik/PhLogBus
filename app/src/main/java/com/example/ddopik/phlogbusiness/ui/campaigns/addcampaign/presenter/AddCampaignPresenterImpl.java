package com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.Utiltes.ErrorUtil;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view.AddCampaignStepTwoActivityView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddCampaignPresenterImpl implements AddCampaignStepTwoPresenter {
    public static String TAG = AddCampaignPresenterImpl.class.getSimpleName();

    @SuppressLint("CheckResult")
    @Override
    public void getIndustries(AddCampaignStepTwoActivityView addCampaignStepTwoActivityView, Context context) {
        BaseNetworkApi.getAllIndustries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allIndustriesResponse -> {
                    addCampaignStepTwoActivityView.viewIndustries(allIndustriesResponse.industryList);
                }, throwable -> {
                    ErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }

}
