
package com.example.ddopik.phlogbusiness.app;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;


import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.example.ddopik.phlogbusiness.network.BasicAuthInterceptor;
import com.example.ddopik.phlogbusiness.realm.RealmConfigFile;
import com.example.ddopik.phlogbusiness.realm.RealmDbMigration;

import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.networkstatus.NetworkChangeBroadcastReceiver;
import com.example.ddopik.phlogbusiness.utiltes.networkstatus.NetworkStateChangeManager;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import okhttp3.OkHttpClient;


/**
 * Created by ddopik Main on 2/28/2017.
 */


public class PhLogBusinessApp extends Application {


    public static Realm realm;
    public static PhLogBusinessApp app;

    private NetworkStateChangeManager networkStateChangeManager;
    private NetworkChangeBroadcastReceiver networkChangeBroadcastReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        this.app = this;
//        MultiDex.install(app);
//        initRealm(); //--> [1]order is must
//        setRealmDefaultConfiguration(); //--> [2]order is must
//        intializeSteatho();
//        deleteCache(app);   ///for developing        ##################
//        initializeDepInj(); ///intializing Dagger Dependancy

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            networkStateChangeManager = new NetworkStateChangeManager(this);
            networkStateChangeManager.listen();
        } else {
            networkChangeBroadcastReceiver = new NetworkChangeBroadcastReceiver();
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(networkChangeBroadcastReceiver, filter);
        }
        String fbtoken = PrefUtils.getFirebaseToken(this);
        Log.e("sd", fbtoken);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (networkStateChangeManager != null)
                networkStateChangeManager.stopListening();
        } else {
            if (networkChangeBroadcastReceiver != null)
                unregisterReceiver(networkChangeBroadcastReceiver);
        }
    }

    /**
     * use this method in case initializing object by --new ()-- keyword
     *
     * @param app app Context
     */
    public static void init(@NonNull final Application app) {
        PhLogBusinessApp.app = (PhLogBusinessApp) app;
    }


    public static PhLogBusinessApp getApp() {
        if (app != null) {
            return app;
        }
        throw new NullPointerException("u should init AppContext  first");
    }

    /**
     * delete App Cache and NetWork Cache
     **/
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    /**
     * initialize Realm Instance this method called after Realm.init(context)
     * Note -->this migrate change of tables if Happened
     *
     * @param isFirstLaunch true as app first Time launched
     * @param realmModules  --->realm module graph represent realm objects ex{@link }
     *                      <p>
     *                      Now you can return Realm instance through App by Calling ----> Realm.getDefaultInstance()
     */
    public static void setRealmDefaultConfiguration(boolean isFirstLaunch, Object realmModules) {
        if (isFirstLaunch) {
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().schemaVersion(RealmConfigFile.REALM_VERSION).migration(new RealmDbMigration()).
                    modules(realmModules).build();
            Realm.setDefaultConfiguration(realmConfiguration);
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
//        MultiDex.install(this);
    }


    public void initRealm() {
        Realm.init(this);
    }


    /**
     * Inspect your Realm Tables through Google Browzer
     */
    public void intializeSteatho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

    }


//    private void initializeDepInj() {
//        if (appComponent == null) {
//            appComponent = DaggerAppComponent.builder()
//                    .mainAppModule(new MainAppModule(this)).build();
////            appComponent.inject(this);  //this App don't Need Any Dependancyes
//        }
//    }


    public static void initFastAndroidNetworking(String userToken, String userType, String lang, Context context) {

/**
 * initializing block to add authentication to your Header Request
 * **/
        if (userToken != null) {

            // enable this for request logging
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            BasicAuthInterceptor basicAuthInterceptor = new BasicAuthInterceptor(context);
            basicAuthInterceptor.setUserToken(userToken);
            basicAuthInterceptor.setUserType(userType);
            basicAuthInterceptor.setLang(lang);
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .addNetworkInterceptor(basicAuthInterceptor)
//                    .addInterceptor(loggingInterceptor)
                    .build();
            AndroidNetworking.initialize(context, okHttpClient);
        } else {
            /**
             * default initialization
             * */
            AndroidNetworking.initialize(context);
        }
    }

}
