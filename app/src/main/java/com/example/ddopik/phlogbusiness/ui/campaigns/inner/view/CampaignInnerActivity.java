package com.example.ddopik.phlogbusiness.ui.campaigns.inner.view;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.presenter.CampaignInnerPresenter;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.presenter.CampaignInnerPresenterImpl;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/4/2018.
 */
public class CampaignInnerActivity extends BaseActivity implements CampaignInnerActivityView {


    private final String TAG = CampaignInnerActivity.class.getSimpleName();
    public static String CAMPAIGN_ID="campaign_id";
    private FrameLayout campaignImg;
    private TextView campaignTitle, campaignHostedBy, campaignDayLeft;
    private TabLayout campaignTabs;
    private ViewPager campaignViewPager;
    private CampaignInnerPresenter campaignInnerPresenter;
    private OnMissionCampaignDataRecived onMissionCampaignDataRecived;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_innner);
        initPresenter();
        initView();

    }

    @Override
    public void initView() {

        campaignImg = findViewById(R.id.campaign_header_img);
        campaignTitle = findViewById(R.id.campaign_title);
        campaignHostedBy = findViewById(R.id.campaign_hosted_by);
        campaignDayLeft = findViewById(R.id.campaign_day_left);
        campaignTabs = findViewById(R.id.inner_campaign_tabs);
        campaignViewPager = findViewById(R.id.inner_campaign_viewpager);
        if (getIntent().getStringExtra(CAMPAIGN_ID) !=null)
            campaignInnerPresenter.getCampaignDetails( getIntent().getStringExtra(CAMPAIGN_ID));
    }

    @Override
    public void initPresenter() {
        campaignInnerPresenter = new CampaignInnerPresenterImpl(getBaseContext(), this);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }


    @Override
    public void viewCampaignHeaderImg(String img) {
        GlideApp.with(this).load(img)
                .error(R.drawable.splash_screen_background)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                        campaignImg.setBackground(resource);
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void viewCampaignTitle(String name) {
        campaignTitle.setText(name);
    }

    @Override
    public void viewCampaignLeftDays(String dayLeft) {
        campaignDayLeft.setText(dayLeft);
        campaignDayLeft.append("  ");
        campaignDayLeft.append(getResources().getString(R.string.days_left));
    }

    @Override
    public void viewCampaignHostedBy(String hostName) {
        campaignHostedBy.setText(getResources().getString(R.string.hosted_by));
        campaignHostedBy.append(hostName);

    }

    @Override
    public void viewCampaignMissionDescription(String missionDesc,int photosCount) {

         InnerCampaignFragmentPagerAdapter innerCampaignFragmentPagerAdapter = new InnerCampaignFragmentPagerAdapter(getSupportFragmentManager(), getFragmentPagerFragment(), getFragmentTitles(photosCount));
        if (onMissionCampaignDataRecived != null && missionDesc != null) {
            campaignViewPager.setAdapter(innerCampaignFragmentPagerAdapter);
            campaignTabs.setupWithViewPager(campaignViewPager);
            onMissionCampaignDataRecived.onCampaignDescription(missionDesc);

        }
    }

    private List<Fragment> getFragmentPagerFragment() {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        CampaignInnerSettingFragment campaignInnerSettingFragment = CampaignInnerSettingFragment.getInstance();
         fragmentList.add(campaignInnerSettingFragment);
        fragmentList.add(CampaignInnerPhotosFragment.getInstance("1"));
        return fragmentList;
    }

    private List<String> getFragmentTitles(int photosCount) {
        List<String> fragmentList = new ArrayList<String>();
        fragmentList.add(getResources().getString(R.string.tab_mission));
        fragmentList.add(photosCount+" " + getResources().getString(R.string.tab_photos));
        return fragmentList;
    }

    public interface OnMissionCampaignDataRecived {
        void onCampaignDescription(String desc);
    }
}
