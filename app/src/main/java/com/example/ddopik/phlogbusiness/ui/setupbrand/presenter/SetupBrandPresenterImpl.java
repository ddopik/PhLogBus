package com.example.ddopik.phlogbusiness.ui.setupbrand.presenter;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.SetupBrandModel;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandView;

public class SetupBrandPresenterImpl implements SetupBrandPresenter {

    private SetupBrandView view;

    @Override
    public void setView(SetupBrandView view) {
        this.view = view;
    }

    @Override
    public ValidationResult shouldProceed(int currentStep, SetupBrandModel model) {
        switch (currentStep) {
            case 1:
                if (model.cover == null)
                    return new ValidationResult(false, R.string.error_missing_cover);
                else if (model.thumbnail == null)
                    return new ValidationResult(false, R.string.error_missing_thumbnail);
                else if (model.arabicBrandName == null || model.arabicBrandName.isEmpty())
                    return new ValidationResult(false, R.string.error_missing_arabic_name);
                else if (model.englishBrandName == null || model.englishBrandName.isEmpty())
                    return new ValidationResult(false, R.string.error_missing_english_name);
                else return new ValidationResult(true, 0);
            case 2:
                break;
            case 3:
                break;
        }
        return null;
    }
}
