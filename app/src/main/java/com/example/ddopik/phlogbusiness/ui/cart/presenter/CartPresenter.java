package com.example.ddopik.phlogbusiness.ui.cart.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.ui.cart.view.CartView;

public interface CartPresenter {

    void setView(CartView view);

    void loadCartItems(Context baseContext);
}
