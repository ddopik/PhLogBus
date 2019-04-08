package com.example.ddopik.phlogbusiness.ui.notification.view;


import com.example.ddopik.phlogbusiness.ui.notification.model.NotificationData;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface NotificationFragmentView {

    void viewNotificationList(NotificationData notificationData);
    void viewNotificationProgress(boolean state);
    void setNextPageUrl(String page);

}
