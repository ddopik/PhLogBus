package com.example.ddopik.phlogbusiness.ui.cart.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.ui.cart.view.CartView;

import java.util.List;

import io.reactivex.functions.Consumer;

public interface CartPresenter {

    void setView(CartView view);

    void loadCartItems(Consumer<List<Object>> consumer, Context baseContext);

    void terminate();
}
