package com.example.ddopik.phlogbusiness.ui.campaigns.completed.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;

import java.util.List;

public class CompletedCampaignsAdapter extends RecyclerView.Adapter<CompletedCampaignsAdapter.CompletedCampaignViewHolder> {

    private List<Campaign> completedCampaignCampaignList;
    private Context context;
    public CompletedCampaignsAdapter.CampaignLister campaignLister;

    public CompletedCampaignsAdapter(Context context, List<Campaign> completedCampaignCampaignList) {
        this.context = context;
        this.completedCampaignCampaignList = completedCampaignCampaignList;

    }


    @NonNull
    @Override
    public CompletedCampaignsAdapter.CompletedCampaignViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new CompletedCampaignsAdapter.CompletedCampaignViewHolder(layoutInflater.inflate(R.layout.view_holder_home_campaigns, viewGroup, false));


    }

    @Override
    public void onBindViewHolder(@NonNull CompletedCampaignsAdapter.CompletedCampaignViewHolder campaignViewHolder, int i) {

        Campaign homeCampaign = completedCampaignCampaignList.get(i);
        if (homeCampaign == null)
            return;

        campaignViewHolder.campaignImage.setOnClickListener(v -> {
            if (campaignLister != null) {
                campaignLister.onCampaignClicked(homeCampaign.id.toString());
            }
        });


         ;


        Glide.with(context).load(homeCampaign.imageCover)
                .apply(RequestOptions.errorOf(R.drawable.default_place_holder))
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
                        .error(R.drawable.default_place_holder))
                .into(campaignViewHolder.campaignBusinessIcon);

        campaignViewHolder.campaignDayLeft.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return completedCampaignCampaignList.size();
    }

    public class CompletedCampaignViewHolder extends RecyclerView.ViewHolder {
        private ImageView campaignImage;
        private ImageView campaignBusinessIcon;
        private TextView campaignBusinessName, campaignTitle, campaignDayLeft, campaignPhotosCount, campaignPrize;
        private Button joinCampaignBtn;

        public CompletedCampaignViewHolder(View view) {
            super(view);

            campaignBusinessIcon = view.findViewById(R.id.campaign_icon);
            campaignImage = view.findViewById(R.id.campaign_img);
            campaignBusinessName = view.findViewById(R.id.campaign_name);
            campaignTitle = view.findViewById(R.id.campaign_title);
            campaignDayLeft = view.findViewById(R.id.campaign_day_left);
            campaignPhotosCount = view.findViewById(R.id.campaign_joined);

            campaignPrize = view.findViewById(R.id.campaign_prize);
        }
    }


    public interface CampaignLister {

        void onCampaignClicked(String campaignID);

    }
}
