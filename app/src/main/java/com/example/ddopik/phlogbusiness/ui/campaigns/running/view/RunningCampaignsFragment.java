package com.example.ddopik.phlogbusiness.ui.campaigns.running.view;

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
import com.example.ddopik.phlogbusiness.ui.campaigns.draft.presenter.DraftCampaignsPresenter;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.view.CampaignInnerActivity;
import com.example.ddopik.phlogbusiness.ui.campaigns.presenter.CampaignsPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.campaigns.running.presenter.RunningCampaignPresenter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class RunningCampaignsFragment extends BaseFragment implements RunningCampaignFragmentView{


    private View mainView;
    private ProgressBar progressBar;
    private CustomRecyclerView allCampaignsRv;
    private RunningCampaignsAdapter runningCampaignsAdapter;
    private List<Campaign> runningCampaignList = new ArrayList<>();
    private RunningCampaignPresenter runningCampaignPresenter;
    private PagingController pagingController;





    public static RunningCampaignsFragment getInstance(){
        RunningCampaignsFragment runningCampaignsFragment=new RunningCampaignsFragment();
        return runningCampaignsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_running_campaigns, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        runningCampaignPresenter.getRunningCampaign(0,this);
    }


    @Override
    protected void initViews() {
        progressBar = mainView.findViewById(R.id.all_home_campaign_progress_bar);
        allCampaignsRv = mainView.findViewById(R.id.all_campaigns_rv);
        runningCampaignsAdapter = new RunningCampaignsAdapter(getContext(), runningCampaignList);
        allCampaignsRv.setAdapter(runningCampaignsAdapter);


    }

    @Override
    protected void initPresenter() {
        runningCampaignPresenter = new CampaignsPresenterImpl(getContext());
    }

    private void initListener() {

        pagingController = new PagingController(allCampaignsRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                runningCampaignPresenter.getRunningCampaign(page,RunningCampaignsFragment.this);
            }
        };
        runningCampaignsAdapter.campaignLister = campaignID -> {
            Intent intent = new Intent(getContext(), CampaignInnerActivity.class);
            intent.putExtra(CampaignInnerActivity.CAMPAIGN_ID, campaignID);
            startActivity(intent);
        };

    }




    @Override
    public void viewAllCampaign(List<Campaign> runningCampaignList) {
        allCampaignsRv.setVisibility(View.VISIBLE);
        this.runningCampaignList.addAll(runningCampaignList);
        runningCampaignsAdapter.notifyDataSetChanged();

    }

    @Override
    public void showAllCampaignProgress(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }
}
