package com.example.ddopik.phlogbusiness.ui.setupbrand.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.ui.setupbrand.fragment.stepone.StepOneFragment;
import com.example.ddopik.phlogbusiness.ui.setupbrand.fragment.stepthree.StepThreeFragment;
import com.example.ddopik.phlogbusiness.ui.setupbrand.fragment.stoptwo.StepTwoFragment;

import java.util.ArrayList;
import java.util.List;

public class SetupBrandPagerAdapter extends FragmentStatePagerAdapter {

    private final List<BaseFragment> fragments = new ArrayList<>();

    public SetupBrandPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public SetupBrandPagerAdapter(FragmentManager supportFragmentManager, SetupBrandActivity.SubViewActionConsumer subViewActionConsumer) {
        this(supportFragmentManager);
        fragments.add(StepOneFragment.newInstance(subViewActionConsumer));
        fragments.add(StepTwoFragment.newInstance(subViewActionConsumer));
        fragments.add(StepThreeFragment.newInstance(subViewActionConsumer));
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
