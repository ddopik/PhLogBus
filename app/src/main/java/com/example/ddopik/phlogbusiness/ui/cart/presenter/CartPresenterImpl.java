package com.example.ddopik.phlogbusiness.ui.cart.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.cart.view.CartActivity;
import com.example.ddopik.phlogbusiness.ui.cart.view.CartView;

import java.util.List;

import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartPresenterImpl implements CartPresenter {

    private static final String SAVED = "saved";
    private static final String TAG = CartPresenterImpl.class.getSimpleName();

    private CartView view;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void setView(CartView view) {
        this.view = view;
    }

    @Override
    public void loadCartItems(Consumer<List<BaseImage>> consumer, Context baseContext) {
        Disposable disposable = BaseNetworkApi.getCartItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    consumer.accept(response.data);
                }, throwable -> {
                    consumer.accept(null);
                    CustomErrorUtil.Companion.setError(baseContext, TAG, throwable);
                });
        disposables.add(disposable);
    }

    @Override
    public void terminate() {
        disposables.dispose();
    }

    @Override
    public void removeCartItem(Context baseContext, BaseImage baseImage, Consumer<Boolean> booleanConsumer) {
        Disposable disposable = BaseNetworkApi.removeCartItem(baseImage.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null && response.getMsg().equals(SAVED))
                        booleanConsumer.accept(true);
                    else
                        booleanConsumer.accept(false);
                }, throwable -> {
                    booleanConsumer.accept(false);
                    CustomErrorUtil.Companion.setError(baseContext, TAG, throwable);
                });
        disposables.add(disposable);
    }

    @Override
    public void setExclusive(Context context, BaseImage image, Consumer<Boolean> success) {
        Disposable disposable = BaseNetworkApi.setImageBuyExclusive(image, !image.exclusive)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (s != null)
                        success.accept(true);
                    else success.accept(false);
                }, throwable -> {
                    success.accept(false);
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
        disposables.add(disposable);
    }
}
