package com.example.ddopik.phlogbusiness.ui.lightbox.view;

import com.example.ddopik.phlogbusiness.base.commonmodel.LightBox;

import java.util.List;

public interface BrandLightBoxFragmentView {

    void viewLightBoxes(List<LightBox> lightBoxes,boolean forceRefresh);
    void onLightBoxLightDeleted();
    void onLightBoxLightAdded();
    void showMessage(String msg);
    void viewLightBoxProgress(Boolean state);
    void setNextPageUrl(String page);

}
