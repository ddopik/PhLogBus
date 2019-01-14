package com.example.ddopik.phlogbusiness.ui.cart.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.ui.cart.presenter.CartPresenter;
import com.example.ddopik.phlogbusiness.ui.cart.presenter.CartPresenterImpl;

public class CartActivity extends BaseActivity implements CartView {

    private CartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {
        presenter = new CartPresenterImpl();
    }
}
