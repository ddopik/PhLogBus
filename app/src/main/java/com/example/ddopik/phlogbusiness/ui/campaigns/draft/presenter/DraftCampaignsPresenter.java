package com.example.ddopik.phlogbusiness.ui.campaigns.draft.presenter;

import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;
import com.example.ddopik.phlogbusiness.ui.campaigns.draft.view.DraftCampaignsFragmentView;
import io.reactivex.Observable;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public interface DraftCampaignsPresenter {
    void getDraftCampaign(String page, DraftCampaignsFragmentView draftCampaignsFragmentView);

    Observable<Boolean> deleteCampaign(Campaign campaign);
}
