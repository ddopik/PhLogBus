package com.example.ddopik.phlogbusiness.Utiltes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class ErrorUtils {

    private static String TAG = ErrorUtils.class.getSimpleName();

    public ErrorUtils() {

    }

    public static void setError(Context context, String contextTAG, String error, String stateCode) {

        Log.e(TAG, contextTAG +  "------>" + error);
        Toast.makeText(context, "Request error", Toast.LENGTH_SHORT).show();
    }

    public static void setError(Context context, String contextTAG, String error) {
        Log.e(TAG, contextTAG +   "------>" + error);
    }
}
// ErrorUtils.setError(context, TAG, albumPreviewResponse.msg, albumPreviewResponse.state);
// ErrorUtils.setError(context, TAG, throwable.toString());