package com.example.ddopik.phlogbusiness.ui.social.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.view.SearchActivity;
import com.example.ddopik.phlogbusiness.ui.social.model.SocialData;
import com.example.ddopik.phlogbusiness.ui.social.presenter.SocailFragmentPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.social.presenter.SocialFragmentPresenter;
import com.example.ddopik.phlogbusiness.ui.social.view.adapter.SocialAdapter;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;


import java.util.ArrayList;
import java.util.List;

import static com.example.ddopik.phlogbusiness.utiltes.Constants.SOCIAL_FRAGMENT_PAGING_THRESHOLD;


public class SocialFragment extends BaseFragment implements SocialFragmentView, SocialAdapter.OnSocialItemListener {

    private View mainView;
    private EditText homeSearch;
    private ProgressBar socialProgress;
    private CustomRecyclerView socailRv;
    private SocialFragmentPresenter socialFragmentPresenter;
    private SocialAdapter socialAdapter;
    private List<SocialData> socialDataList = new ArrayList<>();
    private PagingController pagingController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.fragment_home, container, false);
        return mainView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        socialFragmentPresenter.getSocialData(true);
        try {
            Utilities.intializeData("mina");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void initPresenter() {
        socialFragmentPresenter = new SocailFragmentPresenterImpl(getContext(), this);
    }

    @Override
    protected void initViews() {
        homeSearch = mainView.findViewById(R.id.home_search);
        socailRv = mainView.findViewById(R.id.social_rv);
        socialProgress = mainView.findViewById(R.id.social_progress);


        this.socialAdapter = new SocialAdapter(socialDataList, getActivity(), this);
        socailRv.setAdapter(socialAdapter);


    }

    private void initListener() {
        homeSearch.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        pagingController = new PagingController(socailRv, SOCIAL_FRAGMENT_PAGING_THRESHOLD) {

            @Override
            public void getPagingControllerCallBack(int page) {
                socialFragmentPresenter.getSocialData(false);
            }
        };
    }

    @Override
    public void viewSocialData(List<SocialData> socialDataList) {
        this.socialDataList.addAll(socialDataList);
        socialAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewSocialDataProgress(boolean state) {
        if (state) {
            socialProgress.setVisibility(View.VISIBLE);
        } else {
            socialProgress.setVisibility(View.GONE);
        }
    }


//    @Override
//    public void onSocialCampaignJoined(int campaignId, boolean state) {
//        for (SocialData socialData : socialDataList) {
//            if (socialData.campaigns != null && socialData.campaigns.size() > 0)
//                for (Campaign campaign : socialData.campaigns) {
//                    if (campaignId == campaign.id) {
//                        campaign.isJoined = state;
//                    }
//                }
//        }
//        socialAdapter.notifyDataSetChanged();
//    }


//    @Override
//    public void onSocialPhotoGrapherFollowed(int userId, boolean state) {
//        for (SocialData socialData : socialDataList) {
//            if (socialData.profiles != null && socialData.profiles.size() > 0)
//                for (Photographer photographer : socialData.profiles) {
//                    if (photographer.id.equals(userId)) {
//                        photographer.isFollow = state;
//                    }
//                }
//        }
//        socialAdapter.notifyDataSetChanged();
//    }


    @Override
    public void onSocialBrandFollowed(int brandId, boolean state) {
        for (SocialData socialData : socialDataList) {
            if (socialData.brands != null && socialData.brands.size() > 0)
                for (Business business : socialData.brands) {
                    if (business.id.equals(brandId)) {
                        business.isFollow = state;
                    }
                }
        }
        socialAdapter.notifyDataSetChanged();
    }


    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

}
