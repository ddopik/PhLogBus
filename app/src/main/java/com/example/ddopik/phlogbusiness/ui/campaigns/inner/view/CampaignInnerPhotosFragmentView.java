package com.example.ddopik.phlogbusiness.ui.campaigns.inner.view;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

import java.util.List;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public interface CampaignInnerPhotosFragmentView {

    void getInnerCampaignPhotos(List<BaseImage> campaignInnerPhotoList);
    void viewCampaignInnerPhotosProgress(boolean state);
}
