package com.example.ddopik.phlogbusiness.ui.campaigns.inner.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/4/2018.
 */
public class InnerCampaignFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitleList;

    public InnerCampaignFragmentPagerAdapter(FragmentManager manager, Campaign campaign, List<String> mFragmentTitleList) {
        super(manager);
        mFragmentList.add(CampaignInnerSettingFragment.getInstance(campaign));
        mFragmentList.add(CampaignInnerPhotosFragment.getInstance(String.valueOf(campaign.id), campaign.status));
        this.mFragmentTitleList = mFragmentTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
