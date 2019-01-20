package com.example.ddopik.phlogbusiness.ui.customersupport.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerSupportFragment extends BaseFragment {

    private View mainView;
    private ImageView icon, loading;

    public CustomerSupportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return mainView = inflater.inflate(R.layout.fragment_customer_support, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        icon = mainView.findViewById(R.id.icon);
        loading = mainView.findViewById(R.id.loading);
        Glide.with(this)
                .load(R.drawable.ic_customer_support)
                .apply(RequestOptions.circleCropTransform())
                .into(icon);
        Glide.with(this)
                .load(R.drawable.ic_custom_loading)
                .into(loading);
    }

}
