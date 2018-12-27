package com.example.ddopik.phlogbusiness.ui.login.view;

public interface LoginView {
    void navigateToSignUp();

    void navigateToHome();
    void navigateToPickProfilePhoto();
    void showMessage(String msg);
    void showLoginProgress(boolean state);
}
