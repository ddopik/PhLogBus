package com.example.ddopik.phlogbusiness.ui.lightbox.presenter;

public interface BrandLightBoxPresenter {

    void getLightBoxes(String page,boolean forceReFresh);
    void deleteLightBox(int id);
    void addLightBox(String name,String description);
}
