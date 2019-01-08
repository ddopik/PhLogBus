package com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.view.AddToLightBoxDialogFragmentView;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddNewLightBoxDialogPresenterImpl implements AddNewLightBoxDialogPresenter{

    private String TAG=AddNewLightBoxDialogPresenterImpl.class.getSimpleName();
    private Context context;
    private AddToLightBoxDialogFragmentView addToLightBoxDialogFragmentView;

    public AddNewLightBoxDialogPresenterImpl(Context context,AddToLightBoxDialogFragmentView addToLightBoxDialogFragmentView){
        this.context=context;
        this.addToLightBoxDialogFragmentView=addToLightBoxDialogFragmentView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getLightBoxes() {
        addToLightBoxDialogFragmentView.viewLightBoxProgress(true);
        BaseNetworkApi.getLightBoxes().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addToLightBoxResponse -> {
                    addToLightBoxDialogFragmentView.viewLightBoxes(addToLightBoxResponse.data);
                    addToLightBoxDialogFragmentView.viewLightBoxProgress(false);
                }, throwable -> {

                    CustomErrorUtil.Companion.setError(context,TAG,throwable);
                    addToLightBoxDialogFragmentView.viewLightBoxProgress(false);
                });
    }
}
