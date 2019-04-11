package com.example.ddopik.phlogbusiness.utiltes.downloader;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.widget.Toast;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.example.ddopik.phlogbusiness.BuildConfig;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.downloads.model.DownloadRequestModel;
import com.example.ddopik.phlogbusiness.utiltes.BitmapUtils;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import com.example.ddopik.phlogbusiness.utiltes.notification.NotificationFactory;
import com.example.ddopik.phlogbusiness.utiltes.uploader.UploaderService;
import com.google.gson.Gson;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.util.Date;

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
                    downloading++;
                    File f = new File(Environment.getExternalStorageDirectory(), SUB_DIR);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    String path = f.getAbsolutePath();
                    DownloadRequestModel model = new DownloadRequestModel(image.id, PrefUtils.getBrandToken(getApplicationContext()));
                    String s = new Gson().toJson(model);
                    String encryptedS = null;
                    try {
                        encryptedS = Utilities.encrypt(s
                                , new SecretKeySpec(BuildConfig.PAYMENT_SECRET.getBytes(), "AES")
                                , BuildConfig.PAYMENT_IV_KEY);
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                    String fileName = String.valueOf(new Date().getTime()) + "." + image.extension;
                    String url = BaseNetworkApi.BASE_IMAGE_DOWNLOAD_URL + "/" + encryptedS;
                    Rx2AndroidNetworking.download(url, path, fileName)
                            .setPriority(Priority.HIGH)
                            .build()
                            .startDownload(new DownloadListener() {
                                @Override
                                public void onDownloadComplete() {
                                    downloading--;
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    Uri uri = FileProvider.getUriForFile(getApplicationContext()
                                            , getApplicationContext().getPackageName() + ".provider", f);
                                    intent.setDataAndType(uri, "*/*");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext()
                                            , (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                                    BitmapUtils.galleryAddPic(getApplicationContext(), path + "/" + fileName);
                                    if (downloading == 0)
                                        notificationFactory.changeNotificationPendingIntent(getApplicationContext()
                                                , getString(R.string.permanent_notification_channel_id)
                                                , getString(R.string.permanent_notification_id), pendingIntent
                                                , getString(R.string.download_complete), R.drawable.phlog_logo);
                                    checkAndStop();
                                    Toast.makeText(getApplicationContext(), R.string.download_complete, Toast.LENGTH_SHORT).show();
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
