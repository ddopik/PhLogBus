package com.example.ddopik.phlogbusiness.fgm;

import com.example.ddopik.phlogbusiness.fgm.model.FirebaseNotificationData;
import com.example.ddopik.phlogbusiness.fgm.parse.NotificationParser;
import com.example.ddopik.phlogbusiness.utiltes.rxeventbus.RxEvent;
import com.example.ddopik.phlogbusiness.utiltes.rxeventbus.RxEventBus;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PhlogFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        FirebaseNotificationData data = NotificationParser.parse(remoteMessage.getData().get("data"));
        RxEventBus.getInstance().post(new RxEvent<>(RxEvent.Type.POPUP, data));
    }
}
