package com.example.ddopik.phlogbusiness.ui.social.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.bumptech.glide.request.RequestOptions;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.brand.view.BrandInnerActivity;
import com.example.ddopik.phlogbusiness.ui.social.model.SocialData;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class SocialAdapterBrandController {

    private String TAG=SocialAdapterBrandController.class.getSimpleName();
    private Context context;
    private SocialAdapter socialAdapter;
    private List<SocialData> socialDataList;
    public SocialAdapterBrandController(Context context, SocialAdapter socialAdapter, List<SocialData> socialDataList) {
        this.context = context;
        this.socialAdapter = socialAdapter;
        this.socialDataList = socialDataList;

    }

    public void setBrandViewType_1(SocialAdapter.SocialViewHolder socialViewHolder, SocialData socialData, SocialAdapter.OnSocialItemListener onSocialItemListener) {
        socialViewHolder.socialBrandType1.setVisibility(View.VISIBLE);
        socialViewHolder.storyTitle.setText(socialData.title);
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

        socialViewHolder.socialBrandName.setText(socialData.brands.get(0).fullName);
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
                if (!socialData.brands.get(0).isFollow){
                    followSocialBrand(socialData.brands.get(0).id,socialData);
                }else{
                    unFollowSocialBrand(socialData.brands.get(0).id,socialData);
                }

            });
        }
    }

    @SuppressLint("CheckResult")
    private void followSocialBrand(int brandId,SocialData socialData) {
        BaseNetworkApi.followBrand(String.valueOf(brandId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {

                    socialData.brands.get(0).isFollow = true;
                    getBrandIndex(brandId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(index -> {
                                if (index > 0) {
                                    socialDataList.set(index, socialData);
                                    socialAdapter.notifyDataSetChanged();
                                }

                            }, throwable -> {
                                CustomErrorUtil.Companion.setError(context, TAG, throwable);
                            });
//                    onSocialItemListener.onSocialBrandFollowed(brandId,true);
                 }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }
    @SuppressLint("CheckResult")
     public void unFollowSocialBrand(int brandId,SocialData socialData) {
        BaseNetworkApi.unFollowBrand(String.valueOf(brandId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followBrandResponse -> {
                    socialData.brands.get(0).isFollow = false;
                    getBrandIndex(brandId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(index -> {
                                if (index > 0) {
                                    socialDataList.set(index, socialData);
                                    socialAdapter.notifyDataSetChanged();
                                }

                            }, throwable -> {
                                CustomErrorUtil.Companion.setError(context, TAG, throwable);
                            });
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }


    private Observable<Integer> getBrandIndex(int businessId) {
        return Observable.fromCallable(() -> {

            for (int i = 0; i < socialDataList.size(); i++) {
                if (socialDataList.get(i).profiles != null && socialDataList.get(i).profiles.size() > 0)
                    for (Business business : socialDataList.get(i).brands) {
                        if (business.id.equals(businessId)) {
                            return i;
                        }
                    }
            }
            return -1;
        });

    }
}
