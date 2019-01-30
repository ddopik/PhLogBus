package com.example.ddopik.phlogbusiness.ui.lightboxphotos.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.ui.lightboxphotos.view.LightboxPhotosView;
import io.reactivex.Observable;

public interface LightboxPhotosPresenter {

    void setView(LightboxPhotosView view);

    void getLightboxPhotos(Context context, int lightBoxId, int page);

    Observable<Boolean> follow(Context context, BaseImage image);

    Observable<Boolean> unfollow(Context context, BaseImage image);

    Observable<Boolean> like(BaseImage image);

    Observable<Boolean> unlike(BaseImage image);

    Observable<Boolean> delete(int lightBoxId, BaseImage image);

    Observable<Boolean> addToCart(BaseImage image);
}
