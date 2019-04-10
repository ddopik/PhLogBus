package com.example.ddopik.phlogbusiness.ui.setupbrand.presenter;

import android.content.Context;
import android.util.Log;

import android.util.Patterns;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Industry;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.Doc;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.SetupBrandModel;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SetupBrandPresenterImpl implements SetupBrandPresenter {

    private CompositeDisposable disposables = new CompositeDisposable();

    public static final String TAG = SetupBrandPresenterImpl.class.getSimpleName();

    public static final String SAVED = "Saved";

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
                else
                    return new ValidationResult(true, 0);
            case 2:
                if (model.industryId == null)
                    return new ValidationResult(false, R.string.error_missing_industry);
                else if (model.phone == null || model.phone.isEmpty())
                    return new ValidationResult(false, R.string.error_missing_phone);
                else if (!Patterns.PHONE.matcher(model.phone).matches())
                    return new ValidationResult(false, R.string.error_invalid_phone);
                else if (model.address == null || model.address.isEmpty())
                    return new ValidationResult(false, R.string.error_missing_address);
                else if (model.email == null || model.email.isEmpty())
                    return new ValidationResult(false, R.string.error_missing_email);
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(model.email).matches())
                    return new ValidationResult(false, R.string.error_invalid_email);
                else if (model.webSite == null || model.webSite.isEmpty())
                    return new ValidationResult(false, R.string.error_missing_website);
                else if (!Patterns.WEB_URL.matcher(model.webSite).matches())
                    return new ValidationResult(false, R.string.error_invalid_website);
                else if (model.desc == null || model.desc.isEmpty())
                    return new ValidationResult(false, R.string.error_missing_desc);
                else
                    return new ValidationResult(true, 0);
            case 3:
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
        disposables.add(disposable);
    }

    @Override
    public void terminate() {
        disposables.dispose();
    }

    @Override
    public void setupBrand(SetupBrandModel model, Context context, Consumer<Boolean> consumer) {
        view.setLoading(true);
        Disposable disposable = BaseNetworkApi.setupBrand(PrefUtils.getBrandToken(context), model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    view.setLoading(false);
                    consumer.accept(true);
                }, throwable -> {
                    view.setLoading(false);
                    consumer.accept(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
        disposables.add(disposable);
    }

    @Override
    public void loadDocs(Consumer<List<Doc>> object, Context baseContext) {
        view.setLoading(true);
        Disposable disposable = BaseNetworkApi.getDocumentList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null && response.getData() != null)
                        object.accept(response.getData());
                    view.setLoading(false);
                }, throwable -> {
                    view.setLoading(false);
                    Log.e(TAG, throwable.getMessage());
                });
        disposables.add(disposable);
    }

    @Override
    public void verify(Context context, Consumer<Boolean> success) {
        view.setLoading(true);
        Disposable disposable = BaseNetworkApi.requestVerificationBrand()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> view.setLoading(false))
                .subscribe(response -> {
                    if (response.getMsg() != null && response.getMsg().equals(SAVED)) {
                        success.accept(true);
                    } else {
                        success.accept(false);
                    }
                }, throwable -> {
                    success.accept(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
        disposables.add(disposable);
    }
}
