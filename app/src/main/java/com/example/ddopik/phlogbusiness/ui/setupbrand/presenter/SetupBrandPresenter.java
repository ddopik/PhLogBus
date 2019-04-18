package com.example.ddopik.phlogbusiness.ui.setupbrand.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.base.commonmodel.Industry;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.Doc;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.SetupBrandModel;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandActivity;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandView;

import java.util.List;

import io.reactivex.functions.Consumer;

public interface SetupBrandPresenter {

    void setView(SetupBrandView view);

    void terminate();

    ValidationResult shouldProceed(int currentStep, SetupBrandModel model);

    void loadIndustries(Consumer<List<Industry>> consumer, Context context);

    void setupBrand(SetupBrandModel model, Context context, Consumer<Boolean> consumer);

    void loadDocs(Consumer<List<Doc>> object, Context context);

    void verify(Context context, Consumer<Boolean> success);

    void getSetupBrandDetails(Context context, Consumer<Business> businessConsumer);

    class ValidationResult {
        public final boolean shouldProceed;
        public final int errorMessage;

        public ValidationResult(boolean shouldProceed, int errorMessage) {
            this.shouldProceed = shouldProceed;
            this.errorMessage = errorMessage;
        }
    }
}
