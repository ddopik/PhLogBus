package com.example.ddopik.phlogbusiness.ui.campaigns.running.view;


import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;

import java.util.List;

/**
 * Created by abdalla_maged on 10/2/2018.
 */
public interface RunningCampaignFragmentView {

    void viewAllCampaign(List<Campaign> homeCampaignList);

    void showAllCampaignProgress(boolean state);

}
