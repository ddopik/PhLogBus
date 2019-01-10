package com.example.ddopik.phlogbusiness.ui.setupbrand.presenter;

import com.example.ddopik.phlogbusiness.ui.setupbrand.model.SetupBrandModel;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandView;

public interface SetupBrandPresenter {

    void setView(SetupBrandView view);

    ValidationResult shouldProceed(int currentStep, SetupBrandModel model);

    class ValidationResult {
        public final boolean shouldProceed;
        public final int errorMessage;

        public ValidationResult(boolean shouldProceed, int errorMessage) {
            this.shouldProceed = shouldProceed;
            this.errorMessage = errorMessage;
        }
    }
}
