package com.example.ddopik.phlogbusiness.fgm.parse;

import com.example.ddopik.phlogbusiness.ui.notification.model.NotificationList;
import com.google.gson.Gson;

public class NotificationParser {

    public static NotificationList parse (String data) {
        return new Gson().fromJson(data, NotificationList.class);
    }
}
