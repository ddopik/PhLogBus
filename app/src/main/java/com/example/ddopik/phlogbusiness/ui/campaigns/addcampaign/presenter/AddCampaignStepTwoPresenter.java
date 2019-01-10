package com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.presenter;

import android.content.Context;
import com.example.ddopik.phlogbusiness.base.commonmodel.Industry;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view.AddCampaignStepTwoActivityView;

public interface AddCampaignStepTwoPresenter {
    void getTags(AddCampaignStepTwoActivityView addCampaignStepTwoActivityView,Context context,String Tag);
}
