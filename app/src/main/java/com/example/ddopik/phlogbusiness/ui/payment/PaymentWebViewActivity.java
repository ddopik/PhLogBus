package com.example.ddopik.phlogbusiness.ui.payment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.ddopik.phlogbusiness.BuildConfig;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;

import javax.crypto.spec.SecretKeySpec;

@SuppressLint("Registered")
public class PaymentWebViewActivity extends BaseActivity {


    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_webview);
        webview = (WebView) findViewById(R.id.payment_webview);
        webView();
    }

    //Metodo llamar el webview
    private void webView() {
        //Habilitar JavaScript (Videos youtube)
        webview.getSettings().setJavaScriptEnabled(true);

        //Handling Page Navigation
        webview.setWebViewClient(new WebViewClient());

        //Load a URL on WebView
//        webview.loadUrl("http://google.com/");
        String url = BaseNetworkApi.PAYMENT_URL + "/" + getEncryptedToken();
        Log.e("url", url);
        if (url != null)
            webview.loadUrl(url);

    }

    private String getEncryptedToken() {
        try {
            return Utilities.encrypt(PrefUtils.getBrandToken(getApplicationContext())
                    , new SecretKeySpec(BuildConfig.PAYMENT_SECRET.getBytes(), "AES")
                    , BuildConfig.PAYMENT_IV_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        return "eyJpdiI6IkcxWDljdFVDXC9DNnJzeEdjSzI0ZHVRPT0iLCJ2YWx1ZSI6Ilo4cm1jeGg4Y0dnaHRQRG15ZlY1ZjlUeVBIYkhwMHRDQ2FBQjRlRzhLVDg9IiwibWFjIjoiNWIxNzk1YzhmNjRmOWZiYzU2MjZmYTk3MjJjNTg0MDQ0NDIwMWU4MWQ0YWYxMjFhNjI5OTg2YzY4ZDM2MGZjMyJ9";
    }

    // Metodo Navigating web page history
    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

//    // Subclase WebViewClient() para Handling Page Navigation
//    private class MyWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
////            if (Uri.parse(url).getHost().equals("google.com")) { //Force to open the url in WEBVIEW
////                return false;
////            }
//
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            startActivity(intent);
//            return true;
//        }
//    }


    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }
}

