package com.example.ddopik.phlogbusiness.ui.downloads.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.ui.downloads.model.DataItem;
import com.example.ddopik.phlogbusiness.ui.downloads.view.DownloadsFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GridFragment extends Fragment {

    private List<DataItem> data;
    private DownloadsFragment.ChildFragmentActionListener childFragmentActionListener;

    public GridFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(List<DataItem> data, DownloadsFragment.ChildFragmentActionListener childFragmentActionListener) {
        GridFragment fragment = new GridFragment();
        fragment.data = data;
        fragment.childFragmentActionListener = childFragmentActionListener;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }

}
