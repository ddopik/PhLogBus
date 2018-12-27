package com.example.ddopik.phlogbusiness.ui.notification.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.Utiltes.ErrorUtil;
import com.example.ddopik.phlogbusiness.Utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.notification.view.NotificationFragmentView;

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
                    notificationFragmentView.viewNotificationList(notificationResponse.data);
                    notificationFragmentView.viewNotificationProgress(false);

                }, throwable -> {
                    ErrorUtil.Companion.setError(context, TAG, throwable.toString());
                    notificationFragmentView.viewNotificationProgress(false);
                });
    }
    // ErrorUtil.setError(context, TAG, albumPreviewResponse.msg, albumPreviewResponse.state);
// ErrorUtil.setError(context, TAG, throwable.toString());
}
