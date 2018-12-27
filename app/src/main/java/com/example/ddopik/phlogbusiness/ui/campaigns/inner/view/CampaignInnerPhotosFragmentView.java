package com.example.ddopik.phlogbusiness.ui.campaigns.inner.view;
import com.example.ddopik.phlogbusiness.base.model.ImageObj;

import java.util.List;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public interface CampaignInnerPhotosFragmentView {

    void getInnerCampaignPhotos(List<ImageObj> campaignInnerPhotoList);
    void viewCampaignInnerPhotosProgress(boolean state);
}
