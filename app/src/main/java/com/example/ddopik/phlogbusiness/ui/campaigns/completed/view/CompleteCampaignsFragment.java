package com.example.ddopik.phlogbusiness.ui.campaigns.completed.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.campaigns.completed.presenter.CompleteCampaignPresenter;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.view.CampaignInnerActivity;
import com.example.ddopik.phlogbusiness.ui.campaigns.presenter.CampaignsPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.campaigns.running.presenter.RunningCampaignPresenter;
import com.example.ddopik.phlogbusiness.ui.campaigns.running.view.RunningCampaignsAdapter;
import com.example.ddopik.phlogbusiness.ui.campaigns.running.view.RunningCampaignsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class CompleteCampaignsFragment extends BaseFragment implements CompleteCampaignsFragmentView {

    private View mainView;
    private ProgressBar progressBar;
    private CustomRecyclerView allCampaignsRv;
    private CompletedCampaignsAdapter completedCampaignsAdapter;
    private List<Campaign> completedCampaignList = new ArrayList<>();
    private CompleteCampaignPresenter completeCampaignPresenter;
    private PagingController pagingController;


    public static CompleteCampaignsFragment getInstance(){
        CompleteCampaignsFragment completeCampaignsFragment=new CompleteCampaignsFragment();
        return completeCampaignsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.fragment_complete_fragment, container,false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        completeCampaignPresenter.getCompletedCampaign(0,this);
    }

    @Override
    protected void initViews() {
        progressBar = mainView.findViewById(R.id.complete_campaign_progress_bar);
        allCampaignsRv = mainView.findViewById(R.id.complete_campaigns_rv);
        completedCampaignsAdapter = new CompletedCampaignsAdapter
                (getContext(), completedCampaignList);
        allCampaignsRv.setAdapter(completedCampaignsAdapter);
    }
    @Override
    protected void initPresenter() {
        completeCampaignPresenter = new CampaignsPresenterImpl(getContext());

    }
    private void initListener() {

        pagingController = new PagingController(allCampaignsRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                completeCampaignPresenter.getCompletedCampaign(page,CompleteCampaignsFragment.this);
            }
        };
        completedCampaignsAdapter.campaignLister = campaignID -> {
            Intent intent = new Intent(getContext(), CampaignInnerActivity.class);
            intent.putExtra(CampaignInnerActivity.CAMPAIGN_ID, campaignID);
            startActivity(intent);
        };

    }


    @Override
    public void viewAllCompletedCampaign(List<Campaign> campaignList) {
        allCampaignsRv.setVisibility(View.VISIBLE);
        this.completedCampaignList.addAll(campaignList);
        completedCampaignsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAllCompletedCampaignProgress(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }
}


