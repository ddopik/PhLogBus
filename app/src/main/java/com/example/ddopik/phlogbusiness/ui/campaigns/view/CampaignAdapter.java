package com.example.ddopik.phlogbusiness.ui.campaigns.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;

import java.util.List;

/**
 * Created by abdalla_maged on 12/25/2018.
 */
public class CampaignAdapter  extends RecyclerView.Adapter<CampaignAdapter.CampaignViewHolder> {

    private List<Campaign> campaignList;
    private Context context;
    public CampaignAdapter.CampaignLister campaignLister;

    public CampaignAdapter(Context context, List<Campaign> campaignList) {
        this.context = context;
        this.campaignList = campaignList;

    }


    @NonNull
    @Override
    public CampaignAdapter.CampaignViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new CampaignAdapter.CampaignViewHolder(layoutInflater.inflate(R.layout.view_holder_home_campaigns, viewGroup, false));


    }

    @Override
    public void onBindViewHolder(@NonNull CampaignAdapter.CampaignViewHolder campaignViewHolder, int i) {

        Campaign campaign = campaignList.get(i);
        campaignViewHolder.campaignImage.setOnClickListener(v -> {
            if (campaignLister != null) {
                campaignLister.onCampaignClicked(campaign.id.toString());
            }
        });

        GlideApp.with(context).load(campaign.imageCover)
                .error(R.drawable.splash_screen_background)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                        campaignViewHolder.campaignImage.setBackground(resource);
                    }
                });
        campaignViewHolder.campaignBusinessName.setText(campaign.business.fullName);
        campaignViewHolder.campaignTitle.setText(campaign.titleEn);
        campaignViewHolder.campaignDayLeft.setText(campaign.daysLeft);
        campaignViewHolder.campaignDayLeft.append(" "+context.getResources().getString(R.string.days_left));


        GlideApp.with(context).load(campaign.business.thumbnail).apply(RequestOptions.circleCropTransform()).into(campaignViewHolder.campaignBusinessIcon);
    }

    @Override
    public int getItemCount() {
        return campaignList.size();
    }

    public class CampaignViewHolder extends RecyclerView.ViewHolder {
        private ImageView campaignImage;
        private  ImageView campaignBusinessIcon;
        private TextView campaignBusinessName, campaignTitle, campaignDayLeft;
        private Button joinCampaignBtn;

        public CampaignViewHolder(View view) {
            super(view);

            campaignBusinessIcon = view.findViewById(R.id.campaign_icon);
            campaignImage = view.findViewById(R.id.campaign_img);
            campaignBusinessName = view.findViewById(R.id.campaign_name);
            campaignTitle = view.findViewById(R.id.campaign_title);
            campaignDayLeft = view.findViewById(R.id.campaign_day_left);

//            readMeBtn = view.findViewById(R.id.remind_me_btn);


        }
    }


    public interface CampaignLister {

        void onCampaignClicked(String campaignID);

    }
}