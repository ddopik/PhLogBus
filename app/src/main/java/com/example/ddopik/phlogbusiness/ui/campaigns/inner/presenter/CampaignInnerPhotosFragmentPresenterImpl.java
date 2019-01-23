package com.example.ddopik.phlogbusiness.ui.campaigns.inner.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.view.CampaignInnerPhotosFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public class CampaignInnerPhotosFragmentPresenterImpl implements CampaignInnerPhotosFragmentPresenter {


    private String TAG = CampaignInnerPhotosFragmentPresenterImpl.class.getSimpleName();
    private Context context;
    private CampaignInnerPhotosFragmentView campaignInnerPhotosFragmentView;

    public CampaignInnerPhotosFragmentPresenterImpl(Context context, CampaignInnerPhotosFragmentView campaignInnerPhotosFragmentView) {
        this.context = context;
        this.campaignInnerPhotosFragmentView = campaignInnerPhotosFragmentView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getCampaignInnerPhotos(String campaignID, int page) {
        campaignInnerPhotosFragmentView.viewCampaignInnerPhotosProgress(true);
        BaseNetworkApi.getCampaignInnerPhotos(PrefUtils.getBrandToken(context), campaignID, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(campaignInnerPhotosResponse -> {
                    campaignInnerPhotosFragmentView.viewCampaignInnerPhotosProgress(false);
                    if (campaignInnerPhotosResponse == null || campaignInnerPhotosResponse.data == null)
                        return;
                    campaignInnerPhotosFragmentView.addPhotos(campaignInnerPhotosResponse.data);
                }, throwable -> {
                    Log.e(TAG, "getCampaignInnerPhotos() --->" + throwable.getMessage());
                    campaignInnerPhotosFragmentView.viewCampaignInnerPhotosProgress(false);

                });


    }
}
