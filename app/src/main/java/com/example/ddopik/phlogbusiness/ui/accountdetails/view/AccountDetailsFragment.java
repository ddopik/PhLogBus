package com.example.ddopik.phlogbusiness.ui.accountdetails.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountDetailsFragment extends BaseFragment implements AccountDetailsView {

    private Business business;

    public static final String TAG = AccountDetailsFragment.class.getSimpleName();

    private ImageView profileImage, coverImage;

    public AccountDetailsFragment() {
        // Required empty public constructor
    }

    public static AccountDetailsFragment getInstance(Business messageToFragment) {
        AccountDetailsFragment fragment = new AccountDetailsFragment();
        fragment.business = messageToFragment;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_details, container, false);
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

    }

    @Override
    protected void initViews() {

    }

    private void setListeners() {
    }

}
