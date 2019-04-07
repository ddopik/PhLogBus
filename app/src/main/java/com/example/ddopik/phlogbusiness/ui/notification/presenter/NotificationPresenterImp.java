package com.example.ddopik.phlogbusiness.ui.notification.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.notification.view.NotificationFragmentView;

import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class NotificationPresenterImp implements NotificationPresenter {
    private String TAG = NotificationPresenterImp.class.getSimpleName();
    private Context context;
    private NotificationFragmentView notificationFragmentView;

    public NotificationPresenterImp(Context context, NotificationFragmentView notificationFragmentView) {
        this.context = context;
        this.notificationFragmentView = notificationFragmentView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getNotification(String page) {
        notificationFragmentView.viewNotificationProgress(true);
        BaseNetworkApi.getNotification(PrefUtils.getBrandToken(context), page)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(notificationResponse -> {
                    notificationFragmentView.viewNotificationList(notificationResponse.notificationData);
                    notificationFragmentView.viewNotificationProgress(false);
                    if (notificationResponse.notificationData.nextPageUrl != null) {
                        notificationFragmentView.setNextPageUrl(Utilities.getNextPageNumber(context, notificationResponse.notificationData.nextPageUrl));

                    } else {
                        notificationFragmentView.setNextPageUrl(null);
                    }


                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable.toString());
                    notificationFragmentView.viewNotificationProgress(false);
                });
    }
    // CustomErrorUtil.setError(context, TAG, albumPreviewResponse.msg, albumPreviewResponse.state);
// CustomErrorUtil.setError(context, TAG, throwable.toString());
}
