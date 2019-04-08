package com.example.ddopik.phlogbusiness.ui.accountdetails.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.ui.accountdetails.model.AccountDetailsModel;
import com.example.ddopik.phlogbusiness.ui.accountdetails.view.AccountDetailsView;

public interface AccountDetailsPresenter {
    
    void setView(AccountDetailsView view);
    
    ValidationResult validate(AccountDetailsModel model);

    void saveProfile(Context context, AccountDetailsModel model);

    void terminate();

      void changePassword(Context context, String oldPassword, String newPassword) ;

    class ValidationResult {
        public final boolean valid;
        public final int errorMessage;

        public ValidationResult(boolean valid, int errorMessage) {
            this.valid = valid;
            this.errorMessage = errorMessage;
        }
    }
}
