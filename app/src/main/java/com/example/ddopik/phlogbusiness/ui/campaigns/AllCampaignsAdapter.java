package com.example.ddopik.phlogbusiness.ui.campaigns;

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
import com.example.ddopik.phlogbusiness.ui.campaigns.model.HomeCampaign;

import java.util.List;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class AllCampaignsAdapter extends RecyclerView.Adapter<AllCampaignsAdapter.CampaignViewHolder> {

    private List<HomeCampaign> homeCampaignList;
    private Context context;
    public CampaignLister campaignLister;

    public AllCampaignsAdapter(Context context, List<HomeCampaign> homeCampaignList) {
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

        HomeCampaign homeCampaign = homeCampaignList.get(i);
        campaignViewHolder.campaignImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campaignLister != null) {
                    campaignLister.onCampaignClicked(homeCampaign.id.toString());
                }
            }
        });

        GlideApp.with(context).load(homeCampaign.imageCover)
                .error(R.drawable.splash_screen_background)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                        campaignViewHolder.campaignImage.setBackground(resource);
                    }
                });
        campaignViewHolder.campaignBusinessName.setText(homeCampaign.business.userName);
        campaignViewHolder.campaignTitle.setText(homeCampaign.titleEn);
        campaignViewHolder.campaignDayLeft.setText(homeCampaign.leftDays);
        campaignViewHolder.campaignDayLeft.append(" "+context.getResources().getString(R.string.days_left));
        GlideApp.with(context).load(homeCampaign.business.thumbnail).apply(RequestOptions.circleCropTransform()).into(campaignViewHolder.campaignBusinessIcon);
    }

    @Override
    public int getItemCount() {
        return homeCampaignList.size();
    }

    public class CampaignViewHolder extends RecyclerView.ViewHolder {
        private  ImageView campaignImage;
        private  ImageView campaignBusinessIcon;
        private  TextView campaignBusinessName, campaignTitle, campaignDayLeft;
        private   Button joinCampaignBtn;

        public CampaignViewHolder(View view) {
            super(view);

            campaignBusinessIcon = view.findViewById(R.id.campaign_icon);
            campaignImage = view.findViewById(R.id.campaign_img);
            campaignBusinessName = view.findViewById(R.id.campaign_name);
            campaignTitle = view.findViewById(R.id.campaign_title);
            campaignDayLeft = view.findViewById(R.id.campaign_day_left);
//            readMeBtn = view.findViewById(R.id.remind_me_btn);
            joinCampaignBtn = view.findViewById(R.id.join_campaign_btn);

        }
    }


    public interface CampaignLister {

        void onCampaignClicked(String campaignID);

    }
}
