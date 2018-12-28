package com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.Utilities;
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

import static android.support.v4.content.ContextCompat.getSystemService;


public class AddCampaignStepTwoActivity extends BaseActivity implements AddCampaignStepTwoActivityView {
    public static String CAMPAIGN_DATA = "campaign_data";
    private AddCampaignRequestModel addCampaignRequestModel;
    private AutoCompleteTextView autoCompleteTextView;
    private List<Industry> industryList = new ArrayList<Industry>();
    private List<Industry> industriesMenuList = new ArrayList<Industry>();
    private CampaignIndustryAdapter campaignIndustryAdapter;
    private AutoCompleteCampaignIndustryMenuAdapter autoCompleteCampaignIndustryMenuAdapter;
    private AddCampaignStepTwoPresenter addCampaignStepTwoPresenter;
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


        autoCompleteTextView = findViewById(R.id.campaign_tag_auto_complete);
        CustomRecyclerView tagsRv = findViewById(R.id.campaigns_tags_rv);







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

//        backBtn.setOnClickListener((view) -> onBackPressed());

//        uploadBrn.setOnClickListener(v -> {
//            addTagActivityPresenter.uploadPhoto(imagePreviewPath, imageCaption, imageLocation, draftState, imageType, industryList);
//        });
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



//        this.industriesMenuList.clear();
//        this.industriesMenuList.addAll(industryList);
//        autoCompleteCampaignIndustryMenuAdapter.notifyDataSetInvalidated();
//        autoCompleteCampaignIndustryMenuAdapter.notifyDataSetChanged();
    }
    private  void hideSoftKeyBoard () {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
