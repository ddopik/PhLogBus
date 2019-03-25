package com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view;

import android.content.Intent;
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
import com.example.ddopik.phlogbusiness.ui.MainActivity;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.model.AddCampaignRequestModel;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.presenter.AddCampaignPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.presenter.AddCampaignStepThreePresenter;
import com.example.ddopik.phlogbusiness.utiltes.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCampaignStepThreeActivity extends BaseActivity implements AddCampaignStepThreeActivityView {

    public static String CAMPAIGN_DATA = "campaign_data";
    private TextInputLayout campaignStartDateInput, campaignEndDateInput,numberOfWinneresInput;
    private EditText campaignStartDate, campaignEndDate,numberOfWinneres;
    private Button submitCampaignBtn, submitCampaignDraftBtn;
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
            setCampaignData();
            initPresenter();
            initListeners();
        }

    }

    private void setCampaignData() {
        if (addCampaignRequestModel != null) {
            if (addCampaignRequestModel.campaignStartDate != null)
                campaignStartDate.setText(addCampaignRequestModel.campaignStartDate);
            if (addCampaignRequestModel.campaignEndDate != null)
                campaignEndDate.setText(addCampaignRequestModel.campaignEndDate);
            if (addCampaignRequestModel.winnersNumber != null)
                numberOfWinneres.setText(addCampaignRequestModel.winnersNumber);
        } else {
            addCampaignRequestModel = new AddCampaignRequestModel();
        }
    }

    @Override
    public void initView() {
        campaignStartDateInput = findViewById(R.id.campaign_start_date_input);
        campaignEndDateInput = findViewById(R.id.campaign_end_date_input);
        numberOfWinneresInput = findViewById(R.id.number_of_winner_input);
        campaignStartDate = findViewById(R.id.campaign_start_date);
        campaignEndDate = findViewById(R.id.campaign_end_date);
        numberOfWinneres = findViewById(R.id.number_of_winner);
        submitCampaignBtn = findViewById(R.id.add_campaign_step_three_next_btn);
        submitCampaignDraftBtn = findViewById(R.id.set_campaign_draft);
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
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    String dateString = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                    campaignStartDate.setText(dateString);
                }
            });
            pickDateDialog.show(getSupportFragmentManager(), PickDateDialog.class.getSimpleName());

        });


        campaignEndDate.setOnClickListener(v -> {
            PickDateDialog pickDateDialog = new PickDateDialog();
            pickDateDialog.setOnDateSet((year, month, day) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                String dateString = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                campaignEndDate.setText(dateString);
            });
            pickDateDialog.show(getSupportFragmentManager(), PickDateDialog.class.getSimpleName());

        });


        submitCampaignBtn.setOnClickListener(v -> {
            if (isInputValid()) {

                addCampaignRequestModel.campaignStartDate=campaignStartDate.getText().toString();
                addCampaignRequestModel.campaignEndDate =campaignEndDate.getText().toString();
                addCampaignRequestModel.winnersNumber=numberOfWinneres.getText().toString();
                addCampaignRequestModel.isDraft = "false";
                addCampaignStepThreePresenter.submitCampaign(this,addCampaignRequestModel,getBaseContext());
            }
        });


        submitCampaignDraftBtn.setOnClickListener(v -> {
            if (isInputValid()) {

                addCampaignRequestModel.campaignStartDate = campaignStartDate.getText().toString();
                addCampaignRequestModel.campaignEndDate = campaignEndDate.getText().toString();
                addCampaignRequestModel.winnersNumber=numberOfWinneres.getText().toString();
                addCampaignRequestModel.isDraft = "true";
                addCampaignStepThreePresenter.submitCampaign(this, addCampaignRequestModel, getBaseContext());
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
        if (numberOfWinneres.getText().length() == 0) {
            numberOfWinneresInput.setError(getResources().getString(R.string.winners_number_required));
            inputState = false;
        } else {
            numberOfWinneresInput.setErrorEnabled(false);
        }

        if (!datesAreValid(campaignStartDate.getText().toString(), campaignEndDate.getText().toString())) {
            inputState = false;
            showToast(getString(R.string.dates_not_valid));
        }

        return inputState;
    }

    private boolean datesAreValid(String start, String end) {
        try {
            Date startDate = getDate(start);
            Date endDate = getDate(end);
            return startDate.before(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Date getDate(String d) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse(d);
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
    public void onCampaignCompleted() {
        Intent intents = new Intent(this, MainActivity.class);
        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intents.putExtra(Constants.MainActivityRedirectionValue.VALUE, Constants.MainActivityRedirectionValue.TO_CAMPAIGNS);
        startActivity(intents);
        finish();
        finish();
    }

    @Override
    public void viewMessage(String msg) {
        showToast(msg);
    }
}
