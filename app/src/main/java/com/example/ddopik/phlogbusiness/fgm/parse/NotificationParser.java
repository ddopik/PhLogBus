package com.example.ddopik.phlogbusiness.fgm.parse;

import com.example.ddopik.phlogbusiness.fgm.model.FirebaseNotificationData;
import com.google.gson.Gson;

public class NotificationParser {

    public static FirebaseNotificationData parse (String data) {
        return new Gson().fromJson(data, FirebaseNotificationData.class);
    }
}
