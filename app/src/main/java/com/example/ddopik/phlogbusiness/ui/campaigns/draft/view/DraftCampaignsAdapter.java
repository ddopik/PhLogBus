package com.example.ddopik.phlogbusiness.ui.campaigns.draft.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;
import com.example.ddopik.phlogbusiness.utiltes.Constants;

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

        if (homeCampaign == null)
            return;
        campaignViewHolder.campaignDayLeft.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_time_white), null, null, null);
//        campaignViewHolder.campaignJoined.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_joined), null, null, null);
        campaignViewHolder.campaignJoined.setVisibility(View.INVISIBLE);
        campaignViewHolder.campaignPrize.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_prize_white), null, null, null);
        if (homeCampaign.prize != null)
            campaignViewHolder.campaignPrize.setText(homeCampaign.prize);
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
        campaignViewHolder.campaignJoined.append(" " + context.getResources().getString(R.string.people_joined));

        Glide.with(context)
                .load(homeCampaign.business.thumbnail)
                .apply(RequestOptions.circleCropTransform()
                        .error(R.drawable.default_place_holder))
                .into(campaignViewHolder.campaignBusinessIcon);

        campaignViewHolder.statusView.setVisibility(View.VISIBLE);
        campaignViewHolder.statusView.setVisibility(View.VISIBLE);
        switch (homeCampaign.status) {
            case Constants.CampaignStatus
                    .CAMPAIGN_STATUS_DRAFT:
                campaignViewHolder.deleteButton.setVisibility(View.VISIBLE);
                campaignViewHolder.statusVal.setText(R.string.draft);
                campaignViewHolder.statusIndicator.setBackgroundResource(R.drawable.circle_blue);
                break;
            case Constants.CampaignStatus.CAMPAIGN_STATUS_REQUEST:
            case Constants.CampaignStatus.CAMPAIGN_STATUS_PENDING:
                campaignViewHolder.deleteButton.setVisibility(View.INVISIBLE);
                campaignViewHolder.statusVal.setText(R.string.pending_approval);
                campaignViewHolder.statusIndicator.setBackgroundResource(R.drawable.circle_orange);
                break;
        }
        campaignViewHolder.deleteButton.setOnClickListener(v -> {
            if (campaignLister != null)
                campaignLister.onDeleteClicked(homeCampaign, i);
        });
    }

    @Override
    public int getItemCount() {
        return draftCampaignList.size();
    }

    public class DraftCampaignViewHolder extends RecyclerView.ViewHolder {
        private ImageView campaignImage;
        private ImageView campaignBusinessIcon;
        private TextView campaignBusinessName, campaignTitle, campaignDayLeft, campaignJoined, campaignPrize;
        private ImageButton deleteButton;
        private ConstraintLayout statusView;
        private ImageView statusIndicator;
        private TextView statusVal;

        public DraftCampaignViewHolder(View view) {
            super(view);

            campaignBusinessIcon = view.findViewById(R.id.campaign_icon);
            campaignImage = view.findViewById(R.id.campaign_img);
            campaignBusinessName = view.findViewById(R.id.campaign_name);
            campaignTitle = view.findViewById(R.id.campaign_title);
            campaignDayLeft = view.findViewById(R.id.campaign_day_left);
            campaignJoined = view.findViewById(R.id.campaign_joined);
            campaignPrize = view.findViewById(R.id.campaign_prize);

            deleteButton = view.findViewById(R.id.delete_button);
            statusView = view.findViewById(R.id.status_view);
            statusIndicator = view.findViewById(R.id.status_indicator);
            statusVal = view.findViewById(R.id.status_val);
        }
    }


    public interface CampaignLister {

        void onCampaignClicked(Campaign campaign);

        void onDeleteClicked(Campaign campaign, int position);

    }
}
