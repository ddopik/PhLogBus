package com.example.ddopik.phlogbusiness.ui.campaigns.inner.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.ui.campaigns.inner.view.CampaignInnerSettingsView;

public interface CampaignInnerSettingPresenter {

    void setView(CampaignInnerSettingsView view);

    void setEndDate(Context context, Integer id, String dateString);
}
