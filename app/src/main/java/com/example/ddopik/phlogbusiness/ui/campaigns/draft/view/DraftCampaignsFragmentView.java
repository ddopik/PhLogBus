package com.example.ddopik.phlogbusiness.ui.campaigns.draft.view;

import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;

import java.util.List;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public interface DraftCampaignsFragmentView {
    void viewDraftCampaign(List<Campaign> draftCampaignsList);

    void showDraftCampaignProgress(boolean state);
    void setNextPageUrl(String page);

}
