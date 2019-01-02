package com.example.ddopik.phlogbusiness.ui.signup.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.Industry;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.ConfirmEmailDialog;
import com.example.ddopik.phlogbusiness.ui.login.view.LoginActivity;
import com.example.ddopik.phlogbusiness.ui.signup.presenter.SignUpPresenter;
import com.example.ddopik.phlogbusiness.ui.signup.presenter.SignUpPresenterImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignUpActivity extends BaseActivity implements SignUpView {

    private String TAG = SignUpActivity.class.getSimpleName();
    private EditText firstName, lastName, mail, registerPassword, mobile;
    private TextInputLayout firstNameInput, lastNameInput, mailInput, registerPasswordInput, mobileInput, industryInput;
    private Button registerCancel, register_signUp;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> industryAdapter;
    private ArrayList<Industry> industryListObj = new ArrayList<>();
    private ArrayList<String> industryList = new ArrayList<>();
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        initPresenter();
        initView();
        initListener();
        signUpPresenter.getAllIndustries();
    }


    @Override
    public void initView() {
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        mail = findViewById(R.id.mail);
        registerPassword = findViewById(R.id.register_password);

        mobile = findViewById(R.id.mobile);
        registerCancel = findViewById(R.id.register_cancel);
        register_signUp = findViewById(R.id.register_sign_up);


        firstNameInput = findViewById(R.id.first_name_input);
        lastNameInput = findViewById(R.id.last_name_input);
        mailInput = findViewById(R.id.mail_input);
        registerPasswordInput = findViewById(R.id.register_password_input);

        mobileInput = findViewById(R.id.mobile_input);
        industryInput = findViewById(R.id.industry_input);
        industryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, industryList);
        autoCompleteTextView = findViewById(R.id.industry);
        autoCompleteTextView.setAdapter(industryAdapter);


    }

    @Override
    public void initPresenter() {
        signUpPresenter = new SignUpPresenterImp(getBaseContext(), this);
    }

    public void initListener() {


        register_signUp.setOnClickListener(view -> {
            if (isDataIsValid()) {


                HashMap<String, String> signUpData = new HashMap<>();

                signUpData.put("first_name", firstName.getText().toString());
                signUpData.put("last_name", lastName.getText().toString());
                signUpData.put("password", registerPassword.getText().toString());
                signUpData.put("email", mail.getText().toString());
                signUpData.put("phone", mobile.getText().toString());

                signUpData.put("industry_id", String.valueOf(getCountryID()));


                signUpData.put("mobile_os", "Android");
                signUpData.put("mobile_model", Utilities.getDeviceName());

                signUpPresenter.signUpUser(signUpData);

            }
        });

        autoCompleteTextView.setOnFocusChangeListener((v, hasFocus) -> {
            autoCompleteTextView.showDropDown();
        });
        registerCancel.setOnClickListener(view -> navigateToLogin());
    }

    private boolean isDataIsValid() {
        List<Boolean> failedStates = new ArrayList<Boolean>(5);

        if (firstName.getText() == null || firstName.getText().toString().equals("")) {
            firstNameInput.setError(getResources().getText(R.string.invailde_name));
            failedStates.add(0, false);
        } else {
            firstNameInput.setErrorEnabled(false);
            failedStates.add(0, true);
        }
        if (lastName.getText().toString().isEmpty()) {
            lastNameInput.setError(getString(R.string.invalid_last_name));
            failedStates.add(1, false);
        } else {
            lastNameInput.setErrorEnabled(false);
            failedStates.add(1, true);
        }
        if (mail.getText().toString().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches()) {
            mailInput.setError(getString(R.string.invalid_mail));
            failedStates.add(2, false);
        } else {
            mailInput.setErrorEnabled(false);
            failedStates.add(2, true);
        }
        if (registerPassword.getText().toString().isEmpty()) {
            registerPasswordInput.setError(getString(R.string.invalid_password));
            failedStates.add(3, false);
        } else if (registerPassword.getText().length() < 6) {
            registerPasswordInput.setError(getString(R.string.password_limit));
            failedStates.add(3, false);
        } else {
            registerPasswordInput.setErrorEnabled(false);
            failedStates.add(3, true);
        }

        if (mobile.getText().toString().equals("") || !android.util.Patterns.PHONE.matcher(mobile.getText()).matches()) {
            mobileInput.setError(getString(R.string.invalid_phone_number));
            failedStates.add(4, false);
        } else {
            mobileInput.setErrorEnabled(false);
            failedStates.add(4, true);
        }


        if (getCountryID() == 0) {
            industryInput.setError(getString(R.string.select_industry_not_exist));
            failedStates.add(5, false);
        } else {
            industryInput.setErrorEnabled(false);
            failedStates.add(5, true);


        }

        /// if one case exists with false state stop view error
        for (int i = 0; i < failedStates.size(); i++) {
            if (!failedStates.get(i)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void showIndustries(List<Industry> industries) {
        this.industryList.clear();
        this.industryListObj.clear();
        this.industryListObj.addAll(industries);
        for (int i = 0; i < industries.size(); i++) {
            this.industryList.add(industries.get(i).nameEn);
        }
        industryAdapter.notifyDataSetChanged();
    }

    @Override
    public void signUpSuccess() {
        ConfirmEmailDialog confirmEmailDialog = new ConfirmEmailDialog(this);
        confirmEmailDialog.show();

    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private int getCountryID() {
        //select industry id by value inserted  from callBack Array
        for (int i = 0; i < industryListObj.size(); i++) {
            if (industryListObj.get(i).nameEn.equals(autoCompleteTextView.getText().toString())) {
                return industryListObj.get(i).id;
            }
        }
        return 0;
    }


    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }
}
