package com.example.ddopik.phlogbusiness.ui.campaigns.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class CampaignsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> fragmentTitles;

    public CampaignsPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> fragmentTitles) {
        super(fragmentManager);

        this.fragmentList = fragmentList;
        this.fragmentTitles = fragmentTitles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}
