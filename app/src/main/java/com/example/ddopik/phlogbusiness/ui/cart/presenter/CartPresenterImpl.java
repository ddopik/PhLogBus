package com.example.ddopik.phlogbusiness.ui.cart.presenter;

import com.example.ddopik.phlogbusiness.ui.cart.view.CartView;

public class CartPresenterImpl implements CartPresenter {

    private CartView view;

    @Override
    public void setView(CartView view) {
        this.view = view;
    }
}
