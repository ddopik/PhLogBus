package com.example.ddopik.phlogbusiness.ui.downloads.view;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.ui.downloads.model.DataItem;
import com.example.ddopik.phlogbusiness.ui.downloads.presenter.DownloadsPresenter;
import com.example.ddopik.phlogbusiness.ui.downloads.presenter.DownloadsPresenterImpl;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadsFragment extends BaseFragment implements DownloadsView {

    private DownloadsPresenter presenter;

    private View mainView;
    private TextView itemsNumberTV;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public DownloadsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return mainView = inflater.inflate(R.layout.fragment_downloads, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setListeners();
        initPresenter();
    }

    @Override
    protected void initPresenter() {
        presenter = new DownloadsPresenterImpl();
        presenter.setView(this);
        presenter.getDownloads(getContext());
    }

    @Override
    protected void initViews() {
        itemsNumberTV = mainView.findViewById(R.id.items_number_text_view);
        tabLayout = mainView.findViewById(R.id.tab_layout);
        viewPager = mainView.findViewById(R.id.view_pager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setListeners() {
    }

    @Override
    public void setDownloads(List<DataItem> data) {
        PagerAdapter adapter = new DownloadsPagerAdpter(getChildFragmentManager(), data, childFragmentActionListener);
        viewPager.setAdapter(adapter);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_list);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_grid);
    }

    private ChildFragmentActionListener childFragmentActionListener = (type, image) -> {
        switch (type) {
            case DOWnLOAD:
                break;
        }
    };

    public interface ChildFragmentActionListener {

        void onAction(ActionType type, BaseImage image);

        enum ActionType {
            DOWnLOAD
        }
    }
}
