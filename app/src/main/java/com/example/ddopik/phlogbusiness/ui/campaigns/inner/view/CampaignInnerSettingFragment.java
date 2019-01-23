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
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;
import com.example.ddopik.phlogbusiness.base.widgets.PickDateDialog;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.presenter.CampaignInnerSettingPresenter;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.presenter.CampaignInnerSettingPresenterImpl;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by abdalla_maged on 10/8/2018.
 */
public class CampaignInnerSettingFragment extends BaseFragment implements CampaignInnerSettingsView {


    private View mainView;
    private TextView campaignStatus, campaignPrize,campaignStartDate,campaignEndDate, campaignPhotosNumber;
    private Campaign campaign;

    private CampaignInnerSettingPresenter presenter;

    public CampaignInnerSettingFragment() {
    }

    public static CampaignInnerSettingFragment getInstance(Campaign campaign) {
        CampaignInnerSettingFragment fragment = new CampaignInnerSettingFragment();
        fragment.campaign = campaign;
        return fragment;
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
        initListeners();
        initPresenter();
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    protected void initPresenter() {
        presenter = new CampaignInnerSettingPresenterImpl();
        presenter.setView(this);
    }

    @Override
    protected void initViews() {

        campaignStatus = mainView.findViewById(R.id.campaign_status);
        campaignPrize = mainView.findViewById(R.id.campaign_prize);
        campaignStartDate = mainView.findViewById(R.id.campaign_start_date);
        campaignEndDate = mainView.findViewById(R.id.campaign_end_date);
        campaignPhotosNumber = mainView.findViewById(R.id.campaign_number_photos);

        setCampaignViews();
    }

    private void setCampaignViews() {
        campaignStatus.setText("" + campaign.status);
        campaignEndDate.setText(campaign.endDate);
        campaignStartDate.setText(campaign.startDate);
        campaignPrize.setText(campaign.prize);
        campaignPhotosNumber.setText("" + -1);
    }

    private void initListeners() {
        campaignEndDate.setOnClickListener(v -> {
            PickDateDialog pickDateDialog = new PickDateDialog();
            pickDateDialog.setOnDateSet((year, month, day) -> {
                String dateString = year + "-" + (month + 1) + "-" + day;
                campaignEndDate.setText(dateString);
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                String s = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(calendar.getTime());
                presenter.setEndDate(getContext(), campaign.id, s);
            });
            pickDateDialog.show(getChildFragmentManager(), PickDateDialog.class.getSimpleName());
        });
    }

    @Override
    public void showMessage(int res) {
        showToast(getString(res));
    }
}