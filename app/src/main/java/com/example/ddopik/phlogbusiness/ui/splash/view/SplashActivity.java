package com.example.ddopik.phlogbusiness.ui.splash.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.ui.MainActivity;
import com.example.ddopik.phlogbusiness.ui.dialog.appupdate.AppUpdateDialogFragment;
import com.example.ddopik.phlogbusiness.ui.login.view.LoginActivity;
import com.example.ddopik.phlogbusiness.ui.splash.model.CheckVersionData;
import com.example.ddopik.phlogbusiness.ui.splash.presenter.SplashPresenter;
import com.example.ddopik.phlogbusiness.ui.splash.presenter.SplashPresenterImpl;
import com.example.ddopik.phlogbusiness.utiltes.Constants.MainActivityRedirectionValue;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SplashActivity extends BaseActivity implements SplashView {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private SplashPresenter presenter;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initPresenter();
        initView();
//        getSupportActionBar().hide();
        new Handler().postDelayed(() -> {
            Disposable d = presenter.checkAppVersion()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(checkVersionReponse -> {
                        if (checkVersionReponse == null || checkVersionReponse.getData() == null)
                            return;
                        boolean showSkip = false;
                        switch (checkVersionReponse.getData().getStatus()) {
                            case CheckVersionData.STATUS_NO_UPDATES:
                                checkLoginStatus();
                                break;
                            case CheckVersionData.STATUS_OPTIONAL_UPDATE:
                                showSkip = true;
                                showUpdateDialog(checkVersionReponse.getData(), showSkip);
                                break;
                            case CheckVersionData.STATUS_REQUIRED_UPDATES:
                                showSkip = false;
                                showUpdateDialog(checkVersionReponse.getData(), showSkip);
                                break;
                        }
                    }, throwable -> {
                        CustomErrorUtil.Companion.setError(this, TAG, throwable);
                    });
            disposables.add(d);
        }, 3000);
    }

    private void showUpdateDialog(CheckVersionData data, boolean showSkip) {
        AppUpdateDialogFragment.newInstance(data, showSkip, () -> {
            finish();
            // TODO: redirect to app store
        }, this::checkLoginStatus).show(getSupportFragmentManager(), null);
    }

    private void checkLoginStatus() {
        if (PrefUtils.isLoginProvided(this)) {
            if (PrefUtils.isFirebaseTokenSentToServer(this))
                goToMain();
            else {
                sendFirebaseToken();
            }
        } else {
            goToWelcome();
        }
    }

    private void sendFirebaseToken() {
        Disposable d = presenter.sendFirebaseToken(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                    goToMain();
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(this, TAG, throwable);
                    goToMain();
                });
        disposables.add(d);
    }

    private void goToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String payload = bundle.getString("data");
            if (payload != null) {
                intent.putExtra(MainActivityRedirectionValue.VALUE, MainActivityRedirectionValue.TO_POPUP);
                intent.putExtra(MainActivityRedirectionValue.PAYLOAD, payload);
            }
        }
        startActivity(intent);
    }

    private void goToWelcome() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public void initView() {


//        RotateAnimation anim = new RotateAnimation(0f, 360f, 0f, 10f);
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(1200);


        final ImageView splash = findViewById(R.id.app_logo);
        splash.startAnimation(anim);


//        splash.setAnimation(null);


    }

    @Override
    public void initPresenter() {
        presenter = new SplashPresenterImpl();
        presenter.setView(this);
    }

    @Override
    protected void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }
}
