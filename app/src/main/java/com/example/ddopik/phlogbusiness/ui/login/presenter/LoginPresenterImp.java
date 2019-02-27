package com.example.ddopik.phlogbusiness.ui.login.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.login.model.LoginData;
import com.example.ddopik.phlogbusiness.ui.login.view.LoginView;
import com.jaychang.sa.AuthCallback;
import com.jaychang.sa.SocialUser;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressLint("CheckResult")
public class LoginPresenterImp implements LoginPresenter {

    private LoginView loginView;
    private Context context;

    public LoginPresenterImp(Context context, LoginView loginView) {
        this.loginView = loginView;
        this.context = context;
    }

    private static final String TAG = LoginPresenterImp.class.getSimpleName();

    @Override
    public void signInNormal(HashMap<String, String> loginData) {
        loginView.showLoginProgress(true);
        loginData.put("firebase_token", PrefUtils.getFirebaseToken(context));
        BaseNetworkApi.LoginUserNormal(loginData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    loginView.showLoginProgress(false);
                    saveBrand(loginResponse.getData());
                    sendFirebaseToken();
                }, throwable -> {
                    loginView.showMessage(context.getString(R.string.wrong_credentials));
                    loginView.showLoginProgress(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }

    private void sendFirebaseToken() {
        BaseNetworkApi.updateFirebaseToken(PrefUtils.getBrandToken(context), PrefUtils.getFirebaseToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    loginView.showLoginProgress(false);
                    if (response == null)
                        return;
                    PrefUtils.setFirebaseTokenSentToServer(context, true);
                    if(PrefUtils.isFirstLaunch(context)){
                        loginView.navigateToPickProfilePhoto();
                    }else {
                        loginView.navigateToHome();
                    }
                }, throwable -> {
                    loginView.showLoginProgress(false);
                    PrefUtils.setFirebaseTokenSentToServer(context, false);
                    if(PrefUtils.isFirstLaunch(context)){
                        loginView.navigateToPickProfilePhoto();
                    }else {
                        loginView.navigateToHome();
                    }
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
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

                parameter.put("firebase_token", PrefUtils.getFirebaseToken(context));
                processFaceBookUser(parameter);

            }

            @Override
            public void onError(Throwable error) {
                CustomErrorUtil.Companion.setError(context, TAG, error);
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
                        PrefUtils.setBrandToken(context, socialLoginResponse.token.get(0));
                        loginView.navigateToHome();
                    } else {
                        loginView.showMessage(context.getResources().getString(R.string.error_login));
//                        Log.e(TAG, "processFaceBookUser() Error--->" + socialLoginResponse.state +"  "+ socialLoginResponse.msg) ;
                    }
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }


    private void saveBrand(LoginData loginData) {
        PrefUtils.setLoginState(context, true);
        PrefUtils.setIsFirstLaunch(context, false);
        PrefUtils.setBrandID(context, loginData.id);
        PrefUtils.setBrandToken(context, loginData.token);
        PrefUtils.setBrandHash(context, loginData.hash);
        PrefUtils.setIsBrand(context, loginData.isBrand);
        PrefUtils.setIsPhoneVerified(context, loginData.isPhoneVerified);
        PrefUtils.setBrandStatus(context, loginData.brandStatus);
        PrefUtils.setBrandSetUp(context, loginData.isSetupBrand);
        PrefUtils.setIsMailVerified(context, loginData.isEmailVerified);
        PrefUtils.setBrandText(context, loginData.isBrandText);


    }

    @Override
    public Observable<Boolean> forgotPassword(Context context, String email) {
        return BaseNetworkApi.forgotPassword(email)
                .map(response -> {
                    if (response != null)
                        return true;
                    return false;
                });
    }
}
