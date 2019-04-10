package com.example.ddopik.phlogbusiness.ui.lightboxphotos.view;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

import java.util.List;

public interface LightboxPhotosView {
    void addPhotos(List<BaseImage> data);
    void onPhotoAddedToCart(BaseImage baseImage,boolean state);

    void viewLightBoxProgress(boolean state);
}
