package com.example.ddopik.phlogbusiness.ui.setupbrand.fragment.stoptwo;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.Industry;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.SetupBrandModel;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandActivity;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandActivity.SubViewActionConsumer.ActionType;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepTwoFragment extends BaseFragment {

    private SetupBrandActivity.SubViewActionConsumer consumer;
    private SetupBrandModel model = new SetupBrandModel();

    private View mainView;
    private EditText industryET, phone, address, email, website;

    private Industry selectedIndustry;

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
        return mainView = inflater.inflate(R.layout.fragment_step_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (selectedIndustry != null) {
            industryET.setText(selectedIndustry.nameEn);
        }
        if (model.phone != null)
            phone.setText(model.phone);
        if (model.address != null)
            phone.setText(model.address);
        if (model.email != null)
            phone.setText(model.email);
        if (model.webSite != null)
            phone.setText(model.webSite);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        industryET = mainView.findViewById(R.id.industry_edit_text);
        consumer.accept(new SetupBrandActivity.SubViewActionConsumer.SubViewAction(ActionType.LOAD_INDUSTRIES, listConsumer));
        phone = mainView.findViewById(R.id.phone_edit_text);
        address = mainView.findViewById(R.id.address_edit_text);
        email = mainView.findViewById(R.id.email_edit_text);
        website = mainView.findViewById(R.id.website_edit_text);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {
        TextWatcher phoneWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                model.phone = phone.getText().toString();
                consumer.accept(new SetupBrandActivity.SubViewActionConsumer.SubViewAction(ActionType.PHONE, model.phone));
            }
        };
        phone.addTextChangedListener(phoneWatcher);
        TextWatcher addressWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                model.address = address.getText().toString();
                consumer.accept(new SetupBrandActivity.SubViewActionConsumer.SubViewAction(ActionType.ADDRESS, model.address));
            }
        };
        address.addTextChangedListener(addressWatcher);
        TextWatcher emailWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                model.email = email.getText().toString();
                consumer.accept(new SetupBrandActivity.SubViewActionConsumer.SubViewAction(ActionType.EMAIL, model.email));
            }
        };
        email.addTextChangedListener(emailWatcher);
        TextWatcher websiteWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                model.webSite = website.getText().toString();
                consumer.accept(new SetupBrandActivity.SubViewActionConsumer.SubViewAction(ActionType.WEBSITE, model.webSite));
            }
        };
        website.addTextChangedListener(websiteWatcher);
        industryET.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP)
                if (industries != null) {
                    SelectFromStringListDialogFragment fragment = SelectFromStringListDialogFragment.newInstance(industries);
                    fragment.setConsumer(model -> {
                        selectedIndustry = (Industry) model;
                        industryET.setText(selectedIndustry.nameEn);
                        phone.requestFocus();
                        consumer.accept(new SetupBrandActivity.SubViewActionConsumer.SubViewAction(ActionType.INDUSTRY, selectedIndustry));
                        fragment.dismiss();
                    });
                    fragment.show(getChildFragmentManager(), SelectFromStringListDialogFragment.TAG);
                }
            return false;
        });
    }

    private List<Industry> industries;

    private Consumer<List<Industry>> listConsumer = industries -> {
        this.industries = industries;
    };
}
