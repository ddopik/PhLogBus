package com.example.ddopik.phlogbusiness.ui.signup.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.signup.view.UploadSignUpPhotoView;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class UploadSignUpPhotoPresenterImpl implements UploadSignUpPhotoPresenter {

    private Context context;
    private UploadSignUpPhotoView uploadSignUpPhotoView;

    public UploadSignUpPhotoPresenterImpl(Context context, UploadSignUpPhotoView uploadSignUpPhotoView){
        this.context=context;
        this.uploadSignUpPhotoView=uploadSignUpPhotoView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void uploadProfilePhoto(File image) {
        uploadSignUpPhotoView.viewUploadProfileImgProgress(true);
        BaseNetworkApi.uploadUserProfilePhoto(PrefUtils.getBrandToken(context),image).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uploadProfileImgResponse -> {
                    uploadSignUpPhotoView.viewUploadProfileImgProgress(false);
                    uploadSignUpPhotoView.navigateToHome();
                },throwable -> {
                    uploadSignUpPhotoView.navigateToHome();
                    uploadSignUpPhotoView.viewUploadProfileImgProgress(false);
                });

    }
}
