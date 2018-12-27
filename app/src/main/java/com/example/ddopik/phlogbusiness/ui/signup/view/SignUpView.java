package com.example.ddopik.phlogbusiness.ui.signup.view;

 
import com.example.ddopik.phlogbusiness.ui.signup.model.Country;

import java.util.List;

public interface SignUpView {
    void showCounters(List<Country> countries);
    void showMessage(String msg);
    void pickProfilePhoto();
}
