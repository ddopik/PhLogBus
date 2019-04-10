package com.example.ddopik.phlogbusiness.ui.accountdetails.presenter;

import android.content.Context;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.accountdetails.model.AccountDetailsModel;
import com.example.ddopik.phlogbusiness.ui.accountdetails.view.AccountDetailsView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.HashMap;

public class AccountDetailsPresenterImpl implements AccountDetailsPresenter {

    public static final String TAG = AccountDetailsPresenterImpl.class.getSimpleName();

    private AccountDetailsView view;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void setView(AccountDetailsView view) {
        this.view = view;
    }

    @Override
    public ValidationResult validate(AccountDetailsModel model) {
        if (model.getFirstName() == null || model.getFirstName().isEmpty())
            return new ValidationResult(false, R.string.error_missing_name);
        else if (model.getLastName() == null || model.getLastName().isEmpty())
            return new ValidationResult(false, R.string.error_missing_last_name);
        else if (model.getEmail() == null || model.getEmail().isEmpty())
            return new ValidationResult(false, R.string.error_missing_email);
        else if (!Utilities.isEmailValid(model.getEmail()))
            return new ValidationResult(false, R.string.error_invalid_email);
//        else if (model.getPassword() == null || model.getPassword().isEmpty())
//            return new ValidationResult(false, R.string.error_missing_password);
        else
            return new ValidationResult(true, 0);
    }

    @Override
    public void saveProfile(Context context, AccountDetailsModel model) {
        view.setLoading(true);
        Disposable disposable = BaseNetworkApi.updateProfile(PrefUtils.getBrandToken(context), model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    view.setLoading(false);
                    if (response != null) {
                        view.updateSuccess(true);
                    }
                }, throwable -> {
                    view.setLoading(false);
                    view.updateSuccess(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
        disposables.add(disposable);
    }

    @Override
    public void changePassword(Context context, String oldPassword, String newPassword) {
        view.setLoading(true);
        HashMap<String, String> data = new HashMap<>();
        if (newPassword != null) {
            data.put("password", newPassword);
        }
        if (oldPassword != null) {
            data.put("old_password", oldPassword);
        }

        Disposable disposable = BaseNetworkApi.updateProfile(PrefUtils.getBrandToken(context),data )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> view.setLoading(false))
                .subscribe(s -> {
                    view.setLoading(false);
//                    if (s != null) {
//                        view.showMessage(R.string.password_changed);
//                    }
                }, throwable -> {
                    view.setLoading(false);
                    if (context != null)
                        CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
        disposables.add(disposable);
    }

    @Override
    public void terminate() {
        disposables.dispose();
    }
}
