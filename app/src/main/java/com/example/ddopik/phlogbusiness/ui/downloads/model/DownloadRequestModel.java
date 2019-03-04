package com.example.ddopik.phlogbusiness.ui.downloads.model;

import android.util.Log;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class DownloadRequestModel {

    @SerializedName("p")
    public final String id;

    @SerializedName("t")
    public final String date;

    @SerializedName("bs")
    public final String token;

    public DownloadRequestModel(int id, String token) {
        this.id = String.valueOf(id);
        this.token = token;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm Z", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.date = dateFormat.format(calendar.getTime());
        Log.e("date", date);
    }
}
