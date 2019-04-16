package com.example.ddopik.phlogbusiness.fgm;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.fgm.model.FirebaseNotificationData;
import com.example.ddopik.phlogbusiness.fgm.parse.NotificationParser;
import com.example.ddopik.phlogbusiness.ui.notification.model.NotificationList;
import com.example.ddopik.phlogbusiness.ui.splash.view.SplashActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.notification.NotificationFactory;
import com.example.ddopik.phlogbusiness.utiltes.rxeventbus.RxEvent;
import com.example.ddopik.phlogbusiness.utiltes.rxeventbus.RxEventBus;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PhlogFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        NotificationList data = NotificationParser.parse(remoteMessage.getData().get("data"));

        if (data != null && data.popup == Constants.PopupType.NONE) {
            Notification notification = null;
            NotificationFactory factory = new NotificationFactory();
            factory.createNotificationChannel(this
                    , getString(R.string.normal_notification_channel_id)
                    , getString(R.string.normal_notification_channel_name)
                    , getString(R.string.normal_notification_channel_desc));

            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("data", remoteMessage.getData().get("data"));
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext()
                    , (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);

            notification = factory.createNotification(this
                    , getString(R.string.normal_notification_channel_id)
                    , R.drawable.phlog_logo, getString(R.string.phlog), data.message
                    , true, null, pendingIntent);

            factory.showNotification(this, Integer.valueOf(getString(R.string.normal_notification_channel_id)), notification);
        } else
            RxEventBus.getInstance().post(new RxEvent<>(RxEvent.Type.POPUP, data));
    }
}
