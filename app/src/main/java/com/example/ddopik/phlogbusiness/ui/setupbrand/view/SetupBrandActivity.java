package com.example.ddopik.phlogbusiness.ui.setupbrand.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.widgets.CustomViewPager;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.SetupBrandModel;
import com.example.ddopik.phlogbusiness.ui.setupbrand.presenter.SetupBrandPresenter;
import com.example.ddopik.phlogbusiness.ui.setupbrand.presenter.SetupBrandPresenterImpl;

public class SetupBrandActivity extends BaseActivity implements SetupBrandView {

    private SetupBrandPresenter presenter;
    private SetupBrandModel model = new SetupBrandModel();

    private CustomViewPager viewPager;
    private SetupBrandPagerAdapter pagerAdapter;
    private ProgressBar progressBar;
    private Button actionButton;

    private int currentStep = 1;

    private CustomViewPager.Supplier supplier = () -> {
        SetupBrandPresenter.ValidationResult result = presenter.shouldProceed(currentStep, model);
        if (!result.shouldProceed)
            showToast(getString(result.errorMessage));
        return result.shouldProceed;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_brand);

        initView();
        initPresenter();
    }

    @Override
    public void initView() {
        viewPager = findViewById(R.id.steps_view_pager);
        pagerAdapter = new SetupBrandPagerAdapter(getSupportFragmentManager(), subViewActionConsumer);
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setSupplier(supplier);
        viewPager.setAdapter(pagerAdapter);
        progressBar = findViewById(R.id.steps_progress_bar);
        setStepsProgress(100 / 3);
        actionButton = findViewById(R.id.action_button);

        setListeners();
    }

    private void setListeners() {
        actionButton.setOnClickListener(v -> {
            switch (currentStep) {
                case 1:
                    presenter.shouldProceed(currentStep, model);
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        });
    }

    private void validate() {
        SetupBrandPresenter.ValidationResult result = presenter.shouldProceed(currentStep, model);
        viewPager.setCanSwipeLeft(result.shouldProceed);
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
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            setStepsProgress((int) (((i + v + 1) * 1 / 3) * 100));
        }

        @Override
        public void onPageSelected(int i) {
            currentStep = i + 1;
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };

    private SubViewActionConsumer subViewActionConsumer = action -> {
        switch (action.action) {
            case ARABIC_NAME:
                model.arabicBrandName = (String) action.object;
                break;
            case ENGLISH_NAME:
                model.englishBrandName = (String) action.object;
                break;
            case COVER_PHOTO:
                model.cover = (String) action.object;
                break;
            case THUMBNAIL_PHOTO:
                model.thumbnail = (String) action.object;
                break;
        }
        validate();
    };

    public interface SubViewActionConsumer {

        void accept(SubViewAction action);

        enum ActionType {
            THUMBNAIL_PHOTO, COVER_PHOTO, ARABIC_NAME, ENGLISH_NAME
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
