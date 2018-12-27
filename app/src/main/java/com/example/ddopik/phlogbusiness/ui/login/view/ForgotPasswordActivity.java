package com.example.ddopik.phlogbusiness.ui.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;


public class ForgotPasswordActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }



    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }
    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }
}
