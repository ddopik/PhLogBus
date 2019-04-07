package com.example.ddopik.phlogbusiness.ui.campaigns.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view.AddCampaignActivity;
import com.example.ddopik.phlogbusiness.ui.campaigns.completed.view.CompleteCampaignsFragment;
import com.example.ddopik.phlogbusiness.ui.campaigns.draft.view.DraftCampaignsFragment;
import com.example.ddopik.phlogbusiness.ui.campaigns.running.view.RunningCampaignsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class CampaignsFragment extends BaseFragment {

    private View mainView;
    private TabLayout campaignsTabLayout;
    private ViewPager campaignsViewPager;
    private CampaignsPagerAdapter campaignsPagerAdapter;
    private ImageButton addCampaignBtn;


    public static CampaignsFragment getInstance() {
        CampaignsFragment campaignsFragment = new CampaignsFragment();
        return campaignsFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_campaigns, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListeners();
    }



    @Override
    protected void initViews() {
        campaignsTabLayout = mainView.findViewById(R.id.campaigns_tab);
        campaignsViewPager = mainView.findViewById(R.id.campaigns_view_pager);
        addCampaignBtn=mainView.findViewById(R.id.add_campaign_btn);
        campaignsPagerAdapter = new CampaignsPagerAdapter(getChildFragmentManager(), getCampaignsFragment(), getFragmentTitles());
        campaignsViewPager.setAdapter(campaignsPagerAdapter);
        campaignsViewPager.setOffscreenPageLimit(2);
        campaignsTabLayout.setupWithViewPager(campaignsViewPager);
        mainView.findViewById(R.id.back_btn).setVisibility(View.GONE);
    }

    @Override
    protected void initPresenter() {

    }

    private void initListeners(){
        addCampaignBtn.setOnClickListener(v -> {
            Intent intent =new Intent(getContext(),AddCampaignActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }


    private List<Fragment> getCampaignsFragment() {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(RunningCampaignsFragment.getInstance());
        fragmentList.add(CompleteCampaignsFragment.getInstance());
        fragmentList.add(DraftCampaignsFragment.getInstance());
        return fragmentList;
    }

    private List<String> getFragmentTitles() {
        List<String> fragmentListTitle = new ArrayList<String>();
        fragmentListTitle.add(getContext().getResources().getString(R.string.campaign_running_tab));
        fragmentListTitle.add(getContext().getResources().getString(R.string.campaign_complete_tab));
        fragmentListTitle.add(getContext().getResources().getString(R.string.campaign_draft_tab));

        return fragmentListTitle;
    }

}
