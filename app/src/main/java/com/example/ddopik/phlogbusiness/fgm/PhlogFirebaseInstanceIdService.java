package com.example.ddopik.phlogbusiness.fgm;

import android.util.Log;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public class PhlogFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("Firebase token", token);
        PrefUtils.saveFirebaseToken(getApplicationContext(), token);
    }
}
