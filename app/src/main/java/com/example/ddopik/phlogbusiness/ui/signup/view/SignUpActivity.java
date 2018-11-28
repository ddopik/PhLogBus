package com.example.ddopik.phlogbusiness.ui.signup.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.Utilities;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.ui.login.view.LoginActivity;
import com.example.ddopik.phlogbusiness.ui.signup.PickProfilePhotoActivity;
import com.example.ddopik.phlogbusiness.ui.signup.model.Country;
import com.example.ddopik.phlogbusiness.ui.signup.presenter.SignUpPresenter;
import com.example.ddopik.phlogbusiness.ui.signup.presenter.SignUpPresenterImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignUpActivity extends BaseActivity implements SignUpView {

    private String TAG = SignUpActivity.class.getSimpleName();
    private EditText full_name, mail, registerPassword, mobile;
    private TextInputLayout nameInput, userNameInput, mailInput, registerPasswordInput, mobileInput, countryInput;
    private Button registerCancel, register_signUp;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<Country> countryListObj = new ArrayList<>();
    private ArrayList<String> countryList = new ArrayList<>();
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        initPresenter();
        initView();
        initListener();
        signUpPresenter.getAllCounters();
    }


    @Override
    public void initView() {
        full_name = findViewById(R.id.first_name);
//        userName = findViewById(R.id.user_name);
        mail = findViewById(R.id.mail);
        registerPassword = findViewById(R.id.register_password);

        mobile = findViewById(R.id.mobile);
        registerCancel = findViewById(R.id.register_cancel);
        register_signUp = findViewById(R.id.register_sign_up);


        nameInput = findViewById(R.id.first_name_input);
//        userNameInput = findViewById(R.id.user_name_input);
        mailInput = findViewById(R.id.mail_input);
        registerPasswordInput = findViewById(R.id.register_password_input);

        mobileInput = findViewById(R.id.mobile_input);
        countryInput = findViewById(R.id.industry_input);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countryList);
        autoCompleteTextView = findViewById(R.id.industry);
        autoCompleteTextView.setAdapter(arrayAdapter);


    }

    @Override
    public void initPresenter() {
        signUpPresenter = new SignUpPresenterImp(this);
    }

    public void initListener() {
        register_signUp.setOnClickListener(view -> {
            if (isDataIsValid()) {

                HashMap<String, String> signUpData = new HashMap<>();
                signUpData.put("full_name", full_name.getText().toString());
                signUpData.put("password", registerPassword.getText().toString());

                if (!mail.getText().toString().isEmpty()) {
                    signUpData.put("email", mail.getText().toString());
                } else {
                    signUpData.put("email", "");

                }
                if (!mobile.getText().toString().isEmpty()) {
                    signUpData.put("mobile", mobile.getText().toString());
                } else {
                    signUpData.put("mobile", "");

                }

                if (!String.valueOf(getCountryID()).isEmpty()) {
                    signUpData.put("country_id", String.valueOf(getCountryID()));
                }
                {
                    signUpData.put("country_id", "");
                }

//                signUpData.put("User_name", userName.getText().toString());
                signUpData.put("mobile_os", "Android");
                signUpData.put("mobile_model", Utilities.getDeviceName());

                signUpPresenter.signUpUser(signUpData);

            }
        });
        registerCancel.setOnClickListener(view -> navigateToLogin());
    }

    private boolean isDataIsValid() {
        List<Boolean> failedStates = new ArrayList<Boolean>(5);

        if (full_name.getText() == null || full_name.getText().toString().equals("")) {
            nameInput.setError(getResources().getText(R.string.invailde_name));
            failedStates.add(0, false);
        } else {
            nameInput.setErrorEnabled(false);
            failedStates.add(0, true);
        }
//        if (userName.getText().toString().isEmpty()) {
//            userNameInput.setError(getString(R.string.invalid_user_name));
//            failedStates.add(1, false);
//        } else {
//            userNameInput.setErrorEnabled(false);
//            failedStates.add(1, true);
//        }
//        if (mail.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches()) {
//            mailInput.setError(getString(R.string.invalid_mail));
//            failedStates.add(2, false);
//        } else {
//            mailInput.setErrorEnabled(false);
//            failedStates.add(2, true);
//        }
        if (registerPassword.getText().toString().isEmpty()) {
            registerPasswordInput.setError(getString(R.string.invalid_password));
            failedStates.add(1, false);
        } else if (registerPassword.getText().length() < 6) {
            registerPasswordInput.setError(getString(R.string.password_limit));
            failedStates.add(1, false);
        } else {
            registerPasswordInput.setErrorEnabled(false);
            failedStates.add(1, true);
        }

//        if (mobile.getText().toString().equals("") || !android.util.Patterns.PHONE.matcher(mobile.getText()).matches()) {
//            mobileInput.setError(getString(R.string.invalid_phone_number));
//            failedStates.add(5, false);
//        } else {
//            mobileInput.setErrorEnabled(false);
//            failedStates.add(5, true);
//        }


        if (autoCompleteTextView.getText().toString().isEmpty()) {
//            countryInput.setError(getString(R.string.please_select_country));
//            countryInput.setError(getString(R.string.please_select_country));
//            failedStates.add(6, false);
        } else {


            if (getCountryID() == 0) {
                countryInput.setError(getString(R.string.select_country_not_exist));
                failedStates.add(3, false);
            } else {
                countryInput.setErrorEnabled(false);
                failedStates.add(3, true);
            }


        }


        for (int i = 0; i < failedStates.size(); i++) {
            if (!failedStates.get(i)) {
                return false;
            }
        }

        return true;
    }


    @Override
    public void showCounters(List<Country> countries) {
        this.countryList.clear();
        this.countryListObj.clear();
        this.countryListObj.addAll(countries);
        for (int i = 0; i < countries.size(); i++) {
            countryList.add(countries.get(i).nameEn);
        }
        arrayAdapter.notifyDataSetChanged();
    }
    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private int getCountryID() {
        for (int i = 0; i < countryListObj.size(); i++) {
            if (countryListObj.get(i).nameEn.equals(autoCompleteTextView.getText().toString())) {
                return countryListObj.get(i).id;
            }
        }
        return 0;
    }

    @Override
    public void pickProfilePhoto() {
        Intent intent = new Intent(this, PickProfilePhotoActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }
}
