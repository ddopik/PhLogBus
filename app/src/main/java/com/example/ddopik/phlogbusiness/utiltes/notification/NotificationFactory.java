package com.example.ddopik.phlogbusiness.utiltes.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class NotificationFactory {

    public NotificationFactory() {
    }

    public Notification createNotification(Context context, String channelId, int icon, String title, String content, boolean autoCancel, String groupKey) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(autoCancel)
                .setOnlyAlertOnce(true) // what are those for
                .setSound(null);
        if (icon != 0)
            builder.setSmallIcon(icon);
        if (content != null)
            builder.setContentText(content);
        if (groupKey != null)
            builder.setGroup(groupKey);
        return builder.build();
    }

    public Notification createNotification(Context context, String channelId, int icon, String title, String content
            , boolean autoCancel, String groupKey, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(autoCancel)
                .setOnlyAlertOnce(true) // what are those for
                .setSound(null);
        if (icon != 0)
            builder.setSmallIcon(icon);
        if (content != null)
            builder.setContentText(content);
        if (groupKey != null)
            builder.setGroup(groupKey);
        if (pendingIntent != null)
            builder.setContentIntent(pendingIntent);
        return builder.build();
    }

    public void createNotificationChannel(Context context, String id, String name, String desc) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(desc);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void changeNotificationContent(Context context, String channelId, String notificationId, String content, int icon) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setContentText(content);
        builder.setSmallIcon(icon);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(Integer.valueOf(notificationId), builder.build());
    }

    public void changeNotificationPendingIntent(Context context, String channelId, String notificationId, PendingIntent pendingIntent, int icon) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(icon);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(Integer.valueOf(notificationId), builder.build());
    }

    public void changeNotificationPendingIntent(Context context, String channelId, String notificationId, PendingIntent pendingIntent, String content, int icon) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setContentIntent(pendingIntent);
        builder.setContentText(content);
        builder.setSmallIcon(icon);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(Integer.valueOf(notificationId), builder.build());
    }

    public void showNotification(Context context, int notificationId, Notification notification) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(notificationId, notification);
    }
}
