package com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.presenter;

import android.content.Context;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.model.AddCampaignRequestModel;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view.AddCampaignStepThreeActivityView;

import java.io.File;
import java.util.HashMap;

public interface AddCampaignStepThreePresenter {
     void submitCampaign(AddCampaignStepThreeActivityView addCampaignStepThreeActivityView,AddCampaignRequestModel addCampaignRequestModel,Context context);
}
