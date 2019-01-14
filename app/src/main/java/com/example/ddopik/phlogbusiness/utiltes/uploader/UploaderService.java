package com.example.ddopik.phlogbusiness.utiltes.uploader;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.notification.NotificationFactory;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class UploaderService extends Service {

    public static final String TAG = UploaderService.class.getSimpleName();

    private NotificationFactory notificationFactory = new NotificationFactory();
    private CompositeDisposable disposables = new CompositeDisposable();

    public UploaderService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            handleIntent(intent);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationFactory.createNotificationChannel(getApplicationContext()
                , getString(R.string.permanent_notification_channel_id)
                , getString(R.string.permanent_notification_channel_name)
                , getString(R.string.permanent_notification_channel_desc));
        Notification notification = notificationFactory.createNotification(getApplicationContext()
                , getString(R.string.permanent_notification_channel_id)
                , R.drawable.phlog_logo
                , getString(R.string.phlog_uploading)
                , null, false, null);
        startForeground(Integer.valueOf(getString(R.string.permanent_notification_id)), notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }

    private void handleIntent(Intent intent) throws Exception {
        Serializable s = intent.getSerializableExtra("map");
        if (s instanceof HashMap) {
            HashMap<Integer, String> map = (HashMap<Integer, String>) s;
            Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
            if (iterator.hasNext()) {
                Action action = new Action() {
                    @Override
                    public void run() throws Exception {
                        if (iterator.hasNext())
                            upload(iterator.next(), this);
                        else {
                            notificationFactory.changeNotificationContent(getApplicationContext()
                                    , getString(R.string.permanent_notification_id)
                                    , getString(R.string.uplaod_done));
                            stopForeground(false);
                            stopSelf();
                        }
                    }
                };
                action.run();
            }
        }
    }

    private void upload(Map.Entry<Integer, String> entry, Action action) {
        Disposable disposable = BaseNetworkApi.uploadBrandDocument(PrefUtils.getBrandToken(getApplicationContext()), entry.getKey(), new File(entry.getValue()))
                .subscribe(s -> {
                    Log.e(TAG, "response: " + s);
                    action.run();
                }, throwable -> {
                    Log.e(TAG, throwable.toString());
                    try {
                        CustomErrorUtil.Companion.setError(getApplicationContext(), TAG, throwable);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                });
        disposables.add(disposable);
    }
}
