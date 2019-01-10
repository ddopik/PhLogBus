package com.example.ddopik.phlogbusiness.ui.setupbrand.fragment.stoptwo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepTwoFragment extends BaseFragment {

    private SetupBrandActivity.SubViewActionConsumer consumer;

    public StepTwoFragment() {
        // Required empty public constructor
    }


    public static StepTwoFragment newInstance(SetupBrandActivity.SubViewActionConsumer consumer) {
        StepTwoFragment fragment = new StepTwoFragment();
        fragment.consumer = consumer;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_two, container, false);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {

    }

}
