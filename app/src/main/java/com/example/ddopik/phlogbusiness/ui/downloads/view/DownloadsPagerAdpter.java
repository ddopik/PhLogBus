package com.example.ddopik.phlogbusiness.ui.downloads.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ddopik.phlogbusiness.ui.downloads.fragments.GridFragment;
import com.example.ddopik.phlogbusiness.ui.downloads.fragments.ListFragment;
import com.example.ddopik.phlogbusiness.ui.downloads.model.GroupItem;

import java.util.ArrayList;
import java.util.List;

public class DownloadsPagerAdpter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public DownloadsPagerAdpter(FragmentManager fm) {
        super(fm);
    }

    public DownloadsPagerAdpter(FragmentManager fm, List<GroupItem> data, DownloadsFragment.ChildFragmentActionListener childFragmentActionListener) {
        this(fm);
        fragments.add(ListFragment.newInstance(data, childFragmentActionListener));
        fragments.add(GridFragment.newInstance(data, childFragmentActionListener));
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
