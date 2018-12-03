package com.example.ddopik.phlogbusiness.ui.campaigns;

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
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.view.CampaignInnerActivity;
import com.example.ddopik.phlogbusiness.ui.campaigns.model.HomeCampaign;
import com.example.ddopik.phlogbusiness.ui.campaigns.presenter.CampaignPresenter;
import com.example.ddopik.phlogbusiness.ui.campaigns.presenter.CampaignPresenterImpl;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class CampaignsFragment extends BaseFragment implements CampaignFragmentView {


    private View mainView;
    private ProgressBar progressBar;
    private CustomRecyclerView allCampaignsRv;
    private AllCampaignsAdapter allCampaignsAdapter;
    private List<HomeCampaign> homeCampaignList = new ArrayList<>();
    private CampaignPresenter campaignPresenter;
    private PagingController pagingController;

    private int currentPage = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_campaigns, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        campaignPresenter.getAllCampaign(0);
    }


    @Override
    protected void initViews() {
        progressBar = mainView.findViewById(R.id.all_home_campaign_progress_bar);
        allCampaignsRv = mainView.findViewById(R.id.all_campaigns_rv);
        allCampaignsAdapter = new AllCampaignsAdapter(getContext(), homeCampaignList);
        allCampaignsRv.setAdapter(allCampaignsAdapter);


    }

    @Override
    protected void initPresenter() {
        campaignPresenter = new CampaignPresenterImpl(getContext(), this);
    }

    private void initListener() {

        pagingController = new PagingController(allCampaignsRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                campaignPresenter.getAllCampaign(currentPage + 1);
            }
        };
        allCampaignsAdapter.campaignLister = campaignID -> {
            Intent intent = new Intent(getContext(), CampaignInnerActivity.class);
            intent.putExtra(CampaignInnerActivity.CAMPAIGN_ID, campaignID);
            startActivity(intent);
        };

    }


    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public void viewAllCampaign(List<HomeCampaign> homeCampaignList) {
        allCampaignsRv.setVisibility(View.VISIBLE);
        this.homeCampaignList.clear();
        this.homeCampaignList.addAll(homeCampaignList);
        allCampaignsAdapter.notifyDataSetChanged();

    }

    @Override
    public void showAllCampaginProgress(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }
}
