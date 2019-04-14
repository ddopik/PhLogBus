package com.example.ddopik.phlogbusiness.ui.cart.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.ui.cart.presenter.CartPresenter;
import com.example.ddopik.phlogbusiness.ui.cart.presenter.CartPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.ImageCommentActivity;
import com.example.ddopik.phlogbusiness.ui.payment.PaymentWebViewActivity;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity implements CartView {

    private CartPresenter presenter;

    private TextView itemsNumberTV, cartIsEmptyTV;
    private Button checkoutButton;
    private ImageView cartIsEmptyIV;
    private RecyclerView recyclerView;
    private ProgressBar loading;

    public static final String REMOVED_LIST = "removedList";

    private ArrayList<Integer> removedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initPresenter();
        initView();
        initListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadCart();
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
        recyclerView.setAdapter(new CartAdapter(actionListener));
        loading = findViewById(R.id.loading);
    }

    private boolean isCheckingOut = false;
    private void initListeners() {
        checkoutButton.setOnClickListener(v -> {
            isCheckingOut = true;
            Intent intent=new Intent(this,PaymentWebViewActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

    @Override
    public void initPresenter() {
        presenter = new CartPresenterImpl();
    }

    private void reloadCart() {

        presenter.loadCartItems(objects -> {
            if (objects == null || objects.isEmpty()) {
                itemsNumberTV.setText(getString(R.string.cart_item_count, 0));
                cartIsEmptyIV.setVisibility(View.VISIBLE);
                cartIsEmptyTV.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                if (isCheckingOut) {
                    removedItems.clear();
                    removedItems.add(-1);
                }
            } else {
                loading.setVisibility(View.GONE);
                CartAdapter adapter = (CartAdapter) recyclerView.getAdapter();
                adapter.setList(objects);
                itemsNumberTV.setText(getString(R.string.cart_item_count, objects.size()));
            }
        }, this);
    }

    private CartAdapter.ActionListener actionListener = (type, o, booleanConsumer) -> {
        switch (type) {
            case REMOVE:
                CharSequence photoChooserOptions[] = new CharSequence[]{getString(R.string.yes), getString(R.string.no)};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.remove_cart_item_confirmation));
                builder.setPositiveButton(R.string.yes, (dialog, which) -> {
                    presenter.removeCartItem(this, o, aBoolean -> {
                        if (aBoolean)
                            removedItems.add(o.id);
                        booleanConsumer.accept(aBoolean);
                    });
                }).setNegativeButton(R.string.no, null).show();
                break;
            case VIEW:
                Intent intent = new Intent(this, ImageCommentActivity.class);
                intent.putExtra(ImageCommentActivity.IMAGE_DATA, o);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case EXCLUSIVE:
                presenter.setExclusive(this, o, success -> {
                    if (!success)
                        showToast(getString(R.string.error_exclusive));
                    booleanConsumer.accept(success);
                });
                break;
        }
    };

    @Override
    public void onBackPressed() {
        if (removedItems.size() > 0) {
            Intent data = new Intent();
            data.putIntegerArrayListExtra(REMOVED_LIST, removedItems);
            setResult(RESULT_OK, data);
        }
        super.onBackPressed();
    }
}
