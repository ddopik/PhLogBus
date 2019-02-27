package com.example.ddopik.phlogbusiness.ui.welcome.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import com.bikomobile.circleindicatorpager.CircleIndicatorPager;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.ui.login.view.LoginActivity;
import com.example.ddopik.phlogbusiness.ui.signup.view.SignUpActivity;
import com.example.ddopik.phlogbusiness.ui.welcome.model.InitSlider;
import com.example.ddopik.phlogbusiness.ui.welcome.presenter.WelcomePresenter;
import com.example.ddopik.phlogbusiness.ui.welcome.presenter.WelcomeScreenImpl;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends BaseActivity implements WelcomeView {


    private static final String TAG = WelcomeActivity.class.getSimpleName();


    private WelcomeSlideAdapter WelcomeSlideAdapter;
    private List<InitSlider> urlList = new ArrayList<InitSlider>();
    private WelcomePresenter welcomePresenter;
    private Button signInBtn, signUpBtn;
    private CircleIndicatorPager indicator;
    private ViewPager slidesViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        initListener();
        initPresenter();
//        welcomePresenter.getWelcomeSlidesImages();
    }

    @Override
    public void initView() {


        signInBtn = findViewById(R.id.sign_in_btn);
        signUpBtn = findViewById(R.id.sign_up_btn);
//        InitSlider initSliderDefaultItem = new InitSlider();
//        initSliderDefaultItem.image = "";
//        initSliderDefaultItem.text = "basic Image";
//        urlList.add(initSliderDefaultItem);
//        WelcomeSlideAdapter = new WelcomeSlideAdapter(getBaseContext(), urlList);
//        slidesViewPager = findViewById(R.id.slides_view_pager);
//        slidesViewPager.setAdapter(WelcomeSlideAdapter);
//        indicator = findViewById(R.id.circle_indicator_pager);


    }


    @Override
    public void initPresenter() {
        welcomePresenter = new WelcomeScreenImpl(this, getBaseContext());
    }

    private void initListener() {
        signInBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
        signUpBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }

//    @Override
//    public void showWelcomeImageSlider(List<InitSlider> images) {
//        urlList.clear();
//        InitSlider initSliderDefaultItem = new InitSlider();
//        initSliderDefaultItem.image = "";
//        initSliderDefaultItem.text = "basic Image";
//        urlList.add(initSliderDefaultItem);
//        urlList.addAll(images);
//        WelcomeSlideAdapter.notifyDataSetChanged();
//        indicator.setViewPager(slidesViewPager);
//    }


}
