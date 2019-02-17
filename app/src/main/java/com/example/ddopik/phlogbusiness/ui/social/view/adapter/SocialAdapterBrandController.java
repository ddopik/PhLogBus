package com.example.ddopik.phlogbusiness.ui.social.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.bumptech.glide.request.RequestOptions;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.brand.view.BrandInnerActivity;
import com.example.ddopik.phlogbusiness.ui.social.model.SocialData;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SocialAdapterBrandController {

    private String TAG=SocialAdapterBrandController.class.getSimpleName();
    private Context context;

    public SocialAdapterBrandController(Context context) {
        this.context = context;
    }

    public void setBrandViewType_1(SocialAdapter.SocialViewHolder socialViewHolder, SocialData socialData, SocialAdapter.OnSocialItemListener onSocialItemListener) {
        socialViewHolder.socialBrandType1.setVisibility(View.VISIBLE);

        GlideApp.with(context)
                .load(socialData.brands.get(0).thumbnail)
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.default_user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(socialViewHolder.socialBrandIconImg);

        GlideApp.with(context)
                .load(socialData.brands.get(0).imageCover)
                .centerCrop()
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .into(socialViewHolder.socialBrandImg);

        socialViewHolder.socialBrandName.setText(socialData.brands.get(0).firstName+" "+socialData.brands.get(0).lastName);
        socialViewHolder.socialBrandFollowing.setText(socialData.brands.get(0).followingsCount);

        if (onSocialItemListener != null) {
            socialViewHolder.socialBrandImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, BrandInnerActivity.class);
                    intent.putExtra(BrandInnerActivity.BRAND_ID, String.valueOf(socialData.brands.get(0).id));
                    context.startActivity(intent);
                }
            });
        }
        if (onSocialItemListener != null) {
            socialViewHolder.followBrandBtn.setOnClickListener(v -> {
             followSocialBrand(socialData.brands.get(0).id,onSocialItemListener);
            });
        }
    }

    @SuppressLint("CheckResult")
    private void followSocialBrand(int brandId,SocialAdapter.OnSocialItemListener onSocialItemListener) {
        BaseNetworkApi.followBrand(String.valueOf(brandId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {
                    onSocialItemListener.onSocialBrandFollowed(brandId,true);
                 }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }
    @SuppressLint("CheckResult")
     public void unFollowSocialBrand(int brandId,SocialAdapter.OnSocialItemListener onSocialItemListener) {
        BaseNetworkApi.unFollowBrand(String.valueOf(brandId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {
                    onSocialItemListener.onSocialBrandFollowed(brandId,false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }
}
