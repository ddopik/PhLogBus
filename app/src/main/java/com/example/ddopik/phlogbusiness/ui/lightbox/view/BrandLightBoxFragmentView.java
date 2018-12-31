package com.example.ddopik.phlogbusiness.ui.lightbox.view;

import com.example.ddopik.phlogbusiness.ui.lightbox.model.LightBox;

import java.util.List;

public interface BrandLightBoxFragmentView {

    void viewLightBoxes(List<LightBox> lightBoxes);
    void showMessage(String msg);
    void viewLightBoxProgress(Boolean state);
}
