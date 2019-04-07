package com.example.ddopik.phlogbusiness.ui.social.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.social.model.SocialResponse;
import com.example.ddopik.phlogbusiness.ui.social.view.SocialFragmentView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import com.google.gson.Gson;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SocailFragmentPresenterImpl implements SocialFragmentPresenter {

    private static String TAG = SocailFragmentPresenterImpl.class.getSimpleName();

    private Context context;
    private SocialFragmentView socialFragmentView;

    public SocailFragmentPresenterImpl(Context context, SocialFragmentView socialFragmentView) {
        this.context = context;
        this.socialFragmentView = socialFragmentView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getSocialData(boolean firstTime) {
        socialFragmentView.viewSocialDataProgress(true);
        BaseNetworkApi.getSocialData(firstTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socialResponse -> {
                    socialFragmentView.viewSocialData(socialResponse.data);
                    socialFragmentView.viewSocialDataProgress(false);


                    if (socialResponse.data.size() > 0) {
                        socialFragmentView.loadMore(true);

                    } else {
                        socialFragmentView.loadMore(false);
                    }

                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    socialFragmentView.viewSocialDataProgress(false);
                });

    }





}
