package com.example.ddopik.phlogbusiness.ui.cart.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.ui.cart.presenter.CartPresenter;
import com.example.ddopik.phlogbusiness.ui.cart.presenter.CartPresenterImpl;

public class CartActivity extends BaseActivity implements CartView {

    private CartPresenter presenter;

    private TextView itemsNumberTV, cartIsEmptyTV;
    private Button checkoutButton;
    private ImageView cartIsEmptyIV;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        initPresenter();
        initListeners();
    }

    @Override
    protected void onDestroy() {
        presenter.terminate();
        super.onDestroy();
    }

    @Override
    public void initView() {
        itemsNumberTV = findViewById(R.id.items_number_text_view);
        cartIsEmptyTV = findViewById(R.id.cart_empty_tv);
        cartIsEmptyIV = findViewById(R.id.cart_empty_iv);
        checkoutButton = findViewById(R.id.checkout_button);
        recyclerView = findViewById(R.id.cart_recycler_view);
        recyclerView.setAdapter(new CartAdapter());
    }

    private void initListeners() {
        checkoutButton.setOnClickListener(v -> {

        });
    }

    @Override
    public void initPresenter() {
        presenter = new CartPresenterImpl();
        presenter.loadCartItems(objects -> {

        }, getBaseContext());
    }
}
