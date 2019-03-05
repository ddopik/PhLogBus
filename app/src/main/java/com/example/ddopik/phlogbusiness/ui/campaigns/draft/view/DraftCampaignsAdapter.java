package com.example.ddopik.phlogbusiness.ui.campaigns.draft.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;

import java.util.List;

public class DraftCampaignsAdapter extends RecyclerView.Adapter<DraftCampaignsAdapter.DraftCampaignViewHolder> {

    private List<Campaign> draftCampaignList;
    private Context context;
    public DraftCampaignsAdapter.CampaignLister campaignLister;

    public DraftCampaignsAdapter(Context context, List<Campaign> draftCampaignList) {
        this.context = context;
        this.draftCampaignList = draftCampaignList;

    }


    @NonNull
    @Override
    public DraftCampaignsAdapter.DraftCampaignViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new DraftCampaignsAdapter.DraftCampaignViewHolder(layoutInflater.inflate(R.layout.view_holder_home_campaigns, viewGroup, false));


    }

    @Override
    public void onBindViewHolder(@NonNull DraftCampaignsAdapter.DraftCampaignViewHolder campaignViewHolder, int i) {

        Campaign homeCampaign = draftCampaignList.get(i);
        campaignViewHolder.campaignImage.setOnClickListener(v -> {
            if (campaignLister != null) {
                campaignLister.onCampaignClicked(homeCampaign);
            }
        });

        Glide.with(context).load(homeCampaign.imageCover)
                .apply(RequestOptions.errorOf(R.drawable.default_place_holder))
                .into(campaignViewHolder.campaignImage);
        campaignViewHolder.campaignBusinessName.setText(homeCampaign.business.fullName);
        campaignViewHolder.campaignTitle.setText(homeCampaign.titleEn);
        campaignViewHolder.campaignDayLeft.setText(String.valueOf(homeCampaign.daysLeft));
        campaignViewHolder.campaignDayLeft.append(" " + context.getResources().getString(R.string.days_left));

        campaignViewHolder.campaignJoined.setText(String.valueOf(homeCampaign.daysLeft));
        campaignViewHolder.campaignJoined.append(" "+context.getResources().getString(R.string.people_joined));

        Glide.with(context)
                .load(homeCampaign.business.thumbnail)
                .apply(RequestOptions.circleCropTransform()
                        .error(R.drawable.default_place_holder))
                .into(campaignViewHolder.campaignBusinessIcon);
    }

    @Override
    public int getItemCount() {
        return draftCampaignList.size();
    }

    public class DraftCampaignViewHolder extends RecyclerView.ViewHolder {
        private ImageView campaignImage;
        private ImageView campaignBusinessIcon;
        private TextView campaignBusinessName, campaignTitle, campaignDayLeft,campaignJoined;


        public DraftCampaignViewHolder(View view) {
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

        void onCampaignClicked(Campaign campaign);

    }
}
