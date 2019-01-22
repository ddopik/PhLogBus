package com.example.ddopik.phlogbusiness.ui.lightboxphotos.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.ui.lightboxphotos.view.LightboxPhotosView;

public interface LightboxPhotosPresenter {
    void setView(LightboxPhotosView view);

    void getLightboxPhotos(Context context, int lightBoxId, int page);
}
