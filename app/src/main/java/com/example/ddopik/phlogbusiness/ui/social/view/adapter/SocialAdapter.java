package com.example.ddopik.phlogbusiness.ui.social.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.ui.social.model.SocialData;
import com.o_bdreldin.loadingbutton.LoadingButton;

import java.util.List;

import static com.example.ddopik.phlogbusiness.utiltes.Constants.*;


/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.SocialViewHolder> {

    private Context context;
    private List<SocialData> socialDataList;
    private OnSocialItemListener onSocialItemListener;
    private SocialAdapterProfileViewController socialAdapterProfileViewController;
    private SocialAdapterCampaignViewController socialAdapterCampaignViewController;
    private SocialAdapterAlbumViewController socialAdapterAlbumViewController;
    private SocialAdapterPhotosViewController socialAdapterPhotosViewController;
    private SocialAdapterBrandController socialAdapterBrandController;


    public SocialAdapter(List<SocialData> socialDataList, Context context, OnSocialItemListener onSocialItemListener) {
        this.socialDataList = socialDataList;
        this.onSocialItemListener = onSocialItemListener;
        this.context = context;
    }

    @NonNull
    @Override
    public SocialViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new SocialViewHolder(layoutInflater.inflate(R.layout.view_holder_main_social_item, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull SocialViewHolder socialViewHolder, int i) {

        socialViewHolder.socialProfileType3.setVisibility(View.GONE);
        socialViewHolder.socialImageSliderType5.setVisibility(View.GONE);
        socialViewHolder.socialCampaignType1.setVisibility(View.GONE);
        socialViewHolder.socialBrandType1.setVisibility(View.GONE);
        socialViewHolder.socialAlbumType4.setVisibility(View.GONE);
        /// reInstall socialViewHolder  Header  and padding  as "ENTITY_IMAGE"  is clear it
        socialViewHolder.storyTitle.setPadding(18, 18, 18, 18);
        socialViewHolder.storyTitle.setText("");
        ////

        if (socialDataList.size() > 0)
            switch (socialDataList.get(i).entityId) {

                case ENTITY_PROFILE: {
                    socialAdapterProfileViewController = new SocialAdapterProfileViewController(context, this,socialDataList);

                    bindProfileEntity(socialDataList.get(i), socialViewHolder);
                    break;
                }
                case ENTITY_CAMPAIGN: {
                    socialAdapterCampaignViewController = new SocialAdapterCampaignViewController(context, this,socialDataList);
                    bindCampaignEntity(socialDataList.get(i), socialViewHolder);
                    break;
                }
                case ENTITY_ALBUM: {
                    socialAdapterAlbumViewController = new SocialAdapterAlbumViewController(context);
                    bindAlbumEntity(socialDataList.get(i), socialViewHolder);
                    break;
                }
                case ENTITY_IMAGE: {
                    socialAdapterPhotosViewController = new SocialAdapterPhotosViewController(context);
                    bindImageSlider(socialDataList.get(i), socialViewHolder);
                    break;
                }
                case ENTITY_BRAND: {
                    socialAdapterBrandController = new SocialAdapterBrandController(context, this,socialDataList);
                    bindBrandEntity(socialDataList.get(i), socialViewHolder);
                    break;
                }


            }


    }


    @Override
    public int getItemCount() {
        return socialDataList.size();
    }

    class SocialViewHolder extends RecyclerView.ViewHolder {

        CustomTextView storyTitle;
        FrameLayout socialProfileType3, socialImageSliderType5, socialCampaignType1, socialBrandType1, socialAlbumType4;

        ImageView socialProfileType3Icon, socialProfileType3Img_1, socialProfileType3Img_2, socialProfileType3Img_3, socialProfileType3Img_4, socialDefaultAlbumImg;
        TextView socialProfileType3FullName, socialProfileType3UserName;
        LoadingButton followSocialProfileType3Btn;

        LinearLayout socialAlbumImgGroupContainer, socialProfileAlbumType3PhotosContainer,socialProfileType3ItemHeader;


        /////
        CustomRecyclerView socialImgSlideRv;
        CustomTextView socialImageName;
        /////
        LinearLayout socialCampaignContainer;
        ImageView socialCampaignIcon, socialCampaignImg;
        CustomTextView socialCampaignName, socialCampaignTitle, socialCampaignDayLeft;
        Button socialJoinCampaignBtn;
        /////
        ImageView socialBrandIconImg, socialBrandImg;
        CustomTextView socialBrandName, socialBrandFollowing;
        Button followBrandBtn;
        /////
        ImageView socialAlbum1, socialAlbum2, socialAlbum3;
        CustomTextView socialAlbumName, socialAlbumPhotosNumber;


        SocialViewHolder(View view) {
            super(view);
            socialProfileType3 = view.findViewById(R.id.social_profile_type_3);
            socialImageSliderType5 = view.findViewById(R.id.images_slider_type_5);
            socialCampaignType1 = view.findViewById(R.id.social_campaign_type_1);
            socialBrandType1 = view.findViewById(R.id.social_brand_type_1);
            socialAlbumType4 = view.findViewById(R.id.social_album_type_4);
            storyTitle=view.findViewById(R.id.story_title);

            setProfileReferences(view);

            /////ImageSlider type_1
            socialImgSlideRv = view.findViewById(R.id.social_img_slider_rv);
            socialImageName = view.findViewById(R.id.social_image_name);
            /////CampaignItemView
            socialCampaignContainer = view.findViewById(R.id.social_campaign_container);
            socialCampaignIcon = view.findViewById(R.id.social_campaign_icon);
            socialCampaignName = view.findViewById(R.id.social_campaign_name);
            socialCampaignImg = view.findViewById(R.id.social_campaign_img);
            socialCampaignTitle = view.findViewById(R.id.social_campaign_title);
            socialCampaignDayLeft = view.findViewById(R.id.social_campaign_day_left);
            socialJoinCampaignBtn = view.findViewById(R.id.social_join_campaign_btn);
            /////BrandItemView
            socialBrandIconImg = view.findViewById(R.id.social_brand_icon_img);
            socialBrandImg = view.findViewById(R.id.social_brand_img);
            socialBrandName = view.findViewById(R.id.social_brand_name);
            socialBrandFollowing = view.findViewById(R.id.social_brand_following);
            followBrandBtn = view.findViewById(R.id.follow_brand);
            /////BrandAlbumView
            socialAlbumName = view.findViewById(R.id.social_album_name);
            socialAlbumPhotosNumber = view.findViewById(R.id.social_album_photos_number);
            socialAlbum1 = view.findViewById(R.id.social_album_img_1);
            socialAlbum2 = view.findViewById(R.id.social_album_img_2);
            socialAlbum3 = view.findViewById(R.id.social_album_img_3);
            socialAlbumImgGroupContainer = view.findViewById(R.id.social_album_img_group_container); //fill default Img
            socialDefaultAlbumImg = view.findViewById(R.id.album_img_container); //fill default Img


        }

        private void setProfileReferences(View view) {


            /////profileItemView type_1
            socialProfileType3Icon = view.findViewById(R.id.social_profile_type_3_icon_img);
            socialProfileType3ItemHeader = view.findViewById(R.id.social_profile_item_header);

            socialProfileType3FullName = view.findViewById(R.id.social_profile_full_name);
            socialProfileType3UserName = view.findViewById(R.id.social_profile_user_name);
            followSocialProfileType3Btn = view.findViewById(R.id.follow_social_profile);
            socialProfileType3Img_1 = view.findViewById(R.id.social_profile_img_1);
            socialProfileType3Img_2 = view.findViewById(R.id.social_profile_img_2);
            socialProfileType3Img_3 = view.findViewById(R.id.social_profile_img_3);
            socialProfileType3Img_4 = view.findViewById(R.id.social_profile_img_4);
             socialProfileAlbumType3PhotosContainer = view.findViewById(R.id.social_profile_album_type_3_photos_container);


        }
    }

    public interface OnSocialItemListener {

        void onSocialBrandFollowed(int brandId, boolean state);


    }

    private void bindProfileEntity(SocialData socialData, SocialViewHolder socialViewHolder) {

        switch (socialData.displayType) {

            case PROFILE_DISPLAY_TYPE_3:
                socialViewHolder.storyTitle.setText(socialData.title);
                socialAdapterProfileViewController.setProfileType3(socialData, socialViewHolder, onSocialItemListener);
                break;
        }


    }

    private void bindImageSlider(SocialData socialData, SocialViewHolder socialViewHolder) {
        switch (socialData.displayType) {

            case IMGS_DISPLAY_TYPE_5: {
                socialAdapterPhotosViewController.setPhotosViewType5(socialViewHolder, socialData, onSocialItemListener);
                break;
            }
        }
    }

    private void bindCampaignEntity(SocialData socialData, SocialViewHolder socialViewHolder) {
        switch (socialData.displayType) {

            case CAMPAIGN_DISPLAY_TYPE_1:
                socialAdapterCampaignViewController.setCampaignType_1(socialViewHolder, socialData, onSocialItemListener);
                break;

        }

    }

    private void bindBrandEntity(SocialData socialData, SocialViewHolder socialViewHolder) {
        switch (socialData.displayType) {

            case BRAND_DISPLAY_TYPE_1: {
                socialAdapterBrandController.setBrandViewType_1(socialViewHolder, socialData, onSocialItemListener);
                break;
            }
        }
    }

    private void bindAlbumEntity(SocialData socialData, SocialViewHolder socialViewHolder) {
        switch (socialData.displayType) {

            case ALBUM_DISPLAY_TYPE_4: {
                socialAdapterAlbumViewController.setAlbumViewType4(socialViewHolder, socialData, onSocialItemListener);
                break;
            }
        }
    }


}
