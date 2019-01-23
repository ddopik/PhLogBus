package com.example.ddopik.phlogbusiness.ui.campaigns.inner.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.view.CampaignInnerSettingsView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CampaignInnerSettingPresenterImpl implements CampaignInnerSettingPresenter {

    public static final String TAG = CampaignInnerSettingPresenterImpl.class.getSimpleName();
    private static final String SAVED = "Saved";

    private CompositeDisposable disposables = new CompositeDisposable();

    private CampaignInnerSettingsView view;

    @Override
    public void setView(CampaignInnerSettingsView view) {
        this.view = view;
    }

    @Override
    public void setEndDate(Context context, Integer id, String dateString) {
        Disposable disposable = BaseNetworkApi.changeCampaignEndDate(id, dateString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(changeCampaignDateResponse -> {
                    if (changeCampaignDateResponse.getMsg() != null && changeCampaignDateResponse.getMsg().equals(SAVED)) {
                        view.showMessage(R.string.campaign_extended);
                    } else {
                        view.showMessage(R.string.campaign_extended_fail);
                    }
                }, throwable -> {
                    view.showMessage(R.string.campaign_extended_fail);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
        disposables.add(disposable);
    }
}
