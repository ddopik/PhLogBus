package com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.Tag;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.model.AddCampaignRequestModel;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.presenter.AddCampaignPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.presenter.AddCampaignStepTwoPresenter;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AddCampaignStepTwoActivity extends BaseActivity implements AddCampaignStepTwoActivityView {

    private String TAG = AddCampaignStepTwoActivity.class.getSimpleName();
    public static String CAMPAIGN_DATA = "campaign_data";
    private AddCampaignRequestModel addCampaignRequestModel;

    private TextInputLayout campaignDescInput, campaignPrizeInput;
    private EditText campaignDescription, campaignPrize;
    private AutoCompleteTextView autoCompleteTextView;
    private List<Tag> tagList = new ArrayList<Tag>();
    private TagsAdapter tagsAdapter;
    private CompositeDisposable disposable = new CompositeDisposable();
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
        }
    }

    @Override
    public void initView() {

        campaignDescInput = findViewById(R.id.campaign_description_input);
        campaignPrizeInput = findViewById(R.id.campaign_prize_input);
        campaignDescription = findViewById(R.id.campaign_description);
        campaignPrize = findViewById(R.id.campaign_prize);

        autoCompleteTextView = findViewById(R.id.campaign_tag_auto_complete);
        CustomRecyclerView tagsRv = findViewById(R.id.campaigns_tags_rv);
        nextBtn = findViewById(R.id.add_campaign_step_two_next_btn);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getApplicationContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        tagsRv.setLayoutManager(flexboxLayoutManager);

        // Set adapter object.
        tagsAdapter = new TagsAdapter(tagList);

        tagsRv.setAdapter(tagsAdapter);

//        backBtn = findViewById(R.id.back_btn);
    }
    private void initListener() {
        tagsAdapter.onSelectedItemClicked = industry -> {

            List<Tag> tempList = new ArrayList<Tag>();
            /**
             *OnItem Delete
             */
            for (int i = 0; i < tagList.size(); i++) {
                if (!tagList.get(i).name.equals(industry.name)) {
                    tempList.add(tagList.get(i));
                }

            }
            tagList.clear();
            tagList.addAll(tempList);
            tagsAdapter.notifyDataSetChanged();

        };

        disposable.add(

                RxTextView.textChangeEvents(autoCompleteTextView)
                        .skipInitialValue()
                        .debounce(Constants.QUERY_SEARCH_TIME_OUT, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(searchTagQuery())
        );



        initKeyBoardListener();


        nextBtn.setOnClickListener(v -> {

            if (validInputs()) {
                addCampaignRequestModel.campaignDescription = campaignDescription.getText().toString();
                addCampaignRequestModel.campaignPrize = campaignPrize.getText().toString();
                addCampaignRequestModel.tagList = tagList;
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

        if (campaignPrize.getText().length() == 0) {
            campaignPrizeInput.setError(getResources().getString(R.string.invalid_prize_value));
            inputStates = false;

        } else {

            campaignPrizeInput.setErrorEnabled(false);

        }


        if (tagList.size() == 0) {
            autoCompleteTextView.setError(getResources().getString(R.string.please_add_more_industry));
            inputStates = false;

        }

        return inputStates;
    }
    @Override
    public void initPresenter() {
        addCampaignStepTwoPresenter=new AddCampaignPresenterImpl();
    }


    /**
     * KeyBoard Listeners
     * to capture text in autoCompleteText View when user dismiss SoftKeyBoard
     **/
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
                        Tag newTag = new Tag();
                        newTag.name = autoCompleteTextView.getText().toString().toLowerCase();
                        boolean tagAlreadyExist = false;
                        for (Tag tag : tagList) {
                            String industryName = tag.name.toLowerCase();
                            if (industryName.equals(newTag.name))
                                tagAlreadyExist = true;
                        }
                        if (!tagAlreadyExist && newTag.name.length() > 0) {
                            addSelectedTag(newTag);
                        }


                    }
                }


                //
                // Save current decor view height for the next call.
                lastVisibleDecorViewHeight = visibleDecorViewHeight;
            }
        });
    }

    private void addSelectedTag(Tag tag) {
        tagList.add(tag);
        tagsAdapter.notifyDataSetChanged();
        autoCompleteTextView.dismissDropDown();

        autoCompleteTextView.setText("");
        autoCompleteTextView.clearFocus();
        Utilities.hideKeyboard(this);

    }


    private DisposableObserver<TextViewTextChangeEvent> searchTagQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                // user cleared search get default data
                addCampaignStepTwoPresenter.getTags(AddCampaignStepTwoActivity.this, getBaseContext(),autoCompleteTextView.getText().toString().trim());
                Log.e(TAG, "search string: " + autoCompleteTextView.getText().toString().trim());

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void viewTags(List<Tag> tagList) {

        AutoCompleteTagsMenuAdapter autoCompleteTagsMenuAdapter = new AutoCompleteTagsMenuAdapter(this, R.layout.item_drop_down, tagList);
        autoCompleteTextView.setAdapter(autoCompleteTagsMenuAdapter);
        autoCompleteTextView.setThreshold(0);
        autoCompleteTagsMenuAdapter.setNotifyOnChange(true);
        autoCompleteTagsMenuAdapter.notifyDataSetChanged();
        autoCompleteTagsMenuAdapter.onMenuItemClicked = this::addSelectedTag;


    }

}


