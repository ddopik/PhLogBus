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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
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
    public static String CAMPAIGN_ID = "campaign_id";
    private ImageView campaignImg;
    private TextView campaignTitle, campaignHostedBy, campaignDayLeft;
    private TabLayout campaignTabs;
    private ViewPager campaignViewPager;
    private CampaignInnerPresenter campaignInnerPresenter;

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
        campaignTabs.setupWithViewPager(campaignViewPager);
        if (getIntent().getStringExtra(CAMPAIGN_ID) != null)
            campaignInnerPresenter.getCampaignDetails(getIntent().getStringExtra(CAMPAIGN_ID));
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
                .into(campaignImg);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void viewCampaignTitle(String name) {
        campaignTitle.setText(name);
    }

    @Override
    public void viewCampaignLeftDays(String dayLeft) {
        campaignDayLeft.setText(String.format("%1$s %2$s", dayLeft, getString(R.string.days_left)));
    }

    @Override
    public void viewCampaignHostedBy(String hostName) {
        if (hostName == null || hostName.isEmpty())
            return;
        campaignHostedBy.setText(getString(R.string.hosted_by, hostName));
    }

    @Override
    public void viewCampaignMissionDescription(String missionDesc, int photosCount) {
//        InnerCampaignFragmentPagerAdapter innerCampaignFragmentPagerAdapter = new InnerCampaignFragmentPagerAdapter(getSupportFragmentManager(), getFragmentPagerFragment(campaign), getFragmentTitles(photosCount));
//        campaignViewPager.setAdapter(innerCampaignFragmentPagerAdapter);
//        campaignTabs.setupWithViewPager(campaignViewPager);
    }

    private List<Fragment> getFragmentPagerFragment(Campaign campaign) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(CampaignInnerSettingFragment.getInstance(campaign));
        fragmentList.add(CampaignInnerPhotosFragment.getInstance(String.valueOf(campaign.id)));
        return fragmentList;
    }

    private List<String> getFragmentTitles(int photosCount) {
        List<String> fragmentList = new ArrayList<String>();
        fragmentList.add(getResources().getString(R.string.tab_mission));
        fragmentList.add(photosCount + " " + getResources().getString(R.string.tab_photos));
        return fragmentList;
    }

    @Override
    public void setCampaign(Campaign campaign) {
        InnerCampaignFragmentPagerAdapter adapter = new InnerCampaignFragmentPagerAdapter(getSupportFragmentManager()
                , campaign
                , getFragmentTitles(campaign.photosCount));
        campaignViewPager.setAdapter(adapter);
    }
}
