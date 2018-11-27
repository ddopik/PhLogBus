package com.example.ddopik.phlogbusiness.Utiltes;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ayman Abouzeid on 6/12/17.
 */

public abstract class PrefUtils {
    public static final String ARABIC_LANG = "ar";
    public static final String ENGLISH_LANG = "en";
    public static final String GUEST_USER_ID = "-1";
    private static final String LOGIN_PROVIDER = "LOGIN_PROVIDER";
    private static final String USER_ID = "USER_ID";
    private static final String USER_TOKEN = "USER_TOKEN";
    private static final String NOTIFICATION_TOKEN = "NOTIFICATION_TOKEN";
    private static final String IS_FIRST_LAUNCH = "IS_FIRST_LAUNCH";
    private static final String IS_LANGUAGE_SELECTED = "IS_LANGUAGE_SELECTED";
    private static final String IS_TOKEN_SAVED = "IS_TOKEN_SAVED";
    private static final String APP_LANG = "APP_LANG";
    private static String PREF_FILE_NAME;

    public PrefUtils() {
        PREF_FILE_NAME = getProjectName();
    }

    private static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
    }

    public static void setUserID(Context context, String userID) {
        getSharedPref(context).edit().putString(USER_ID, userID).apply();
    }

    public static void setUserToken(Context context, String userToken) {
        getSharedPref(context).edit().putString(USER_TOKEN, userToken).apply();
    }

    public static void setNotificationToken(Context context, String notificationToken) {
        getSharedPref(context).edit().putString(NOTIFICATION_TOKEN, notificationToken).apply();
    }

    public static void setIsFirstLaunch(Context context, boolean isFirstLaunch) {
        getSharedPref(context).edit().putBoolean(IS_FIRST_LAUNCH, isFirstLaunch).apply();
    }

    public static String getUserId(Context mContext) {
        return getSharedPref(mContext).getString(USER_ID, GUEST_USER_ID);
    }

    public static String getUserToken(Context mContext) {
//        return getSharedPref(mContext).getString(USER_TOKEN, "");
        return "ac99b777bf0d1e58e8e7cd8653da52f5";
    }

    public static String getNotificationToken(Context mContext) {
        return getSharedPref(mContext).getString(NOTIFICATION_TOKEN, "");
    }

    public static boolean isFirstLaunch(Context context) {
        return getSharedPref(context).getBoolean(IS_FIRST_LAUNCH, true);
    }

    public static String getAppLang(Context context) {
        return getSharedPref(context).getString(APP_LANG, ARABIC_LANG);
    }

    public static void setAppLang(Context context, String appLang) {
        getSharedPref(context).edit().putString(APP_LANG, appLang).apply();
    }

    public static boolean isLanguageSelected(Context context) {
        return getSharedPref(context).getBoolean(IS_LANGUAGE_SELECTED, false);
    }

    public static void setIsLanguageSelected(Context context, boolean isSelected) {
        getSharedPref(context).edit().putBoolean(IS_LANGUAGE_SELECTED, isSelected).apply();
    }

    public static boolean isTokenSaved(Context context) {
        return getSharedPref(context).getBoolean(IS_TOKEN_SAVED, false);
    }

    public static void setLoginState(Context context,boolean state) {
        getSharedPref(context).edit().putBoolean(LOGIN_PROVIDER, state).apply();

    }
    public static boolean isLoginProvided(Context context){
        return getSharedPref(context).getBoolean(LOGIN_PROVIDER, false);
    }

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime) {
        SharedPreferences sharedPreference = context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }

    public static boolean isFirstTimeAskingPermission(Context context, String permission) {
        return context.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE).getBoolean(permission, true);
    }

    public String getProjectName() {
        return PREF_FILE_NAME;
    }

    public abstract void setPrefFileName(String projectName);
}
