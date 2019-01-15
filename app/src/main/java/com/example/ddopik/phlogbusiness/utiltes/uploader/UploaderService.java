package com.example.ddopik.phlogbusiness.utiltes.uploader;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.Doc;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.notification.NotificationFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class UploaderService extends Service {

    public static final String TAG = UploaderService.class.getSimpleName();

    public static final int ADD_COMMUNICATOR = 0;
    public static final int REMOVE_COMMUNICATOR = 1;
    public static final int UPLOAD_FILE = 2;

    private NotificationFactory notificationFactory = new NotificationFactory();
    private CompositeDisposable disposables = new CompositeDisposable();
    private List<SetupBrandView.Communicator> communicators = new ArrayList<>();
    private int uploading;

    private Messenger messenger = new Messenger(new Handler(message -> {
        switch (message.what) {
            case ADD_COMMUNICATOR:
                if (message.obj instanceof SetupBrandView.Communicator) {
                    SetupBrandView.Communicator c = (SetupBrandView.Communicator) message.obj;
                    communicators.add(c);
                }
                break;
            case REMOVE_COMMUNICATOR:
                if (message.obj instanceof SetupBrandView.Communicator) {
                    SetupBrandView.Communicator c = (SetupBrandView.Communicator) message.obj;
                    communicators.remove(c);
                }
                break;
            case UPLOAD_FILE:
                if (message.obj instanceof Doc) {
                    uploading++;
                    Doc doc = (Doc) message.obj;
                    Disposable disposable = BaseNetworkApi.uploadBrandDocument(PrefUtils.getBrandToken(getApplicationContext()), "" + doc.getId(), new File(doc.path), (bytesUploaded, totalBytes) -> {
                        doc.progress = (int) (bytesUploaded / (float) totalBytes * 100);
                        notifyCommunicatorsWithProgress(doc);
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(s -> {
                                uploading--;
                                notifyCommunicatorsWithDone(doc);
                            }, throwable -> {
                                uploading--;
                                notifyCommunicatorsWithError(doc);
                                checkAndStop();
                                Log.e(TAG, throwable.toString());
                                try {
                                    CustomErrorUtil.Companion.setError(getApplicationContext(), TAG, throwable);
                                } catch (Exception e) {
                                    Log.e(TAG, e.getMessage());
                                }
                            });
                    disposables.add(disposable);
                }
                break;
        }
        return true;
    }));

    private void checkAndStop() {
        if (uploading == 0 && bound == 0) {
            stopForeground(false);
            stopSelf();
        }
    }

    private void notifyCommunicatorsWithProgress(Doc doc) {
        for (SetupBrandView.Communicator communicator : communicators)
            communicator.handle(SetupBrandView.Communicator.Type.PROGRESS, doc);
    }

    private void notifyCommunicatorsWithDone(Doc doc) {
        for (SetupBrandView.Communicator communicator : communicators)
            communicator.handle(SetupBrandView.Communicator.Type.DONE, doc);
    }

    private void notifyCommunicatorsWithError(Doc doc) {
        for (SetupBrandView.Communicator communicator : communicators)
            communicator.handle(SetupBrandView.Communicator.Type.ERROR, doc);
    }

    public UploaderService() {
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
                , getString(R.string.phlog_uploading)
                , null, false, null);
        startForeground(Integer.valueOf(getString(R.string.permanent_notification_id)), notification);
        PrefUtils.setIsUploading(getApplicationContext(), true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
        PrefUtils.setIsUploading(getApplicationContext(), false);
    }
}
