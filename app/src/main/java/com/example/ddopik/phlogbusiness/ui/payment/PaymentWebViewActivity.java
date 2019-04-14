package com.example.ddopik.phlogbusiness.ui.payment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import com.example.ddopik.phlogbusiness.BuildConfig;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;

import java.net.URL;
import java.net.URLEncoder;

import javax.crypto.spec.SecretKeySpec;

@SuppressLint("Registered")
public class PaymentWebViewActivity extends BaseActivity {


    private WebView webview;
    private CustomTextView toolBarTitle;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_webview);
        webview = (WebView) findViewById(R.id.payment_webview);
        toolBarTitle=findViewById(R.id.toolbar_title);
        backBtn=findViewById(R.id.back_btn);
        webView();
        initView();

    }

    //Metodo llamar el webview
    private void webView() {
        //Habilitar JavaScript (Videos youtube)
        webview.getSettings().setJavaScriptEnabled(true);

        // for Stripe to work!
        webview.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 9_3 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13E233 Safari/601.1");

        //Handling Page Navigation
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        //Load a URL on WebView
//        webview.loadUrl("http://google.com/");

//        String url = BaseNetworkApi.PAYMENT_URL + "/" + getEncryptedToken();
        String url =
                Uri.parse(BaseNetworkApi.PAYMENT_URL).buildUpon().appendPath(getEncryptedToken()).build().toString();
        Log.e("url", url);
        if (url != null)
            webview.loadUrl(url);

    }

    private String getEncryptedToken() {
        try {
            String encryptedToken = Utilities.encrypt(PrefUtils.getBrandToken(getApplicationContext())
                    , new SecretKeySpec(BuildConfig.PAYMENT_SECRET.getBytes(), "AES")
                    , BuildConfig.PAYMENT_IV_KEY);
            String encodedToken = URLEncoder.encode(encryptedToken, "UTF-8");
//            encodedToken = Uri.encode(encodedToken);
            return encryptedToken;
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


        toolBarTitle.setText(getResources().getString(R.string.my_cart));
        backBtn.setOnClickListener(v->{
            onBackPressed();
        });
    }

    @Override
    public void initPresenter() {


    }
}

