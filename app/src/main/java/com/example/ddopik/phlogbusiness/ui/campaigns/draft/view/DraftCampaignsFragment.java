package com.example.ddopik.phlogbusiness.ui.campaigns.draft.view;

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
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.model.AddCampaignRequestModel;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view.AddCampaignActivity;
import com.example.ddopik.phlogbusiness.ui.campaigns.draft.presenter.DraftCampaignsPresenter;
import com.example.ddopik.phlogbusiness.ui.campaigns.presenter.CampaignsPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class DraftCampaignsFragment extends BaseFragment implements DraftCampaignsFragmentView {

    private View mainView;
    private ProgressBar progressBar;
    private CustomRecyclerView draftCampaignsRv;
    private DraftCampaignsAdapter draftCampaignsAdapter;
    private List<Campaign> draftCampaignList = new ArrayList<>();
    private DraftCampaignsPresenter draftCampaignsPresenter;
    private PagingController pagingController;

    public static DraftCampaignsFragment getInstance() {
        DraftCampaignsFragment draftCampaignsFragment = new DraftCampaignsFragment();
        return draftCampaignsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_draft_campaigns, container, false);
        return mainView;
    }

    private boolean loadedFirstPage;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPresenter();
        initViews();
        initListener();
        draftCampaignsPresenter.getDraftCampaign(0, this);
    }

    @Override
    protected void initViews() {
        progressBar = mainView.findViewById(R.id.draft_campaign_progress_bar);
        draftCampaignsRv = mainView.findViewById(R.id.draft_campaigns_rv);
        draftCampaignsAdapter = new DraftCampaignsAdapter(getContext(), draftCampaignList);
        draftCampaignsRv.setAdapter(draftCampaignsAdapter);
    }

    @Override
    protected void initPresenter() {
        draftCampaignsPresenter = new CampaignsPresenterImpl(getContext());
    }


    private void initListener() {

        pagingController = new PagingController(draftCampaignsRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
//                draftCampaignsPresenter.getDraftCampaign(page, DraftCampaignsFragment.this);
            }
        };
        draftCampaignsAdapter.campaignLister = campaign -> {
            Intent intent =new Intent(getContext(),AddCampaignActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            AddCampaignRequestModel model = createModel(campaign);
            intent.putExtra(AddCampaignActivity.CAMPAIGN_DATA, model);
            startActivity(intent);
        };

    }

    private AddCampaignRequestModel createModel(Campaign campaign) {
        AddCampaignRequestModel model = new AddCampaignRequestModel();
        model.winnersNumber = String.valueOf(campaign.winnerCount);
        model.campaignEndDate = campaign.endDate;
        model.campaignStartDate = campaign.startDate;
        model.tagList = campaign.tags;
        model.campaignPrize = campaign.prize;
        model.isDraft = "true";
        model.campaignDescription = campaign.descrptionEn;
        model.campaignName = campaign.titleEn;
        model.coverUrl = campaign.imageCover;
        model.alreadyAdded = true;
        model.campaignId = campaign.id;
        return model;
    }

    @Override
    public void viewDraftCampaign(List<Campaign> draftCampaignsList) {
        draftCampaignsRv.setVisibility(View.VISIBLE);
        this.draftCampaignList.addAll(draftCampaignsList);
        draftCampaignsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDraftCampaignProgress(boolean state) {

        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
