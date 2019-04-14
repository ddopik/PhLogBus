package com.example.ddopik.phlogbusiness.ui.campaigns.completed.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.PagingController;
import com.example.ddopik.phlogbusiness.ui.campaigns.completed.presenter.CompleteCampaignPresenter;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.view.CampaignInnerActivity;
import com.example.ddopik.phlogbusiness.ui.campaigns.presenter.CampaignsPresenterImpl;

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
    private String nextPageUrl="1";
    private boolean isLoading;
    private ConstraintLayout noCampaignsPrompt;


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

    private boolean loadedFirstPage;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        completeCampaignPresenter.getCompletedCampaign(nextPageUrl,this);
    }

    @Override
    protected void initViews() {
        progressBar = mainView.findViewById(R.id.complete_campaign_progress_bar);
        allCampaignsRv = mainView.findViewById(R.id.complete_campaigns_rv);
        completedCampaignsAdapter = new CompletedCampaignsAdapter
                (getContext(), completedCampaignList);
        allCampaignsRv.setAdapter(completedCampaignsAdapter);

        noCampaignsPrompt = mainView.findViewById(R.id.no_added_campaign_prompt);
    }
    @Override
    protected void initPresenter() {
        completeCampaignPresenter = new CampaignsPresenterImpl(getContext());

    }
    private void initListener() {



        pagingController = new PagingController(allCampaignsRv) {


            @Override
            protected void loadMoreItems() {
                 completeCampaignPresenter.getCompletedCampaign(nextPageUrl,CompleteCampaignsFragment.this);
            }

            @Override
            public boolean isLastPage() {

                if (nextPageUrl ==null){
                    return  true;
                }else {
                    return false;
                }

            }

            @Override
            public boolean isLoading() {
                return isLoading;
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
        if (this.completedCampaignList.isEmpty()) {
            noCampaignsPrompt.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void setNextPageUrl(String page) {
        this.nextPageUrl=page;
    }
    @Override
    public void showAllCompletedCampaignProgress(boolean state) {
        isLoading=state;
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }
}


