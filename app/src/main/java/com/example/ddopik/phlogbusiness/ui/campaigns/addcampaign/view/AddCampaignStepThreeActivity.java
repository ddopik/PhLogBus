package com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.widgets.PickDateDialog;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.model.AddCampaignRequestModel;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.presenter.AddCampaignPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.presenter.AddCampaignStepThreePresenter;

public class AddCampaignStepThreeActivity extends BaseActivity implements AddCampaignStepThreeActivityView {

    public static String CAMPAIGN_DATA = "campaign_data";
    private TextInputLayout campaignStartDateInput, campaignEndDateInput;
    private EditText campaignStartDate, campaignEndDate;
    private Button submitCampaignBtn;
    private ProgressBar submitCampaignProgress;
    private AddCampaignRequestModel addCampaignRequestModel;
    private AddCampaignStepThreePresenter addCampaignStepThreePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_campaign_step_three);
        if (getIntent().getParcelableExtra(CAMPAIGN_DATA) != null) {
            addCampaignRequestModel = getIntent().getParcelableExtra(CAMPAIGN_DATA);
            initView();
            initPresenter();
            initListeners();
        }

    }

    @Override
    public void initView() {
        campaignStartDateInput = findViewById(R.id.campaign_start_date_input);
        campaignEndDateInput = findViewById(R.id.campaign_end_date_input);
        campaignStartDate = findViewById(R.id.campaign_start_date);
        campaignEndDate = findViewById(R.id.campaign_end_date);
        submitCampaignBtn = findViewById(R.id.add_campaign_step_three_next_btn);
        submitCampaignBtn = findViewById(R.id.add_campaign_step_three_next_btn);
        submitCampaignProgress = findViewById(R.id.submit_campaign_progress);
    }

    @Override
    public void initPresenter() {
        addCampaignStepThreePresenter = new AddCampaignPresenterImpl();
    }

    private void initListeners() {

        campaignStartDate.setOnClickListener(v -> {
            PickDateDialog pickDateDialog = new PickDateDialog();
            pickDateDialog.setOnDateSet(new PickDateDialog.OnDateSet() {
                @Override
                public void onDateSet(int year, int month, int day) {
                    String dateString = year + "-" + (month + 1) + "-" + day;
                    campaignStartDate.setText(dateString);
                }
            });
            pickDateDialog.show(getSupportFragmentManager(), PickDateDialog.class.getSimpleName());
        });


        campaignEndDate.setOnClickListener(v -> {
            PickDateDialog pickDateDialog = new PickDateDialog();
            pickDateDialog.setOnDateSet(new PickDateDialog.OnDateSet() {
                @Override
                public void onDateSet(int year, int month, int day) {
                    String dateString = year + "-" + (month + 1) + "-" + day;
                    campaignEndDate.setText(dateString);
                }
            });
            pickDateDialog.show(getSupportFragmentManager(), PickDateDialog.class.getSimpleName());
        });


        submitCampaignBtn.setOnClickListener(v -> {
            if (isInputValid()) {

                addCampaignRequestModel.campaignStartDate=campaignStartDate.getText().toString();
                addCampaignRequestModel.campaignEndDate =campaignEndDate.getText().toString();

                addCampaignStepThreePresenter.submitCampaign(this,addCampaignRequestModel,getBaseContext());
            }
        });

    }


    private boolean isInputValid() {
        boolean inputState = true;

        if (campaignStartDate.getText().length() == 0) {
            campaignStartDateInput.setError(getResources().getString(R.string.please_select_start_date));
            inputState = false;
        } else {
            campaignStartDateInput.setErrorEnabled(false);
        }
        if (campaignEndDate.getText().length() == 0) {
            campaignEndDateInput.setError(getResources().getString(R.string.please_select_end_date));
            inputState = false;
        } else {
            campaignEndDateInput.setErrorEnabled(false);
        }

        return inputState;
    }

    @Override
    public void viewSubmitCampaignProgress(Boolean state) {
        if (state) {
            submitCampaignProgress.setVisibility(View.VISIBLE);
        } else {
            submitCampaignProgress.setVisibility(View.GONE);
        }

    }

    @Override
    public void viewMessage(String msg) {
        showToast(msg);
    }
}
