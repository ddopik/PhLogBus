package com.example.ddopik.phlogbusiness.ui.campaigns.inner.view;

import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public interface CampaignInnerActivityView {

    void viewCampaignTitle(String name);
    void viewCampaignLeftDays(String dayLeft);
    void viewCampaignHostedBy(String hostName);
    void viewCampaignHeaderImg(String img);
    void viewCampaignMissionDescription(String missionDesc, int photosCount);

    void setCampaign(Campaign campaign);
}
