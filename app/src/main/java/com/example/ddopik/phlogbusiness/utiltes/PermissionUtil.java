package com.example.ddopik.phlogbusiness.utiltes;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by abdalla-maged on 3/6/18.
 */
public abstract class PermissionUtil {



    public static final int REQUEST_PERMISSION_SETTING = 101;
    public static final int REQUEST_Muilty_PERMISSION = 111;
    private static boolean firstTimeAsknigPermeation = true;
    private SharedPreferences permissionStatus;
    private Activity activityContext;
    private boolean sentToSettings = false;


    /*
    * Check if version is marshmallow and above.
    * Used in deciding to ask runtime permission
    * */
    public static boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void checkPermission(Context context, String permission, PermissionAskListener listener){
/*
        * If permission is not granted
        * */
        if (shouldAskPermission(context, permission)){
/*
            * If permission denied previously
            * */
            if (((Activity)context).shouldShowRequestPermissionRationale(permission)) {
                listener.onPermissionPreviouslyDenied();
            } else {
                /*
                * Permission denied or first time requested
                * */
                if (PrefUtils.isFirstTimeAskingPermission(context, permission)) {
                    PrefUtils.firstTimeAskingPermission(context, permission, false);
                    listener.onPermissionAsk();
                } else {
                    /*
                    * Handle the feature without permission or ask user to manually allow permission
                    * */
                    listener.onPermissionDisabled();
                }
            }
        } else {
            listener.onPermissionGranted();
        }
    }

    private static boolean shouldAskPermission(Context context, String permission){
        if (shouldAskPermission()) {
            int permissionResult = ActivityCompat.checkSelfPermission(context, permission);
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    public void askSinglePermeation(final String permeation,String message,final int EXTERNAL_STORAGE_PERMISSION_CONSTANT) {

        permissionStatus = activityContext.getSharedPreferences("permissionStatus", MODE_PRIVATE);
// We checked for permission with checkSelfPermission() Method to check if the app already has permission
// If it has then continue to else part,
// otherwise go continue your processes
        if (ActivityCompat.checkSelfPermission(activityContext, permeation) != PackageManager.PERMISSION_GRANTED) {
            ///there was 'No' previous Acceptance permeation --->then we will ask For Permeation
            if (ActivityCompat.shouldShowRequestPermissionRationale(activityContext, permeation)) {
//         This method returns true if the app has requested this permission previously and the user denied the request."
//         method returns false only if the user selected Never ask again or device policy prohibits the app from having that permission:
                AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission."+message);
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(activityContext, new String[]{permeation}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        setExternalPermeation_Cancel_Method().ExternalPermeation_Cancel_Method();
                        /////////pass cancel method
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permeation, false)) {
//              /// ---->user  previously denied
//                requestPermissions()  will do just nothing. So we need to remember whether we ave priorly requested for permission or not
                AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);
                builder.setTitle("Need  Permission");
                builder.setMessage("This app needs  permission."+message);
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", activityContext.getPackageName(), null);
                        intent.setData(uri);
                        activityContext.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        setExternalPermeation_Cancel_Method().ExternalPermeation_Cancel_Method();
                        /////////pass cancel method
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(activityContext, new String[]{permeation}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
            }
            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permeation, true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            Toast.makeText(activityContext, "Permeation already granted", Toast.LENGTH_SHORT).show();
        }

    }

    public void askMuiltyPermeation(List<HashMap<String, String>> permeation, String message) {

        List<String> grantedPermeation = new ArrayList<String>();
        List<String> neverAskAgainDeniedPermeation = new ArrayList<String>();
        List<String> deniedPermeation = new ArrayList<String>();
        permissionStatus = activityContext.getSharedPreferences("permissionStatus", MODE_PRIVATE);



        for (int i = 0; i < permeation.size(); i++) {
            if (ActivityCompat.checkSelfPermission(activityContext, permeation.get(i).get("permeation")) == PackageManager.PERMISSION_GRANTED) {
                grantedPermeation.add(permeation.get(i).get("permeation"));

            } else {

                if (!ActivityCompat.shouldShowRequestPermissionRationale(activityContext, permeation.get(i).get("permeation")) && permissionStatus.getBoolean("firstTimeAskingPermeation",true) ==false) {
                    ///true if user denied it previouly
                    //false if user denied and checked 'never ask again'
                    //false if first time checking permeation

                    neverAskAgainDeniedPermeation.add(permeation.get(i).get("permeation"));
                }
// else if (!permissionStatus.getBoolean(permeation.get(i).get("permation"), false) && ActivityCompat.checkSelfPermission(activityContext, permeation.get(i).get("permation")) == PackageManager.PERMISSION_GRANTED) {
//                    /// ---->user  previously permeation.get(i).get("permation"))
//                    runPermationThroughSitting(new String[]{permeation.get(i).get("permation")}, "NeverAslAgainCollection ---->" + permeation.get(i).get("permation"), Integer.parseInt(permeation.get(i).get("permationCode")));
//                }
                else {
                    deniedPermeation.add(permeation.get(i).get("permeation"));
                }


            }
        }
        if(permissionStatus.getBoolean("firstTimeAskingPermeation",true))
        {
            ActivityCompat.requestPermissions(activityContext, deniedPermeation.toArray(new String[permeation.size()]), REQUEST_Muilty_PERMISSION);
            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean("firstTimeAskingPermeation",false);
            editor.commit();
            return;

        }
        if (deniedPermeation.size() > 0) {
            runNormalPermeationDialog(deniedPermeation.toArray(new String[deniedPermeation.size()]),message, REQUEST_Muilty_PERMISSION);

        }
        if (neverAskAgainDeniedPermeation.size() > 0)
            runPermeationThroughSitting(neverAskAgainDeniedPermeation.toArray(new String[neverAskAgainDeniedPermeation.size()]), message, REQUEST_Muilty_PERMISSION);
    }

    private void runNormalPermeationDialog(final String[] permeation, final String message, final int permeationCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);
        builder.setTitle("Need  Permission");
        builder.setMessage(message);
        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ActivityCompat.requestPermissions(activityContext, permeation, permeationCode);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                setExternalPermeation_Cancel_Method().ExternalPermeation_Cancel_Method();
                /////////pass cancel method
            }
        });
        builder.show();
    }

    private void runPermeationThroughSitting(String[] permation, final String message, final int permeationCode) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);
        builder.setTitle("Need   Permission");
        builder.setMessage(message);
        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                sentToSettings = true;
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activityContext.getPackageName(), null);
                intent.setData(uri);
                activityContext.startActivityForResult(intent, permeationCode);
                Toast.makeText(activityContext, "Go to " + message, Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                setExternalPermeation_Cancel_Method().ExternalPermeation_Cancel_Method();
            }
        });
        builder.show();
    }

    public abstract ExternalPermeation_Cancel_Method setExternalPermeation_Cancel_Method();


    /*
        * Callback on various cases on checking permission
        *
        * 1.  Below M, runtime permission not needed. In that case onPermissionGranted() would be called.
        *     If permission is already granted, onPermissionGranted() would be called.
        *
        * 2.  Above M, if the permission is being asked first time onPermissionAsk() would be called.
        *
        * 3.  Above M, if the permission is previously asked but not granted, onPermissionPreviouslyDenied()
        *     would be called.
        *
        * 4.  Above M, if the permission is disabled by device policy or the user checked "Never ask again"
        *     check box on previous request permission, onPermissionDisabled() would be called.
        * */
    public interface PermissionAskListener {
        /*
                * Callback to ask permission
                * */
        void onPermissionAsk();

        /*
                * Callback on permission denied
                * */
        void onPermissionPreviouslyDenied();

        /*
                * Callback on permission "Never show again" checked and denied
                * */
        void onPermissionDisabled();

        /*
                * Callback on permission granted
                * */
        void onPermissionGranted();
    }


    public interface ExternalPermeation_Cancel_Method {
        public void ExternalPermeation_Cancel_Method();
    }




}