package com.example.ddopik.phlogbusiness.ui.payment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;

@SuppressLint("Registered")
public class PaymentWebViewActivity extends BaseActivity {



    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_webview);
        webview = (WebView)findViewById(R.id.payment_webview);
        webView();
    }

    private void webView(){
        //Habilitar JavaScript (Videos youtube)
        webview.getSettings().setJavaScriptEnabled(true);

        //Handling Page Navigation
        webview.setWebViewClient(new WebViewClient());

        //Load a URL on WebView
//        webview.loadUrl("http://google.com/");
        webview.loadUrl(getResources().getString(R.string.payment_url));

    }

    // Metodo Navigating web page history
    @Override public void onBackPressed() {
        if(webview.canGoBack()) {
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

