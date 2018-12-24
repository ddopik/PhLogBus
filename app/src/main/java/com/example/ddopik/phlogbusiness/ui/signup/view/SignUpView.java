package com.example.ddopik.phlogbusiness.ui.signup.view;

 
import com.example.ddopik.phlogbusiness.base.commonmodel.Industry;

import java.util.List;

public interface SignUpView {
    void showIndustries(List<Industry> industries);
    void showMessage(String msg);
    void signUpSuccess();
    void pickProfilePhoto();
}
