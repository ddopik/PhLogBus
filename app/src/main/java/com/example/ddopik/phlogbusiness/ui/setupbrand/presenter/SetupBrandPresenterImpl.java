package com.example.ddopik.phlogbusiness.ui.setupbrand.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Industry;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.SetupBrandModel;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SetupBrandPresenterImpl implements SetupBrandPresenter {

    private CompositeDisposable DISPOSABLES = new CompositeDisposable();

    public static final String TAG = SetupBrandPresenterImpl.class.getSimpleName();

    private SetupBrandView view;

    @Override
    public void setView(SetupBrandView view) {
        this.view = view;
    }

    @Override
    public ValidationResult shouldProceed(int currentStep, SetupBrandModel model) {
        switch (currentStep) {
            case 1:
                if (model.cover == null)
                    return new ValidationResult(false, R.string.error_missing_cover);
                else if (model.thumbnail == null)
                    return new ValidationResult(false, R.string.error_missing_thumbnail);
                else if (model.arabicBrandName == null || model.arabicBrandName.isEmpty())
                    return new ValidationResult(false, R.string.error_missing_arabic_name);
                else if (model.englishBrandName == null || model.englishBrandName.isEmpty())
                    return new ValidationResult(false, R.string.error_missing_english_name);
                else return new ValidationResult(true, 0);
            case 2:
                if (model.industryId == null)
                    return new ValidationResult(false, R.string.error_missing_industry);
                else if (model.phone == null || model.phone.isEmpty())
                    return new ValidationResult(false, R.string.error_missing_phone);
                else if (model.address == null || model.address.isEmpty())
                    return new ValidationResult(false, R.string.error_missing_address);
                else if (model.email == null || model.email.isEmpty())
                    return new ValidationResult(false, R.string.error_missing_email);
                else if (!Utilities.isEmailValid(model.email))
                    return new ValidationResult(false, R.string.error_invalid_email);
                else if (model.webSite == null || model.webSite.isEmpty())
                    return new ValidationResult(false, R.string.error_missing_website);
                else if (!Utilities.isWebsiteValid(model.webSite))
                    return new ValidationResult(false, R.string.error_invalid_website);
                else
                    return new ValidationResult(true, 0);
            case 3:
                if (model.commercialRecord == null)
                    return new ValidationResult(false, R.string.error_missing_commercial_record);
                else if (model.taxesRecord == null)
                    return new ValidationResult(false, R.string.error_missing_taxes_record);
                else
                    return new ValidationResult(true, 0);
        }
        return null;
    }

    @Override
    public void loadIndustries(Consumer<List<Industry>> consumer, Context context) {
        Disposable disposable = BaseNetworkApi.getAllIndustries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allIndustriesResponse -> {
                    consumer.accept(allIndustriesResponse.industryList);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
        DISPOSABLES.add(disposable);
    }

    @Override
    public void terminate() {
        DISPOSABLES.dispose();
    }

    @Override
    public void setupBrand(SetupBrandModel model, Context context) {
        view.setLoading(true);
//        uploadCover(model, context);
        setupBrand2(model, context);
    }

//    private void uploadCover(SetupBrandModel model, Context context) {
//        Disposable disposable = BaseNetworkApi.uploadBrandDocument(PrefUtils.getBrandToken(context), new File(model.cover), 0)
//                .subscribe(s -> {
//                    uploadThumb(model, context);
//                }, throwable -> {
//                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
//                });
//        DISPOSABLES.add(disposable);
//    }
//
//    private void uploadThumb(SetupBrandModel model, Context context) {
//        Disposable disposable = BaseNetworkApi.uploadBrandDocument(PrefUtils.getBrandToken(context), new File(model.thumbnail), 0)
//                .subscribe(s -> {
//                    uploadCommercialRecord(model, context);
//                }, throwable -> {
//                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
//                });
//        DISPOSABLES.add(disposable);
//    }
//
//    private void uploadCommercialRecord(SetupBrandModel model, Context context) {
//        Disposable disposable = BaseNetworkApi.uploadBrandDocument(PrefUtils.getBrandToken(context), new File(model.commercialRecord), 1)
//                .subscribe(s -> {
//                    uploadTaxesRecord(model, context);
//                }, throwable -> {
//                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
//                });
//        DISPOSABLES.add(disposable);
//    }
//
//    private void uploadTaxesRecord(SetupBrandModel model, Context context) {
//        Disposable disposable = BaseNetworkApi.uploadBrandDocument(PrefUtils.getBrandToken(context), new File(model.taxesRecord), 2)
//                .subscribe(s -> {
//                    setupBrand2(model, context);
//                }, throwable -> {
//                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
//                });
//        DISPOSABLES.add(disposable);
//    }

    private void setupBrand2(SetupBrandModel model, Context context) {
        Disposable disposable = BaseNetworkApi.setupBrand(PrefUtils.getBrandToken(context), model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    view.setLoading(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
        DISPOSABLES.add(disposable);
    }
}
