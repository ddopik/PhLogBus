package com.example.ddopik.phlogbusiness.ui.lightboxphotos.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.lightboxphotos.view.LightboxPhotosView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;

import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LightboxPhotosPresenterImpl implements LightboxPhotosPresenter {

    public static final String TAG = LightboxPhotosPresenterImpl.class.getSimpleName();

    private static final String SAVED = "Saved";

    private LightboxPhotosView view;

    @Override
    public void setView(LightboxPhotosView view) {
        this.view = view;
    }

    @Override
    public void getLightboxPhotos(Context context, int lightBoxId, int page) {
        Disposable disposable = BaseNetworkApi.getLigtboxPhotos(lightBoxId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    view.addPhotos(response.getData());
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }

    @Override
    public Observable<Boolean> follow(Context context, BaseImage image) {
        return BaseNetworkApi.followUser( image.photographer.id.toString())
                .map(response -> response != null && response.data != null && response.data.isFollow());
    }

    @Override
    public Observable<Boolean> unfollow(Context context, BaseImage image) {
        return BaseNetworkApi.unfollowUser(image.photographer.id.toString())
                .map(response -> {
                    return response != null && response.data != null && !response.data.isFollow();
                });
    }

    @Override
    public Observable<Boolean> like(BaseImage image) {
        return BaseNetworkApi.likeImage(String.valueOf(image.id))
                .map(response -> response != null && response.data != null && response.data.isLiked);
    }

    @Override
    public Observable<Boolean> unlike(BaseImage image) {
        return BaseNetworkApi.unlikeImage(String.valueOf(image.id))
                .map(response -> response != null && response.data != null && !response.data.isLiked);
    }

    @Override
    public Observable<Boolean> delete(int lightBoxId, BaseImage image) {
        return BaseNetworkApi.removeLightBoxImage(String.valueOf(lightBoxId), String.valueOf(image.id))
                .map(response -> response != null && response.state.equals(BaseNetworkApi.STATUS_OK));
    }

    @Override
    public Observable<Boolean> addToCart(BaseImage image) {
        return BaseNetworkApi.addImageToCart(String.valueOf(image.id))
                .map(response -> {
                    return response != null && response.msg.equals(SAVED);
                });
    }
}
