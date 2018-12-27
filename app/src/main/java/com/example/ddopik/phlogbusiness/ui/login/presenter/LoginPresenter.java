package com.example.ddopik.phlogbusiness.ui.login.presenter;

import java.util.HashMap;

public interface LoginPresenter {


    void signInNormal(HashMap<String, String> loginData);
    void signInWithGoogle();

    void signInWithFaceBook();
}
