package com.example.ddopik.phlogbusiness.ui.campaigns.running.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;
import com.example.ddopik.phlogbusiness.utiltes.Constants;

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
        if (homeCampaign == null)
            return;

        campaignViewHolder.campaignImage.setOnClickListener(v -> {
            if (campaignLister != null) {
                campaignLister.onCampaignClicked(homeCampaign.id.toString());
            }
        });
        campaignViewHolder.campaignDayLeft.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_time_white), null, null, null);

        Glide.with(context).load(homeCampaign.imageCover)
                .apply(RequestOptions.errorOf(R.drawable.default_place_holder).placeholder(R.drawable.default_error_img))
                .into(campaignViewHolder.campaignImage);
        campaignViewHolder.campaignBusinessName.setText(homeCampaign.business.fullName);
        campaignViewHolder.campaignTitle.setText(homeCampaign.titleEn);
        campaignViewHolder.campaignPrize.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(context, R.drawable.ic_prize_white), null, null, null);
        if (homeCampaign.prize != null)
            campaignViewHolder.campaignPrize.setText(homeCampaign.prize);
        campaignViewHolder.campaignDayLeft.setText(String.valueOf(homeCampaign.daysLeft));
        campaignViewHolder.campaignDayLeft.append(" " + context.getResources().getString(R.string.days_left));


        campaignViewHolder.campaignPhotosCount.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_photo), null, null, null);
        campaignViewHolder.campaignPhotosCount.setText(String.valueOf(homeCampaign.photosCount));
        campaignViewHolder.campaignPhotosCount.append(" " + context.getResources().getString(R.string.photos_uploaded));

        Glide.with(context)
                .load(homeCampaign.business.thumbnail)
                .apply(RequestOptions.circleCropTransform()
                        .error(R.drawable.default_place_holder)
                        .placeholder(R.drawable.default_error_img))
                .into(campaignViewHolder.campaignBusinessIcon);

        if (homeCampaign.status.equals(Constants.CampaignStatus.CAMPAIGN_STATUS_PRIZE_PROCESSING)) {
            campaignViewHolder.campaignDayLeft.setVisibility(View.GONE);
            campaignViewHolder.statusView.setVisibility(View.VISIBLE);
            campaignViewHolder.statusIndicator.setBackgroundResource(R.drawable.circle_green);
            campaignViewHolder.statusVal.setText(R.string.campaign_status_processing);
        } else {
            campaignViewHolder.campaignDayLeft.setVisibility(View.VISIBLE);
            campaignViewHolder.statusView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return homeCampaignList.size();
    }

    public class CampaignViewHolder extends RecyclerView.ViewHolder {
        private ImageView campaignImage;
        private ImageView campaignBusinessIcon;
        private TextView campaignBusinessName, campaignTitle, campaignDayLeft, campaignPrize, campaignPhotosCount;
        private ConstraintLayout statusView;
        private ImageView statusIndicator;
        private TextView statusVal;

        public CampaignViewHolder(View view) {
            super(view);

            campaignBusinessIcon = view.findViewById(R.id.campaign_icon);
            campaignImage = view.findViewById(R.id.campaign_img);
            campaignBusinessName = view.findViewById(R.id.campaign_name);
            campaignTitle = view.findViewById(R.id.campaign_title);
            campaignDayLeft = view.findViewById(R.id.campaign_day_left);
            campaignPhotosCount = view.findViewById(R.id.campaign_joined);

            campaignPrize = view.findViewById(R.id.campaign_prize);

            statusView = view.findViewById(R.id.status_view);
            statusIndicator = view.findViewById(R.id.status_indicator);
            statusVal = view.findViewById(R.id.status_val);
        }
    }


    public interface CampaignLister {

        void onCampaignClicked(String campaignID);

    }
}
