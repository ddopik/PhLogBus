package com.example.ddopik.phlogbusiness.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.app.PhLogBusinessApp;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.base.commonmodel.LightBox;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.fgm.model.FirebaseNotificationData;
import com.example.ddopik.phlogbusiness.fgm.parse.NotificationParser;
import com.example.ddopik.phlogbusiness.ui.accountdetails.view.AccountDetailsFragment;
import com.example.ddopik.phlogbusiness.ui.customersupport.view.CustomerSupportFragment;
import com.example.ddopik.phlogbusiness.ui.downloads.model.DownloadRequestModel;
import com.example.ddopik.phlogbusiness.ui.downloads.view.DownloadsFragment;
import com.example.ddopik.phlogbusiness.ui.lightboxphotos.view.LightBoxPhotosFragment;
import com.example.ddopik.phlogbusiness.ui.notification.view.NotificationFragment;
import com.example.ddopik.phlogbusiness.ui.splash.view.SplashActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.campaigns.view.CampaignsFragment;
import com.example.ddopik.phlogbusiness.ui.lightbox.view.BrandLightBoxFragment;
import com.example.ddopik.phlogbusiness.ui.profile.view.BusinessProfileFragment;
import com.example.ddopik.phlogbusiness.ui.social.view.SocialFragment;
import com.example.ddopik.phlogbusiness.utiltes.Constants.MainActivityRedirectionValue;
import com.example.ddopik.phlogbusiness.utiltes.Constants.PopupType;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;

import static com.example.ddopik.phlogbusiness.utiltes.Constants.NavigationHelper.*;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private CustomTextView toolBarTitle;
    private TextView callSupportBtn;
    private BottomAppBar bottomNavigation;
    private Button homeBrn, campaignBtn, myProfileBtn, downloadsBtn;
    private FloatingActionButton callSupportFloatBtn;
    private Toolbar toolbar;
    private ImageButton backBtn;
    public static NavigationManger navigationManger;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.phlog_business_main_toolbar);
        super.setSupportActionBar(toolbar);
        navigationManger = new NavigationManger();
        initView();
        initPresenter();
        initListener();
        navigationManger.navigate(HOME);

        handleIntent(getIntent());

//        Log.e("token", PrefUtils.getBrandToken(this));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent.hasExtra(MainActivityRedirectionValue.VALUE)) {
            int redirection = intent.getIntExtra(MainActivityRedirectionValue.VALUE, 0);
            switch (redirection) {
                case MainActivityRedirectionValue.TO_PROFILE:
                    navigationManger.navigate(PROFILE);
                    break;
                case MainActivityRedirectionValue.TO_POPUP:
                    String payload = intent.getStringExtra(MainActivityRedirectionValue.PAYLOAD);
                    FirebaseNotificationData data = NotificationParser.parse(payload);
                    navigationManger.navigate(HOME);
                    if (data != null && data.notification.popup != PopupType.NONE)
                        showPopup(data);
                    break;
                case MainActivityRedirectionValue.TO_CAMPAIGNS:
                    navigationManger.navigate(CAMPAIGN);
                    break;
            }
        } else {
            navigationManger.navigate(HOME);
        }
    }

    @Override
    public void initView() {

        toolBarTitle = findViewById(R.id.toolbar_title);
        backBtn = findViewById(R.id.back_btn);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        homeBrn = findViewById(R.id.navigation_home);
        campaignBtn = findViewById(R.id.navigation_missions);
        myProfileBtn = findViewById(R.id.navigation_profile);

        downloadsBtn = findViewById(R.id.navigation_downloads);
        callSupportFloatBtn = findViewById(R.id.btn_call_support);
        callSupportBtn = findViewById(R.id.navigation_call_support);

    }

    @Override
    public void initPresenter() {
        PhLogBusinessApp.initFastAndroidNetworking(PrefUtils.getBrandToken(getBaseContext()), BaseNetworkApi.DEFAULT_USER_TYPE, " en-us'", getBaseContext());

        bottomNavigation.setOnClickListener(this);
        homeBrn.setOnClickListener(this);
        campaignBtn.setOnClickListener(this);
        myProfileBtn.setOnClickListener(this);

        callSupportFloatBtn.setOnClickListener(this);
        downloadsBtn.setOnClickListener(this);
    }

    private void initListener() {
        backBtn.setOnClickListener((view) -> {
//            navigate(Constants.NavigationHelper.HOME);
            onBackPressed();
        });
    }


    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.navigation_home:
                navigationManger.navigate(HOME);
                break;
            case R.id.navigation_missions:
                navigationManger.navigate(CAMPAIGN);
                break;
            case R.id.navigation_call_support:
                navigationManger.navigate(CALL_SUPPORT);
                break;
            case R.id.navigation_profile:
                navigationManger.navigate(PROFILE);
                break;
            case R.id.btn_call_support:
                navigationManger.navigate(CALL_SUPPORT);
                break;
            case R.id.navigation_downloads:
                navigationManger.navigate(DOWNLOADS);
                break;
            default:
        }
    }

    private void callSupport() {
        Utilities.sendEmail(getResources().getString(R.string.phlog_mail), getBaseContext());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class NavigationManger {
        private Constants.NavigationHelper currentTab;

        private Object messageToFragment;

        public void setMessageToFragment(Object messageToFragment) {
            this.messageToFragment = messageToFragment;
        }

        private void clearSelected() {

            int homeBrnImg_off = R.drawable.ic_social_off;
            int campaignBtnImg_off = R.drawable.ic_campaigns_off;
            int downloadsBtnImg_off = R.drawable.ic_downloads_off;
            int myProfileBtnImg_off = R.drawable.ic_profile_off;

            homeBrn.setTextColor(getResources().getColor(R.color.gray677078));
            homeBrn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), homeBrnImg_off), null, null);
            homeBrn.setCompoundDrawablePadding(8);

            campaignBtn.setTextColor(getResources().getColor(R.color.gray677078));
            campaignBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), campaignBtnImg_off), null, null);
            campaignBtn.setCompoundDrawablePadding(8);


            myProfileBtn.setTextColor(getResources().getColor(R.color.gray677078));
            myProfileBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), myProfileBtnImg_off), null, null);
            myProfileBtn.setCompoundDrawablePadding(8);

            callSupportBtn.setVisibility(View.INVISIBLE);
            callSupportFloatBtn.setImageResource(R.drawable.btn_call_support);
            callSupportBtn.setTextColor(getResources().getColor(R.color.gray677078));
            callSupportBtn.setCompoundDrawablePadding(8);


            downloadsBtn.setTextColor(getResources().getColor(R.color.gray677078));
            downloadsBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), downloadsBtnImg_off), null, null);
            downloadsBtn.setCompoundDrawablePadding(8);
        }

        public void navigate(Constants.NavigationHelper navigationHelper) {
            clearSelected();
            int homeBrnImg = R.drawable.ic_social_on;
            int campaignBtnImg = R.drawable.ic_campaigns_on;
            int downloadsBtnImg = R.drawable.ic_downloads_on;
            int myProfileBtnImg = R.drawable.ic_profile_on;

            switch (navigationHelper) {
                case HOME: {
                    addFragment(R.id.view_container, new SocialFragment(), SocialFragment.class.getSimpleName(), false);
                    homeBrn.setTextColor(getResources().getColor(R.color.text_input_color));
                    homeBrn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), homeBrnImg), null, null);
                    homeBrn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.GONE);
                    currentTab = HOME;
                    break;
                }

                case CAMPAIGN: {
                    addFragment(R.id.view_container, CampaignsFragment.getInstance(), CampaignsFragment.class.getSimpleName(), false);
                    campaignBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    campaignBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), campaignBtnImg), null, null);
                    campaignBtn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.GONE);
                    toolBarTitle.setText(getResources().getString(R.string.campaigns));
                    currentTab = CAMPAIGN;
                    break;
                }
                case NOTIFICATION: {

                    addFragment(R.id.view_container, new NotificationFragment(), NotificationFragment.class.getSimpleName(), false);
//                    notificationBtn.setTextColor(getResources().getColor(R.color.text_input_color));
//                    notificationBtn.setCompoundDrawablesWithIntrinsicBounds(0, notificationBtnImg, 0, 0);
//                    notificationBtn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.GONE);
                    toolBarTitle.setText(getResources().getString(R.string.notification));
                    currentTab = NOTIFICATION;
                    break;
                }
                case CALL_SUPPORT: {
//                    callSupport();
                    addFragment(R.id.view_container, new CustomerSupportFragment(), CustomerSupportFragment.class.getSimpleName(), false);
                    callSupportBtn.setVisibility(View.VISIBLE);
                    callSupportBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    callSupportFloatBtn.setImageResource(R.drawable.btn_call_support_selected);
                    toolbar.setVisibility(View.VISIBLE);
                    toolBarTitle.setText(R.string.customer_support);
                    backBtn.setVisibility(View.INVISIBLE);
                    currentTab = CALL_SUPPORT;
                    break;
                }
                case PROFILE: {
                    addFragment(R.id.view_container, new BusinessProfileFragment(), BusinessProfileFragment.class.getSimpleName(), false);

                    myProfileBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    myProfileBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), myProfileBtnImg), null, null);
                    myProfileBtn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.GONE);
                    toolBarTitle.setText(getResources().getString(R.string.profile));
                    currentTab = PROFILE;
                    break;
                }

                case LIGHT_BOX: {

//
                    addFragment(R.id.view_container, BrandLightBoxFragment.getInstance(), BrandLightBoxFragment.class.getSimpleName(), true);
                    myProfileBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    myProfileBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), myProfileBtnImg), null, null);
                    myProfileBtn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.GONE);
//                    toolBarTitle.setText(getResources().getString(R.string.light_box));
                    currentTab = LIGHT_BOX;

                    break;
                }
                case ACCOUNT_DETAILS:
                    addFragment(R.id.view_container, AccountDetailsFragment.getInstance((Business) messageToFragment), AccountDetailsFragment.TAG, true);
                    currentTab = ACCOUNT_DETAILS;
                    break;
                case DOWNLOADS:
                    addFragment(R.id.view_container, new DownloadsFragment(), AccountDetailsFragment.TAG, true);
                    downloadsBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    downloadsBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), downloadsBtnImg), null, null);
                    downloadsBtn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.VISIBLE);
                    toolBarTitle.setText(R.string.downloads);
                    backBtn.setVisibility(View.INVISIBLE);
                    break;
                case LIGHT_BOX_PHOTOS:
                    LightBox lightBox = (LightBox) messageToFragment;
                    addFragment(R.id.view_container, LightBoxPhotosFragment.getInstance(lightBox), LightBoxPhotosFragment.TAG, true);
                    toolbar.setVisibility(View.VISIBLE);
                    toolBarTitle.setText(lightBox.name);
                    currentTab = LIGHT_BOX_PHOTOS;
                    break;
                case LOGOUT:
                    Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }

        }


        Constants.NavigationHelper getCurrentTab() {
            return currentTab;
        }
    }

    @Override
    public void onBackPressed() {

        switch (navigationManger.getCurrentTab()) {
            case HOME: {
                finish();
                break;
            }
            case CAMPAIGN: {
                navigationManger.navigate(HOME);
                break;
            }
            case NOTIFICATION: {
                navigationManger.navigate(HOME);
                break;
            }
            case PROFILE: {
                navigationManger.navigate(HOME);
                break;
            }
            case CALL_SUPPORT: {
                navigationManger.navigate(HOME);
                break;
            }
            case ACCOUNT_DETAILS: {
                navigationManger.navigate(PROFILE);
                break;
            }
            case LIGHT_BOX_PHOTOS:
                toolbar.setVisibility(View.GONE);
                super.onBackPressed();
                break;
            default: {
                super.onBackPressed();
            }


        }


    }
}
