package com.example.ddopik.phlogbusiness.ui.campaigns.running.view;

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
import com.example.ddopik.phlogbusiness.Utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;

import java.util.List;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class RunningCampaignsAdapter extends RecyclerView.Adapter<RunningCampaignsAdapter.CampaignViewHolder> {

    private List<Campaign> homeCampaignList;
    private Context context;
    public CampaignLister campaignLister;

    public RunningCampaignsAdapter(Context context, List<Campaign> homeCampaignList) {
        this.context = context;
        this.homeCampaignList = homeCampaignList;

    }


    @NonNull
    @Override
    public CampaignViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new CampaignViewHolder(layoutInflater.inflate(R.layout.view_holder_home_campaigns, viewGroup, false));


    }

    @Override
    public void onBindViewHolder(@NonNull CampaignViewHolder campaignViewHolder, int i) {

        Campaign homeCampaign = homeCampaignList.get(i);
        campaignViewHolder.campaignImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campaignLister != null) {
                    campaignLister.onCampaignClicked(homeCampaign.id.toString());
                }
            }
        });

        GlideApp.with(context).load(homeCampaign.imageCover)
                .error(R.drawable.default_place_holder)
                .placeholder(R.drawable.default_error_img)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                        campaignViewHolder.campaignImage.setBackground(resource);
                    }
                });
        campaignViewHolder.campaignBusinessName.setText(homeCampaign.business.fullName);
        campaignViewHolder.campaignTitle.setText(homeCampaign.titleEn);
        campaignViewHolder.campaignDayLeft.setText(String.valueOf(homeCampaign.daysLeft));
        campaignViewHolder.campaignDayLeft.append(" "+context.getResources().getString(R.string.days_left));

        campaignViewHolder.campaignJoined.setText(String.valueOf(homeCampaign.daysLeft));
        campaignViewHolder.campaignJoined.append(" "+context.getResources().getString(R.string.people_joined));

        GlideApp.with(context)
                .load(homeCampaign.business.thumbnail)
                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.default_place_holder)
                .placeholder(R.drawable.default_error_img)
                .into(campaignViewHolder.campaignBusinessIcon);
    }

    @Override
    public int getItemCount() {
        return homeCampaignList.size();
    }

    public class CampaignViewHolder extends RecyclerView.ViewHolder {
        private  ImageView campaignImage;
        private  ImageView campaignBusinessIcon;
        private  TextView campaignBusinessName, campaignTitle, campaignDayLeft,campaignJoined;

        public CampaignViewHolder(View view) {
            super(view);

            campaignBusinessIcon = view.findViewById(R.id.campaign_icon);
            campaignImage = view.findViewById(R.id.campaign_img);
            campaignBusinessName = view.findViewById(R.id.campaign_name);
            campaignTitle = view.findViewById(R.id.campaign_title);
            campaignDayLeft = view.findViewById(R.id.campaign_day_left);
            campaignJoined = view.findViewById(R.id.campaign_joined);

        }
    }


    public interface CampaignLister {

        void onCampaignClicked(String campaignID);

    }
}
