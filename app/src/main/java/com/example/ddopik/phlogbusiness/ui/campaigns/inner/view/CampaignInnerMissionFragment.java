package com.example.ddopik.phlogbusiness.ui.campaigns.inner.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;


/**
 * Created by abdalla_maged on 10/8/2018.
 */
public class CampaignInnerMissionFragment extends BaseFragment implements CampaignInnerActivity.OnMissionCampaignDataRecived {


    private TextView missionDescription;
    private View mainView;

    public static CampaignInnerMissionFragment getInstance() {

        return new CampaignInnerMissionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.fragment_campaign_inner_mission, container, false);
        return mainView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        missionDescription = mainView.findViewById(R.id.mission_desc);


    }

    @Override
    public void onCampaignDescription(String desc) {

            missionDescription.setText(desc);
    }
}