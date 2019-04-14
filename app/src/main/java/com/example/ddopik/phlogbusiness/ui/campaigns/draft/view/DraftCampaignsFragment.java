package com.example.ddopik.phlogbusiness.ui.campaigns.draft.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.PagingController;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.model.AddCampaignRequestModel;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view.AddCampaignActivity;
import com.example.ddopik.phlogbusiness.ui.campaigns.draft.presenter.DraftCampaignsPresenter;
import com.example.ddopik.phlogbusiness.ui.campaigns.presenter.CampaignsPresenterImpl;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private String nextPageUrl="1";
    private boolean isLoading;
    private ConstraintLayout noCampaignsPrompt;

    public static final String TAG = DraftCampaignsFragment.class.getSimpleName();

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
        draftCampaignsPresenter.getDraftCampaign(nextPageUrl, this);
    }

    @Override
    protected void initViews() {
        progressBar = mainView.findViewById(R.id.draft_campaign_progress_bar);
        draftCampaignsRv = mainView.findViewById(R.id.draft_campaigns_rv);
        draftCampaignsAdapter = new DraftCampaignsAdapter(getContext(), draftCampaignList);
        draftCampaignsRv.setAdapter(draftCampaignsAdapter);

        noCampaignsPrompt = mainView.findViewById(R.id.no_added_campaign_prompt);
    }

    @Override
    protected void initPresenter() {
        draftCampaignsPresenter = new CampaignsPresenterImpl(getContext());
    }


    private void initListener() {

        pagingController = new PagingController(draftCampaignsRv) {


            @Override
            protected void loadMoreItems() {

                draftCampaignsPresenter.getDraftCampaign(nextPageUrl, DraftCampaignsFragment.this);
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
        draftCampaignsAdapter.campaignLister = new DraftCampaignsAdapter.CampaignLister() {
            @Override
            public void onCampaignClicked(Campaign campaign) {
                if (campaign.status.equals(Constants.CampaignStatus.CAMPAIGN_STATUS_DRAFT)) {
                    Intent intent = new Intent(getContext(), AddCampaignActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    AddCampaignRequestModel model = createModel(campaign);
                    intent.putExtra(AddCampaignActivity.CAMPAIGN_DATA, model);
                    startActivity(intent);
                }
            }

            @Override
            public void onDeleteClicked(Campaign campaign, int position) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.delete_confirmation)
                        .setPositiveButton(R.string.yes, (dialog, which) -> {
                            dialog.dismiss();
                            progressBar.setVisibility(View.VISIBLE);
                            draftCampaignsPresenter.deleteCampaign(campaign)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .doFinally(() -> progressBar.setVisibility(View.INVISIBLE))
                                    .subscribe(success -> {
                                        if (success) {
                                            draftCampaignList.remove(campaign);
                                            draftCampaignsAdapter.notifyItemRemoved(position);
                                        }
                                    }, throwable -> {
                                        CustomErrorUtil.Companion.setError(getContext(), TAG, throwable);
                                    });
                        }).setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss()).show();
            }
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
        if (this.draftCampaignList.isEmpty()) {
            noCampaignsPrompt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showDraftCampaignProgress(boolean state) {
        isLoading=state;

        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void setNextPageUrl(String page) {
        this.nextPageUrl=page;
    }
}
