package com.example.ddopik.phlogbusiness.ui.notification.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.base.commonmodel.Campaign;
import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.view.CampaignInnerActivity;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.ImageCommentActivity;
import com.example.ddopik.phlogbusiness.ui.notification.model.NotificationList;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;


import java.util.List;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class NotifiationAdapter extends RecyclerView.Adapter<NotifiationAdapter.NotificationViewHolder> {

    public static final int itemType_NOTIFICATION_HEAD = 66;
    private Context context;
    private List<NotificationList> notificationItemList;

    public NotifiationAdapter(List<NotificationList> notificationItemList) {
        this.notificationItemList = notificationItemList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new NotificationViewHolder(layoutInflater.inflate(R.layout.view_holder_notification, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {
        NotificationList notificationItem = notificationItemList.get(i);
        if (notificationViewHolder.notificationTitle != null)
            notificationViewHolder.notificationTitle.setText(notificationItemList.get(i).message);

        if (notificationItem.date != null)
            notificationViewHolder.notificationDate.setText(notificationItem.date);


        switch (notificationItemList.get(i).entityId) {
            case Constants.ENTITY_PROFILE: {
                setSeparatorState(notificationViewHolder, notificationItemList.get(i), false);
                bindItemPhotoGrapher(notificationViewHolder, notificationItemList.get(i).photographer);
                break;
            }
            case Constants.ENTITY_CAMPAIGN: {
                bindItemCampaign(notificationViewHolder, notificationItemList.get(i).campaign);
                break;
            }
            case Constants.ENTITY_ALBUM: {

                break;
            }
            case Constants.ENTITY_IMAGE: {
                bindItemPhoto(notificationViewHolder, notificationItemList.get(i).photo);
                break;
            }
            case Constants.ENTITY_BRAND: {
                bindItemBrand(notificationViewHolder, notificationItemList.get(i).brand);
                break;
            }

            case itemType_NOTIFICATION_HEAD: {
                setSeparatorState(notificationViewHolder, notificationItemList.get(i), true);
                break;
            }
        }

        if (notificationItemList.get(i).isRead != null && !notificationItemList.get(i).isRead) {
            notificationViewHolder.notificationContainer.setBackgroundColor(context.getResources().getColor(R.color.transparent));

        } else {
            notificationViewHolder.notificationContainer.setBackgroundColor(context.getResources().getColor(R.color.text_input_color));
        }

    }

    private void setSeparatorState(@NonNull NotificationViewHolder notificationViewHolder, NotificationList notificationList, boolean state) {
        if (state) {
            notificationViewHolder.notificationContainer.setVisibility(View.GONE);
            notificationViewHolder.notificationSeparatorView.setVisibility(View.VISIBLE);
            notificationViewHolder.separatorTitle.setText(notificationList.message);

        } else {
            notificationViewHolder.notificationContainer.setVisibility(View.VISIBLE);
            notificationViewHolder.notificationSeparatorView.setVisibility(View.GONE);

        }

    }

    private void bindItemPhotoGrapher(NotificationViewHolder notificationViewHolder, Photographer photographer) {
        notificationViewHolder.notificationImg.setBackground(context.getResources().getDrawable(R.drawable.circle_red));
        GlideApp.with(context).load(photographer.imageProfile)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .into(notificationViewHolder.notificationImg);

        notificationViewHolder.notificationContainer.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserProfileActivity.class);
            intent.putExtra(UserProfileActivity.USER_ID, String.valueOf(photographer.id));
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        });


    }

    private void bindItemCampaign(NotificationViewHolder notificationViewHolder, Campaign campaign) {

        notificationViewHolder.notificationImg.setBackground(context.getResources().getDrawable(R.drawable.circle_blue));
        GlideApp.with(context).load(campaign.business.thumbnail)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .into(notificationViewHolder.notificationImg);

        notificationViewHolder.notificationContainer.setOnClickListener(v -> {
            Intent intent = new Intent(context, CampaignInnerActivity.class);
            intent.putExtra(CampaignInnerActivity.CAMPAIGN_ID, String.valueOf(campaign.id));
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        });
    }

    private void bindItemBrand(NotificationViewHolder notificationViewHolder, Business business) {

        notificationViewHolder.notificationImg
                .setBackground(context.getResources().getDrawable(R.drawable.circle_orange));
        GlideApp.with(context).load(business.thumbnail)
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .apply(RequestOptions.circleCropTransform())
                .into(notificationViewHolder.notificationImg);

//        notificationViewHolder.notificationContainer.setOnClickListener(v -> {
//            Intent intent=new Intent(context, BrandInnerActivity.class);
//            intent.putExtra(BrandCampaignsActivity.BRAND_ID,business.id);
//            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            context.startActivity(intent);
//        });
    }

    private void bindItemPhoto(NotificationViewHolder notificationViewHolder, BaseImage image) {

        notificationViewHolder.notificationImg.setBackground(context.getResources().getDrawable(R.drawable.circle_green));
        GlideApp.with(context).load(image.url)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .into(notificationViewHolder.notificationImg);

        notificationViewHolder.notificationContainer.setOnClickListener(v -> {
            Intent intent = new Intent(context, ImageCommentActivity.class);
            intent.putExtra(ImageCommentActivity.IMAGE_DATA, image);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return notificationItemList.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {

        View notificationSeparatorView;
        LinearLayout notificationContainer;
        ImageView notificationImg;
        CustomTextView notificationTitle, notificationDate, separatorTitle;

        NotificationViewHolder(View view) {
            super(view);
            notificationImg = view.findViewById(R.id.notification_img);
            notificationTitle = view.findViewById(R.id.notification_title);
            notificationDate = view.findViewById(R.id.notification_time);
            notificationContainer = view.findViewById(R.id.notification_container);
            separatorTitle = view.findViewById(R.id.notification_separator_title);
            notificationSeparatorView = view.findViewById(R.id.notification_separator_view);
        }
    }
}
