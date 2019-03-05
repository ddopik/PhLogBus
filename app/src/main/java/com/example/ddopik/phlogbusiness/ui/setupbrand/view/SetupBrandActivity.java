package com.example.ddopik.phlogbusiness.ui.setupbrand.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.base.commonmodel.Industry;
import com.example.ddopik.phlogbusiness.base.widgets.CustomViewPager;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.Doc;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.SetupBrandModel;
import com.example.ddopik.phlogbusiness.ui.setupbrand.presenter.SetupBrandPresenter;
import com.example.ddopik.phlogbusiness.ui.setupbrand.presenter.SetupBrandPresenterImpl;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

public class SetupBrandActivity extends BaseActivity implements SetupBrandView {

    private SetupBrandPresenter presenter;
    private SetupBrandModel model = new SetupBrandModel();

    private CustomViewPager viewPager;
    private SetupBrandPagerAdapter pagerAdapter;
    private ProgressBar progressBar;
    private Button actionButton;
    private ProgressBar loading;

    private int currentStep = 1;

    private boolean errorDialogShowing = false;
    private CustomViewPager.OnSwipeLeftListener onSwipeLeftListener = () -> {
        SetupBrandPresenter.ValidationResult result = presenter.shouldProceed(currentStep, model);
        if (!result.shouldProceed) {
            showErrorMessage(result);
        }
    };

    private void showErrorMessage(SetupBrandPresenter.ValidationResult result) {
        if (!errorDialogShowing) {
            errorDialogShowing = true;
            new AlertDialog.Builder(this)
                    .setCancelable(true)
                    .setMessage(result.errorMessage)
                    .setOnCancelListener(dialog -> errorDialogShowing = false)
                    .setPositiveButton(R.string.ok, (dialog, which) -> {
                        dialog.dismiss();
                        errorDialogShowing = false;
                    }).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_brand);
        handleIntent();
        initView();
        initPresenter();
    }

    @Override
    public void onBackPressed() {
        if (currentStep == 1)
            super.onBackPressed();
        else
            viewPager.setCurrentItem(currentStep - 2);
    }

    @Override
    public void setLoading(boolean b) {
        if (b)
            loading.setVisibility(View.VISIBLE);
        else {
            loading.setVisibility(View.INVISIBLE);
        }
    }

    private Business business;

    private void handleIntent() {
        business = getIntent().getParcelableExtra("business");
        if (business == null)
            return;
        model.cover = business.brandImageCover;
        model.thumbnail = business.brandThumbnail;
        model.arabicBrandName = business.nameAr;
        model.englishBrandName = business.nameEn;
        if (business.industry != null)
            model.industryId = business.industry.id;
        model.phone = business.brandPhone;
        model.address = business.brandAddress;
        model.email = business.email;
        model.webSite = business.website;
        model.desc = business.description;
    }

    @Override
    public void initView() {
        viewPager = findViewById(R.id.steps_view_pager);
        pagerAdapter = new SetupBrandPagerAdapter(getSupportFragmentManager(), subViewActionConsumer, business);
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setAllowedSwipeDirection(CustomViewPager.SwipeDirection.RIGHT);
        viewPager.setOnSwipeLeftListener(onSwipeLeftListener);
        viewPager.setAdapter(pagerAdapter);
        progressBar = findViewById(R.id.steps_progress_bar);
        setStepsProgress(100 / 3);
        actionButton = findViewById(R.id.action_button);
        loading = findViewById(R.id.loading);
        loading.setVisibility(View.INVISIBLE);

        setListeners();
    }

    private void setListeners() {
        actionButton.setOnClickListener(v -> {
            SetupBrandPresenter.ValidationResult result = presenter.shouldProceed(currentStep, model);
            switch (currentStep) {
                case 1:
                    if (result.shouldProceed)
                        viewPager.setCurrentItem(1);
                    else
                        showErrorMessage(result);
                    break;
                case 2:
                    if (changed) {
                        if (result.shouldProceed)
                            presenter.setupBrand(model, this, aBoolean -> {
                                if (aBoolean)
                                    viewPager.setCurrentItem(2);
                            });
                        else
                            showErrorMessage(result);
                    } else
                        viewPager.setCurrentItem(2);
                    break;
                case 3:
                    if (docs == null || docs.isEmpty())
                        break;
                    presenter.loadDocs(docs -> {
                        boolean allDocsUploaded = true;
                        for (Doc doc : docs) {
                            if (doc.getUploadedFile() == null || doc.getUploadedFile().getUrl() == null || doc.getUploadedFile().getUrl().isEmpty()) {
                                allDocsUploaded = false;
                                break;
                            }
                        }
                        if (allDocsUploaded)
                            presenter.verify(getBaseContext());
                        else
                            showToast(getString(R.string.upload_all_docs));
                    }, getBaseContext());
                    break;
            }
        });
    }

    private void validate() {
        SetupBrandPresenter.ValidationResult result = presenter.shouldProceed(currentStep, model);
    }

    private void setStepsProgress(int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(i, true);
        } else {
            progressBar.setProgress(i);
        }
    }

    @Override
    public void initPresenter() {
        presenter = new SetupBrandPresenterImpl();
        presenter.setView(this);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            setStepsProgress((int) (((i + v + 1) * 1 / 3) * 100));
        }

        @Override
        public void onPageSelected(int i) {
            currentStep = i + 1;
            setActionButtonText();
            validate();
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };

    private void setActionButtonText() {
        switch (currentStep) {
            case 1:
                actionButton.setText(R.string.next);
                break;
            case 2:
                actionButton.setText(R.string.next);
                break;
            case 3:
                actionButton.setText(R.string.request_verification);
                break;
        }
    }

    private boolean changed = false;

    private List<Doc> docs;
    private SubViewActionConsumer subViewActionConsumer = action -> {
        switch (action.action) {
            case ARABIC_NAME:
                if (model.arabicBrandName != null && model.arabicBrandName.equals(action.object))
                    break;
                changed = true;
                model.arabicBrandName = (String) action.object;
                break;
            case ENGLISH_NAME:
                if (model.englishBrandName != null && model.englishBrandName.equals(action.object))
                    break;
                changed = true;
                model.englishBrandName = (String) action.object;
                break;
            case COVER_PHOTO:
                changed = true;
                model.cover = (String) action.object;
                model.coverChanged = true;
                break;
            case THUMBNAIL_PHOTO:
                changed = true;
                model.thumbnail = (String) action.object;
                model.thumbnailChanged = true;
                break;
            case LOAD_INDUSTRIES:
                if (action.object instanceof Consumer) {
                    presenter.loadIndustries((Consumer<List<Industry>>) action.object, getBaseContext());
                }
                break;
            case INDUSTRY:
                changed = true;
                model.industryId = ((Industry) action.object).id;
                break;
            case PHONE:
                if (model.phone != null && model.phone.equals(action.object))
                    break;
                changed = true;
                model.phone = (String) action.object;
                break;
            case ADDRESS:
                if (model.address != null && model.address.equals(action.object))
                    break;
                changed = true;
                model.address = (String) action.object;
                break;
            case EMAIL:
                if (model.email != null && model.email.equals(action.object))
                    break;
                changed = true;
                model.email = (String) action.object;
                break;
            case WEBSITE:
                if (model.webSite != null && model.webSite.equals(action.object))
                    break;
                changed = true;
                model.webSite = (String) action.object;
                break;
            case GET_DOCUMENT_LIST:
                if (action.object instanceof Consumer) {
                    presenter.loadDocs(docs -> {
                        this.docs = docs;
                        ((Consumer<List<Doc>>) action.object).accept(docs);
                    }, getBaseContext());
                    break;
                }
                break;
            case DESC:
                if (model.desc != null && model.desc.equals(action.object))
                    break;
                changed = true;
                model.desc = (String) action.object;
                break;
            case DOC_UPLOADED:
                Doc doc = (Doc) action.object;
                if (docs != null) {
                    for (Doc d : docs) {
                        if (d.getId() == doc.getId()) {
                            break;
                        }
                    }
                }
                break;
        }
        validate();
    };

    @Override
    public void setVerificationRequestSuccess(boolean success) {
        if (success) {
            showToast(getString(R.string.verfication_request_sent));
        } else {
            showToast(getString(R.string.verfication_request_error));
        }
    }

    public interface SubViewActionConsumer {

        void accept(SubViewAction action);

        enum ActionType {
            THUMBNAIL_PHOTO, COVER_PHOTO, ARABIC_NAME, LOAD_INDUSTRIES, INDUSTRY, PHONE, ADDRESS, EMAIL, WEBSITE, ENGLISH_NAME, COMMERCIAL_RECORD, DESC, GET_DOCUMENT_LIST, DOC_UPLOADED, TAXES_RECORD
        }

        class SubViewAction {
            public final ActionType action;
            public final Object object;

            public SubViewAction(ActionType action, Object object) {
                this.action = action;
                this.object = object;
            }
        }
    }
}
