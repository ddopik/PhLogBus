package com.example.ddopik.phlogbusiness.ui.login.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.Utiltes.Utilities;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.login.view.LoginView;
import com.jaychang.sa.AuthCallback;
import com.jaychang.sa.SocialUser;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginPresenterImp implements LoginPresenter {

    private LoginView loginView;
    private Context context;

    public LoginPresenterImp(Context context,LoginView loginView) {
        this.loginView = loginView;
        this.context=context;
    }

    private static final String TAG = LoginPresenterImp.class.getSimpleName();

    @SuppressLint("CheckResult")
    @Override
    public void signInNormal(HashMap<String, String> loginData) {
        BaseNetworkApi.LoginUserNormal(loginData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    loginView.showMessage(loginResponse.loginData.fullName);
                }, throwable -> {
                    loginView.showMessage(throwable.getMessage());
                });
    }

    @Override
    public void signInWithGoogle() {
//        GoogleSignInOptions.SCOPE_EMAIL
        List<String> scopes = new ArrayList<String>();
        scopes.add("email");
        scopes.add("profile");
        scopes.add("openid");

        com.jaychang.sa.google.SimpleAuth.connectGoogle(scopes, new AuthCallback() {
            @Override
            public void onSuccess(SocialUser socialUser) {

                Log.e(TAG, "userId:" + socialUser.toString());
//                Log.d(TAG, "email:" + socialUser.email);
            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "signInWithGoogle()--->" + error.getMessage());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "Canceled");
            }
        });

    }

    @Override
    public void signInWithFaceBook() {
        List<String> scopes = new ArrayList<>();
        com.jaychang.sa.facebook.SimpleAuth.connectFacebook(scopes, new AuthCallback() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(SocialUser socialUser) {

                HashMap<String, String> parameter = new HashMap<String, String>();
                parameter.put("userId", socialUser.userId);

                parameter.put("accessToken", socialUser.accessToken);
                 parameter.put("username", socialUser.username);
                parameter.put("pageLink", socialUser.pageLink);

                parameter.put("fullName", socialUser.fullName);
                parameter.put("facebook_id", socialUser.userId);
                parameter.put("mobile_os", "Android");
                parameter.put("mobile_model", Utilities.getDeviceName());
                parameter.put("email", socialUser.email);
                parameter.put("image_profile", socialUser.profilePictureUrl);
                processFaceBookUser(parameter);

            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "signInWithFaceBook() --->" + error.getMessage());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "Canceled");
            }
        });
    }


    @SuppressLint("CheckResult")
    private void processFaceBookUser(HashMap<String, String> data) {
        BaseNetworkApi.socialLoginFacebook(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socialLoginResponse -> {
                    if (socialLoginResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        PrefUtils.setLoginState(context, true);
                        PrefUtils.setUserToken(context, socialLoginResponse.token.get(0));
                        loginView.navigateToHome();
                    } else {
                        loginView.showMessage(context.getResources().getString(R.string.error_login));
//                        Log.e(TAG, "processFaceBookUser() Error--->" + socialLoginResponse.state +"  "+ socialLoginResponse.msg) ;
                    }
                }, throwable -> {
                    Log.e(TAG, "processFaceBookUser() Error--->" + throwable.getMessage());
                });
    }
}
