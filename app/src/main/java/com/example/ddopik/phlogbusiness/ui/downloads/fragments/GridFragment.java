package com.example.ddopik.phlogbusiness.ui.downloads.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.ui.downloads.model.GroupItem;
import com.example.ddopik.phlogbusiness.ui.downloads.view.DownloadsFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GridFragment extends Fragment {

    private List<GroupItem> data;
    private DownloadsFragment.ChildFragmentActionListener childFragmentActionListener;

    private RecyclerView recyclerView;

    public GridFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(List<GroupItem> data, DownloadsFragment.ChildFragmentActionListener childFragmentActionListener) {
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        GroupAdapter adapter = new GroupAdapter(data, childFragmentActionListener);
        adapter.setType(GroupAdapter.Type.GRID);
        recyclerView.setAdapter(adapter);
    }
}
