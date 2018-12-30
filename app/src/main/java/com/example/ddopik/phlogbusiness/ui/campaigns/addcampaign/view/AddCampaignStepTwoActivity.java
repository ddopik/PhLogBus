package com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.Industry;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.model.AddCampaignRequestModel;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.presenter.AddCampaignPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.presenter.AddCampaignStepTwoPresenter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;


public class AddCampaignStepTwoActivity extends BaseActivity implements AddCampaignStepTwoActivityView {
    public static String CAMPAIGN_DATA = "campaign_data";
    private AddCampaignRequestModel addCampaignRequestModel;

    private TextInputLayout campaignDescInput, campaignPrizeInput;
    private EditText campaignDescription, campiagnPrize;
    private AutoCompleteTextView autoCompleteTextView;
    private List<Industry> industryList = new ArrayList<Industry>();
    private List<Industry> industriesMenuList = new ArrayList<Industry>();
    private CampaignIndustryAdapter campaignIndustryAdapter;
    private AutoCompleteCampaignIndustryMenuAdapter autoCompleteCampaignIndustryMenuAdapter;
    private AddCampaignStepTwoPresenter addCampaignStepTwoPresenter;
    private Button nextBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_campaign_step_two);
        if (getIntent().getParcelableExtra(CAMPAIGN_DATA) != null) {
            addCampaignRequestModel = getIntent().getParcelableExtra(CAMPAIGN_DATA);
            initPresenter();
            initView();
            initListener();

            addCampaignStepTwoPresenter.getIndustries(this,getBaseContext());

        }
    }

    @Override
    public void initView() {

        campaignDescInput = findViewById(R.id.campaign_description_input);
        campaignPrizeInput = findViewById(R.id.campaign_prize_input);
        campaignDescription = findViewById(R.id.campaign_description);
        campiagnPrize = findViewById(R.id.campaign_prize);

        autoCompleteTextView = findViewById(R.id.campaign_tag_auto_complete);
        CustomRecyclerView tagsRv = findViewById(R.id.campaigns_tags_rv);
        nextBtn = findViewById(R.id.add_campaign_step_two_next_btn);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getApplicationContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        tagsRv.setLayoutManager(flexboxLayoutManager);

        // Set adapter object.
        campaignIndustryAdapter = new CampaignIndustryAdapter(industryList);

        tagsRv.setAdapter(campaignIndustryAdapter);

//        backBtn = findViewById(R.id.back_btn);
    }
    private void initListener() {
        campaignIndustryAdapter.onSelectedItemClicked = industry -> {

            List<Industry> tempList = new ArrayList<Industry>();
            for (int i = 0; i < industryList.size(); i++) {
                if (!industryList.get(i).nameEn.equals(industry.nameEn)) {
                    tempList.add(industryList.get(i));
                }

            }
            industryList.clear();
            industryList.addAll(tempList);
            campaignIndustryAdapter.notifyDataSetChanged();


        };
        initKeyBoardListener();

        nextBtn.setOnClickListener(v -> {

            if (validInputs()) {
                addCampaignRequestModel.campaignDescription = campaignDescription.getText().toString();
                addCampaignRequestModel.campaignPrize = campiagnPrize.getText().toString();

                Intent intent = new Intent(this, AddCampaignStepThreeActivity.class);
                intent.putExtra(AddCampaignStepTwoActivity.CAMPAIGN_DATA, addCampaignRequestModel);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

//        backBtn.setOnClickListener((view) -> onBackPressed());


    }

    private boolean validInputs() {

        boolean inputStates = true;

        if (campaignDescription.getText().length() == 0) {
            campaignDescInput.setError(getResources().getString(R.string.invalid_description_value));
            inputStates = false;
        } else {
            campaignDescInput.setErrorEnabled(false);
        }

        if (campiagnPrize.getText().length() == 0) {
            campaignPrizeInput.setError(getResources().getString(R.string.invalid_prize_value));
            inputStates = false;

        } else {

            campaignPrizeInput.setErrorEnabled(false);

        }

        return inputStates;
    }
    @Override
    public void initPresenter() {
        addCampaignStepTwoPresenter=new AddCampaignPresenterImpl();
    }


    private void initKeyBoardListener() {
        //Threshold for minimal keyboard height.
        final int MIN_KEYBOARD_HEIGHT_PX = 150;
        //уровня view. Top-level window decor view.
        final View decorView = getWindow().getDecorView();
        //Register global layout listener.
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            // Retrieve visible rectangle inside window.
            private final Rect windowVisibleDisplayFrame = new Rect();
            private int lastVisibleDecorViewHeight;

            @Override
            public void onGlobalLayout() {
                decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
                final int visibleDecorViewHeight = windowVisibleDisplayFrame.height();

                if (lastVisibleDecorViewHeight != 0) {
                    if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {


                    } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                        //Key board is visible
                        Industry newIndustry = new Industry();
                        newIndustry.nameEn = autoCompleteTextView.getText().toString().toLowerCase();
                        boolean tagAlreadyExist = false;
                        for (Industry industry : industryList) {
                            String industryName= industry.nameEn.toLowerCase();
                            if (industryName.equals(newIndustry.nameEn))
                                tagAlreadyExist = true;
                        }
                        if (!tagAlreadyExist && newIndustry.nameEn.length()>0){
                            addSelectedIndustry(newIndustry);
                        }

                    }
                }
                //
                // Save current decor view height for the next call.
                lastVisibleDecorViewHeight = visibleDecorViewHeight;
            }
        });
    }

    private void addSelectedIndustry(Industry industry) {
        industryList.add(industry);
        campaignIndustryAdapter.notifyDataSetChanged();
        autoCompleteTextView.dismissDropDown();

        autoCompleteTextView.setText("");
        autoCompleteTextView.clearFocus();
        hideSoftKeyBoard();

    }

    @Override
    public void viewIndustries(List<Industry> industryList) {

        autoCompleteCampaignIndustryMenuAdapter = new AutoCompleteCampaignIndustryMenuAdapter(this, R.layout.item_drop_down, industryList);
        autoCompleteTextView.setAdapter(autoCompleteCampaignIndustryMenuAdapter);
        autoCompleteTextView.setThreshold(0);
        autoCompleteCampaignIndustryMenuAdapter.setNotifyOnChange(true);
        autoCompleteCampaignIndustryMenuAdapter.notifyDataSetChanged();


        autoCompleteCampaignIndustryMenuAdapter.onMenuItemClicked = this::addSelectedIndustry;


    }
    private  void hideSoftKeyBoard () {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
