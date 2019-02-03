package com.example.ddopik.phlogbusiness.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
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
import com.example.ddopik.phlogbusiness.ui.accountdetails.view.AccountDetailsFragment;
import com.example.ddopik.phlogbusiness.ui.customersupport.view.CustomerSupportFragment;
import com.example.ddopik.phlogbusiness.ui.downloads.view.DownloadsFragment;
import com.example.ddopik.phlogbusiness.ui.lightboxphotos.view.LightBoxPhotosFragment;
import com.example.ddopik.phlogbusiness.ui.splash.SplashActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.accountdetails.view.AccountDetailsFragment;
import com.example.ddopik.phlogbusiness.ui.campaigns.view.CampaignsFragment;
import com.example.ddopik.phlogbusiness.ui.lightbox.view.BrandLightBoxFragment;
import com.example.ddopik.phlogbusiness.ui.profile.view.BusinessProfileFragment;
import com.example.ddopik.phlogbusiness.ui.social.view.SocialFragment;
import com.example.ddopik.phlogbusiness.ui.uploadimage.view.GalleryImageFragment;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;

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


    @Override
    protected void onResume() {
        super.onResume();
    }

    public class  NavigationManger {
        private Constants.NavigationHelper currentTab;

        private Object messageToFragment;

        public void setMessageToFragment(Object messageToFragment) {
            this.messageToFragment = messageToFragment;
        }

        private void clearSelected() {

            int homeBrnImg_off = R.drawable.ic_tab_home_off;
            int campaignBtnImg_off = R.drawable.ic_tab_missions_off;
            int notificationBtnImg_off = R.drawable.ic_tab_notificatin_off;
            int myProfileBtnImg_off = R.drawable.ic_tab_profile_off;

            homeBrn.setTextColor(getResources().getColor(R.color.gray677078));
            homeBrn.setCompoundDrawablesWithIntrinsicBounds(0, homeBrnImg_off, 0, 0);
            homeBrn.setCompoundDrawablePadding(8);

            campaignBtn.setTextColor(getResources().getColor(R.color.gray677078));
            campaignBtn.setCompoundDrawablesWithIntrinsicBounds(0, campaignBtnImg_off, 0, 0);
            campaignBtn.setCompoundDrawablePadding(8);



            myProfileBtn.setTextColor(getResources().getColor(R.color.gray677078));
            myProfileBtn.setCompoundDrawablesWithIntrinsicBounds(0, myProfileBtnImg_off, 0, 0);
            myProfileBtn.setCompoundDrawablePadding(8);

            callSupportBtn.setVisibility(View.INVISIBLE);
            callSupportFloatBtn.setImageResource(R.drawable.btn_call_support);
            callSupportBtn.setTextColor(getResources().getColor(R.color.gray677078));

            callSupportBtn.setCompoundDrawablePadding(8);


            downloadsBtn.setTextColor(getResources().getColor(R.color.gray677078));
        }

       public void navigate(Constants.NavigationHelper navigationHelper) {
            clearSelected();
            int homeBrnImg = R.drawable.ic_tab_home_on;
            int campaignBtnImg = R.drawable.ic_tab_missions_on;
            int notificationBtnImg = R.drawable.ic_tab_notificatin_on;
            int myProfileBtnImg = R.drawable.ic_tab_profile_on;

            switch (navigationHelper) {
                case HOME: {
                    addFragment(R.id.view_container, new SocialFragment(), SocialFragment.class.getSimpleName(), false);
                    homeBrn.setTextColor(getResources().getColor(R.color.text_input_color));
                    homeBrn.setCompoundDrawablesWithIntrinsicBounds(0, homeBrnImg, 0, 0);
                    homeBrn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.GONE);
                    currentTab = HOME;
                    break;
                }

                case CAMPAIGN: {
                    addFragment(R.id.view_container,  CampaignsFragment.getInstance(), CampaignsFragment.class.getSimpleName(), false);
                    campaignBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    campaignBtn.setCompoundDrawablesWithIntrinsicBounds(0, campaignBtnImg, 0, 0);
                    campaignBtn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.GONE);
                    toolBarTitle.setText(getResources().getString(R.string.campaigns));
                    currentTab = CAMPAIGN;
                    break;
                }
//                case NOTIFICATION: {
//
//                    addFragment(R.id.view_container, new NotificationFragment(), NotificationFragment.class.getSimpleName(), false);
//                    notificationBtn.setTextColor(getResources().getColor(R.color.text_input_color));
//                    notificationBtn.setCompoundDrawablesWithIntrinsicBounds(0, notificationBtnImg, 0, 0);
//                    notificationBtn.setCompoundDrawablePadding(8);
//                    toolbar.setVisibility(View.VISIBLE);
//                    toolBarTitle.setText(getResources().getString(R.string.notification));
//                    currentTab = NOTIFICATION;
//                    break;
//                }
                case CALL_SUPPORT: {
                    addFragment(R.id.view_container, new CustomerSupportFragment(), CustomerSupportFragment.class.getSimpleName(), false);
                    callSupportBtn.setVisibility(View.VISIBLE);
                    callSupportBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    callSupportFloatBtn.setImageResource(R.drawable.btn_call_support_selected);
                    toolbar.setVisibility(View.VISIBLE);
                    toolBarTitle.setText(getResources().getString(R.string.call_support));
                    currentTab = CALL_SUPPORT;
                    break;
                }
                case PROFILE: {
                    addFragment(R.id.view_container, new BusinessProfileFragment(), BusinessProfileFragment.class.getSimpleName(), false);

                    myProfileBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    myProfileBtn.setCompoundDrawablesWithIntrinsicBounds(0, myProfileBtnImg, 0, 0);
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
                    myProfileBtn.setCompoundDrawablesWithIntrinsicBounds(0, myProfileBtnImg, 0, 0);
                    myProfileBtn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.GONE);
//                    toolBarTitle.setText(getResources().getString(R.string.light_box));
                    currentTab = EDIT_PROFILE;

                    break;
                }
                case ACCOUNT_DETAILS:
                    addFragment(R.id.view_container, AccountDetailsFragment.getInstance((Business) messageToFragment), AccountDetailsFragment.TAG, true);
                    currentTab = ACCOUNT_DETAILS;
                    break;
                case DOWNLOADS:
                    addFragment(R.id.view_container, new DownloadsFragment(), AccountDetailsFragment.TAG, true);
                    downloadsBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    break;
                case LIGHT_BOX_PHOTOS:
                    LightBox lightBox = (LightBox) messageToFragment;
                    addFragment(R.id.view_container, LightBoxPhotosFragment.getInstance(lightBox), LightBoxPhotosFragment.TAG, true);
                    toolbar.setVisibility(View.VISIBLE);
                    toolBarTitle.setText(lightBox.name);
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
