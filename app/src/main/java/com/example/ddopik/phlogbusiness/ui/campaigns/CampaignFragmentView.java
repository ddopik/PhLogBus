package com.example.ddopik.phlogbusiness.ui.campaigns;


import com.example.ddopik.phlogbusiness.ui.campaigns.model.HomeCampaign;

import java.util.List;

/**
 * Created by abdalla_maged on 10/2/2018.
 */
public interface CampaignFragmentView {

    void viewAllCampaign(List<HomeCampaign> homeCampaignList);

    void showAllCampaginProgress(boolean state);

}
