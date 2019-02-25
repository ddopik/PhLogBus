package com.example.ddopik.phlogbusiness.ui.payment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;

@SuppressLint("Registered")
public class PaymentWebViewActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_webview);
        WebView webView = (WebView) findViewById(R.id.payment_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getResources().getString(R.string.payment_url));
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }
}

