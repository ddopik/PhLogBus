package com.example.ddopik.phlogbusiness.utiltes.downloader;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.Doc;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandView;
import com.example.ddopik.phlogbusiness.utiltes.notification.NotificationFactory;
import com.example.ddopik.phlogbusiness.utiltes.uploader.UploaderService;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import io.reactivex.disposables.CompositeDisposable;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DownloaderService extends Service {

    private static final String SUB_DIR = "PhlogBusiness";

    public DownloaderService() {
    }

    public static final String TAG = UploaderService.class.getSimpleName();

    public static final int DOWNLOAD_FILE = 0;

    private NotificationFactory notificationFactory = new NotificationFactory();
    private int downloading;

    private Messenger messenger = new Messenger(new Handler(message -> {
        switch (message.what) {
            case DOWNLOAD_FILE:
                if (message.obj instanceof BaseImage) {
                    BaseImage image = (BaseImage) message.obj;
                    downloading ++;
                    File f = new File(Environment.getExternalStorageDirectory(), SUB_DIR);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    String path = f.getAbsolutePath();
                    Rx2AndroidNetworking.download(image.url, path, String.valueOf(new Date().getTime() + ".jpg"))
                            .setPriority(Priority.HIGH)
                            .build()
                            .startDownload(new DownloadListener() {
                                @Override
                                public void onDownloadComplete() {
                                    downloading--;
                                    checkAndStop();
                                }

                                @Override
                                public void onError(ANError anError) {
                                    downloading--;
                                    checkAndStop();
                                }
                            });
                }
                break;
        }
        return true;
    }));

    private void checkAndStop() {
        if (downloading == 0 && bound == 0) {
            stopForeground(false);
            stopSelf();
        }
    }

    private int bound = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        bound++;
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        bound--;
        checkAndStop();
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
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
                , getString(R.string.phlog_downloading)
                , null, false, null);
        startForeground(Integer.valueOf(getString(R.string.permanent_notification_id)), notification);
    }
}
