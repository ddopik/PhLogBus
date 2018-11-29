package com.example.ddopik.phlogbusiness.ui.social.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.ErrorUtils;
import com.example.ddopik.phlogbusiness.Utiltes.PrefUtils;
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
        BaseNetworkApi.getSocialData(PrefUtils.getUserToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socialResponse -> {
                    if (socialResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        socialFragmentView.viewSocialData(socialResponse.data);
                    } else {
                        ErrorUtils.setError(context, TAG, socialResponse.msg, socialResponse.state);
                    }
                    socialFragmentView.viewSocialDataProgress(false);
                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable.getMessage());
                    socialFragmentView.viewSocialDataProgress(false);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void followUser(String userId) {
        BaseNetworkApi.followUser(PrefUtils.getUserToken(context), userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    if (followUserResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        socialFragmentView.showMessage(context.getResources().getString(R.string.following_state) + " " + followUserResponse.data);
                    } else {
                        socialFragmentView.showMessage(context.getResources().getString(R.string.error_following_state));
                    }
                }, throwable -> {
                    Log.e(TAG, "followUser() ---> Error  " + throwable.getMessage());
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void followSocialCampaign(String id) {
        BaseNetworkApi.followCampaign(PrefUtils.getUserToken(context), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followCampaignResponse -> {
                    if (followCampaignResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        socialFragmentView.showMessage(context.getResources().getString(R.string.campaign_followed));
                    } else {
                        ErrorUtils.setError(context, TAG, followCampaignResponse.msg, followCampaignResponse.state);
                    }

                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable.getMessage());
                });
    }
    @SuppressLint("CheckResult")
    @Override
    public void followSocialBrand(String id) {
        BaseNetworkApi.followBrand(PrefUtils.getUserToken(context), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {
                    if (followBrandResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        socialFragmentView.showMessage(context.getResources().getString(R.string.brand_followed));
                    } else {
                        ErrorUtils.setError(context, TAG, followBrandResponse.msg, followBrandResponse.state);
                    }

                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable.getMessage());
                });
    }
}
