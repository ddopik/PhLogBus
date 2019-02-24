package com.example.ddopik.phlogbusiness.ui.social.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.view.CampaignInnerActivity;
import com.example.ddopik.phlogbusiness.ui.social.model.SocialData;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

public class SocialAdapterCampaignViewController {

    private Context context;
    private String TAG = SocialAdapterCampaignViewController.class.getSimpleName();
    private List<SocialData> socialDataList;
    private SocialAdapter socialAdapter;

    public SocialAdapterCampaignViewController(Context context, SocialAdapter socialAdapter, List<SocialData> socialDataList) {
        this.context = context;
        this.socialAdapter = socialAdapter;
        this.socialDataList = socialDataList;
    }


    @SuppressLint("CheckResult")
    public void setCampaignType_1(SocialAdapter.SocialViewHolder socialViewHolder, SocialData socialData, SocialAdapter.OnSocialItemListener onSocialItemListener) {

        socialViewHolder.socialCampaignType1.setVisibility(View.VISIBLE);
        socialViewHolder.storyTitle.setText(socialData.title);
        GlideApp.with(context)
//                .load(socialData.campaigns.get(0).business.thumbnail)
                .load(socialData.campaigns.get(0).imageCover)
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.default_user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(socialViewHolder.socialCampaignIcon);

        GlideApp.with(context)
                .load(socialData.campaigns.get(0).imageCover)
                .centerCrop()
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .into(socialViewHolder.socialCampaignImg);

        socialViewHolder.socialCampaignTitle.setText(socialData.title);
        socialViewHolder.socialCampaignDayLeft.setText("days_left here");
        socialViewHolder.socialCampaignName.setText(socialData.campaigns.get(0).titleEn);

        if (socialData.campaigns.get(0).isJoined) {
            socialViewHolder.socialJoinCampaignBtn.setVisibility(View.GONE);
        } else {
            socialViewHolder.socialJoinCampaignBtn.setVisibility(View.VISIBLE);
        }

        if (onSocialItemListener != null) {
            socialViewHolder.socialJoinCampaignBtn.setOnClickListener(v -> {
                joinCampaign(socialData.campaigns.get(0).id,socialData);
            });


            GlideApp.with(context)
                    .load(context.getResources().getDrawable(R.drawable.giphy))

                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            // log exception

                            return false; // important to return false so the error placeholder can be placed
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            socialViewHolder.socialJoinCampaignBtn.setBackground(context.getResources().getDrawable(R.drawable.giphy));
                            return false;
                        }
                    });

            socialViewHolder.socialCampaignContainer.setOnClickListener(v -> {
                Intent intent = new Intent(context, CampaignInnerActivity.class);
                intent.putExtra(CampaignInnerActivity.CAMPAIGN_ID, String.valueOf(socialData.campaigns.get(0).id));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            });

        }
    }

    @SuppressLint("CheckResult")
    public void joinCampaign(int campaignId,SocialData socialData) {


        BaseNetworkApi.followCampaign(String.valueOf(campaignId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followCampaignResponse -> {
                    socialData.campaigns.get(0).isJoined =true;

                    getCampaignIndex(campaignId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(index -> {
                                if (index >0){
                                    socialDataList.set(index, socialData);
                                    socialAdapter.notifyDataSetChanged();
                                }
                                socialAdapter.notifyDataSetChanged();

                            }, throwable -> {
                                CustomErrorUtil.Companion.setError(context, TAG, throwable);
                            });


//                    onSocialItemListener.onSocialCampaignJoined(campaignId, true);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }

    private Observable<Integer> getCampaignIndex(int campaign) {
        return Observable.fromCallable(() -> {

            for (int i = 0; i < socialDataList.size(); i++) {
                if (socialDataList.get(i).profiles != null && socialDataList.get(i).profiles.size() > 0)
                    for (Campaign mCampaign : socialDataList.get(i).campaigns) {
                        if (mCampaign.id.equals(campaign)) {
                            return i;
                        }
                    }
            }
            return -1;
        });

    }
}
