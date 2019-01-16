package com.example.ddopik.phlogbusiness.ui.accountdetails.presenter;

import com.example.ddopik.phlogbusiness.ui.accountdetails.view.AccountDetailsView;

public class AccountDetailsPresenterImpl implements AccountDetailsPresenter {

    private AccountDetailsView view;

    @Override
    public void setView(AccountDetailsView view) {
        this.view = view;
    }
}
