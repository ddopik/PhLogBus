package com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.model.AddCampaignRequestModel;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view.AddCampaignStepThreeActivityView;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view.AddCampaignStepTwoActivityView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.HashMap;

public class AddCampaignPresenterImpl implements AddCampaignStepTwoPresenter, AddCampaignStepThreePresenter {
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
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void submitCampaign(AddCampaignStepThreeActivityView addCampaignStepThreeActivityView, AddCampaignRequestModel addCampaignRequestModel , Context context) {
        addCampaignStepThreeActivityView.viewSubmitCampaignProgress(true);


        HashMap<String,String> data=new HashMap<String,String>();
        data.put("title_en",addCampaignRequestModel.campaignName);
        data.put("descrption_en",addCampaignRequestModel.campaignDescription);
        data.put("prize",addCampaignRequestModel.campaignPrize);
        data.put("start_date",addCampaignRequestModel.campaignStartDate);
        data.put("end_date",addCampaignRequestModel.campaignEndDate);
        data.put("is_draft",addCampaignRequestModel.isDraft);

        for(int i=0;i<addCampaignRequestModel.industryList.size();i++){
            data.put("tags["+i+"]",addCampaignRequestModel.industryList.get(i).nameEn);
        }


        BaseNetworkApi.submitCampaign(PrefUtils.getBrandToken(context),data,addCampaignRequestModel.campaignCoverPhoto)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(submitCampaignResponse -> {
                    addCampaignStepThreeActivityView.viewSubmitCampaignProgress(false);
                    addCampaignStepThreeActivityView.viewMessage(submitCampaignResponse.msg);
                    addCampaignStepThreeActivityView.onCampaignCompleted();
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    addCampaignStepThreeActivityView.viewSubmitCampaignProgress(false);
                });
    }
}
