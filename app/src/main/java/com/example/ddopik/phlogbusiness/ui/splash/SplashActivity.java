package com.example.ddopik.phlogbusiness.ui.splash;


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
import com.example.ddopik.phlogbusiness.ui.welcome.view.WelcomeActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.Constants.MainActivityRedirectionValue;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;


public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
//        getSupportActionBar().hide();
        new Handler().postDelayed(() -> {
            if (PrefUtils.isLoginProvided(this)) {
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
            } else {
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            finish();
        }, 3000);
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

    }
}
