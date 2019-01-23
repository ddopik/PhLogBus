package com.example.ddopik.phlogbusiness.ui.campaigns.inner.view;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.model.DataItem;

import java.util.List;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public interface CampaignInnerPhotosFragmentView {

    void addPhotos(List<DataItem> data);
    void viewCampaignInnerPhotosProgress(boolean state);
}
