package com.example.ddopik.phlogbusiness.ui.campaigns.inner.ui;

import com.example.softmills.phlog.ui.campaigns.inner.model.CampaignInnerPhoto;

import java.util.List;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public interface CampaignInnerPhotosFragmentView {

    void getInnerCampaignPhotos(List<CampaignInnerPhoto> campaignInnerPhotoList);
    void viewCampaignInnerPhotosProgress(boolean state);
}
