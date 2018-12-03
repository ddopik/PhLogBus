package com.example.ddopik.phlogbusiness.ui.campaigns.inner.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by abdalla_maged on 10/4/2018.
 */
public class InnerCampaignFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList;
    private List<String> mFragmentTitleList;

    public InnerCampaignFragmentPagerAdapter(FragmentManager manager, List<Fragment> mFragmentList, List<String> mFragmentTitleList) {
        super(manager);
        this.mFragmentList = mFragmentList;
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
