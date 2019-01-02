package com.example.ddopik.phlogbusiness.ui.social.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.social.view.SocialFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SocialFragmentPresenterImpl implements SocialFragmentPresenter {

    private static String TAG = SocialFragmentPresenterImpl.class.getSimpleName();

    private Context context;
    private SocialFragmentView socialFragmentView;

    public SocialFragmentPresenterImpl(Context context, SocialFragmentView socialFragmentView) {
        this.context = context;
        this.socialFragmentView = socialFragmentView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getSocialData() {
        socialFragmentView.viewSocialDataProgress(true);
        BaseNetworkApi.getSocialData(PrefUtils.getBrandToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socialResponse -> {

                        socialFragmentView.viewSocialData(socialResponse.data);

                    socialFragmentView.viewSocialDataProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    socialFragmentView.viewSocialDataProgress(false);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void followUser(String userId) {
        BaseNetworkApi.followUser(PrefUtils.getBrandToken(context), userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    socialFragmentView.showMessage(context.getResources().getString(R.string.following_state) + " " + followUserResponse.data);
                }, throwable -> {
                    Log.e(TAG, "followUser() ---> Error  " + throwable.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void followSocialCampaign(String id) {
        BaseNetworkApi.followCampaign( id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followCampaignResponse -> {
                    socialFragmentView.showMessage(context.getResources().getString(R.string.campaign_followed));
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void followSocialBrand(String id) {
        BaseNetworkApi.followBrand(PrefUtils.getBrandToken(context), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {
                    socialFragmentView.showMessage(context.getResources().getString(R.string.brand_followed));
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable.getMessage());
                });
    }
}
