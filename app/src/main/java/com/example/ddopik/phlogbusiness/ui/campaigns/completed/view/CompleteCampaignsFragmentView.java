package com.example.ddopik.phlogbusiness.ui.campaigns.completed.view;

import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;

import java.util.List;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public interface CompleteCampaignsFragmentView {
    void viewAllCompletedCampaign(List<Campaign> campaignList);

    void showAllCompletedCampaignProgress(boolean state);
    void setNextPageUrl(String page);

}
