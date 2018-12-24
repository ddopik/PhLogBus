package com.example.ddopik.phlogbusiness.ui.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.ui.MainActivity;
import com.example.ddopik.phlogbusiness.ui.login.presenter.LoginPresenter;
import com.example.ddopik.phlogbusiness.ui.login.presenter.LoginPresenterImp;
import com.example.ddopik.phlogbusiness.ui.signup.view.SignUpActivity;

import java.util.HashMap;

public class LoginActivity extends BaseActivity implements LoginView {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button signUpBtn;
    private TextView mail, passWord, signUpTxt;
    private TextInputLayout mailInput, passwordInput;
    private LoginPresenter loginPresenter;
    private ProgressBar loginProgress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initView();
        initPresenter();
        initListener();

    }

    @Override
    public void initView() {

        signUpTxt = findViewById(R.id.sign_up_txt);
        signUpBtn = findViewById(R.id.sign_up_btn);
        mail = findViewById(R.id.mail);
        passWord = findViewById(R.id.password);
        mailInput = findViewById(R.id.mail_login_input);
        passwordInput = findViewById(R.id.login_password_input);
        loginProgress = findViewById(R.id.login_progress);

    }

    @Override
    public void initPresenter() {

        loginPresenter = new LoginPresenterImp(getBaseContext(), this);

    }

    private void initListener() {


        signUpTxt.setOnClickListener((view) -> navigateToSignUp());
        signUpBtn.setOnClickListener(view -> {
            if (isLoginDataValid()) {
                HashMap<String, String> normalLoginData = new HashMap<String, String>();
                normalLoginData.put("email", mail.getText().toString());
                normalLoginData.put("password", passWord.getText().toString());
                loginPresenter.signInNormal(normalLoginData);
            }
        });
    }


    private boolean isLoginDataValid() {
        if (!mail.getText().toString().isEmpty() && !passWord.getText().toString().isEmpty()) {
            return true;
        }

        if (mail.getText().toString().isEmpty()) {
            mailInput.setError(getResources().getString(R.string.email_missing));
        } else {
            mailInput.setErrorEnabled(false);
        }
        if (passWord.getText().toString().isEmpty()) {
            passwordInput.setError(getResources().getString(R.string.invalid_password));
        } else {
            passwordInput.setErrorEnabled(false);
        }
        return false;
    }


    @Override
    public void navigateToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }


    @Override
    public void showMessage(String msg) {
        super.showToast(msg);
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoginProgress(boolean state) {
        if (state) {
            loginProgress.setVisibility(View.VISIBLE);
        } else {
            loginProgress.setVisibility(View.GONE);
        }
    }
}





