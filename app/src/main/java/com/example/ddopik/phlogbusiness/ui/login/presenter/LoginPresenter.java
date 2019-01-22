package com.example.ddopik.phlogbusiness.ui.login.presenter;

import android.content.Context;
import io.reactivex.Observable;

import java.util.HashMap;

public interface LoginPresenter {


    void signInNormal(HashMap<String, String> loginData);
    void signInWithGoogle();

    void signInWithFaceBook();

    Observable<Boolean> forgotPassword(Context context, String email);
}
