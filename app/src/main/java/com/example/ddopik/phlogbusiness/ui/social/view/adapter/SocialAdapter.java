package com.example.ddopik.phlogbusiness.ui.social.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.ui.social.model.Entite;
import com.example.ddopik.phlogbusiness.ui.social.model.SocialData;


import java.util.List;

import static com.example.ddopik.phlogbusiness.Utiltes.Constants.*;


/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.SocialViewHolder> {

    private Context context;
    private List<SocialData> socialDataList;


    public OnSocialItemListener onSocialItemListener;


    public SocialAdapter(List<SocialData> socialDataList) {
        this.socialDataList = socialDataList;
    }

    @NonNull
    @Override
    public SocialViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new SocialViewHolder(layoutInflater.inflate(R.layout.view_holder_main_social_item, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull SocialViewHolder socialViewHolder, int i) {

        switch (socialDataList.get(i).entityId) {

            case ENTITY_PROFILE: {
                bindProfileEntity(socialDataList.get(i).entites.get(0), socialViewHolder);
                break;
            }
            case ENTITY_CAMPAIGN: {
                bindCampaignEntity(socialDataList.get(i).entites.get(0), socialViewHolder);
                break;
            }
            case ENTITY_ALBUM: {
                bindAlbumEntity(socialDataList.get(i).entites.get(0),socialDataList.get(i).albumName, socialViewHolder);
                break;
            }
            case ENTITY_IMAGE: {
                bindImageSlider(socialDataList.get(i).entites.get(0),socialDataList.get(i).title, socialViewHolder);
                break;
            }
            case ENTITY_BRAND: {
                bindBrandEntity(socialDataList.get(i).entites.get(0), socialViewHolder);
                break;
            }


        }


    }

    @Override
    public int getItemCount() {
        return socialDataList.size();
    }

    class SocialViewHolder extends RecyclerView.ViewHolder {

        FrameLayout socialProfileType3, socialImageSliderType5, socialCampaignType1, socialBrandType1,socialAlbumType4;

        ImageView socialProfileIcon, socialProfileImg_1, socialProfileImg_2, socialProfileImg_3, socialProfileImg_4;
        TextView socialProfileFullName, socialProfileUserName;
        Button followSocialProfile;
        LinearLayout socialProfileContainer;
        /////
        CustomRecyclerView socialImgSlideRv;
        ImageView socialItemSliderIcon;
        TextView socialImageName;
        /////
        LinearLayout socailCampaignConatainer;
        ImageView socialCampaignIcon, socialCampaignImg;
        TextView socialCampaignName, socialCampaignTitle, socialCampaignDayLeft;
        Button socialJoinCampaignBtn;
        /////
        ImageView socialBrandIconImg, socialBrandImg;
        TextView socialBrandName, socialBrandFollowing;
        Button followBrandBtn;
        /////
        ImageView socialAlbumIconImg,socialAlbum1,socialAlbum2,socialAlbum3;
        TextView socialAlbumName,socialAlbumPhotosNumber;


        SocialViewHolder(View view) {
            super(view);


            socialProfileType3 = view.findViewById(R.id.social_profile_type_3);
            socialImageSliderType5 = view.findViewById(R.id.images_slider_type_5);
            socialCampaignType1 = view.findViewById(R.id.social_campaign_type_1);
            socialBrandType1 = view.findViewById(R.id.social_brand_type_1);
            socialAlbumType4 = view.findViewById(R.id.social_album_type_4);
            /////profileItemView type_1
            socialProfileIcon = view.findViewById(R.id.social_profile_icon_img);
            socialProfileFullName = view.findViewById(R.id.social_profile_full_name);
            socialProfileUserName = view.findViewById(R.id.social_profile_user_name);
            followSocialProfile = view.findViewById(R.id.follow_social_profile);
            socialProfileImg_1 = view.findViewById(R.id.social_profile_img_1);
            socialProfileImg_2 = view.findViewById(R.id.social_profile_img_2);
            socialProfileImg_3 = view.findViewById(R.id.social_profile_img_3);
            socialProfileImg_4 = view.findViewById(R.id.social_profile_img_4);
            socialProfileContainer = view.findViewById(R.id.social_profile_container);
            /////ImageSlider type_1
            socialImgSlideRv = view.findViewById(R.id.social_img_slider_rv);
            socialItemSliderIcon = view.findViewById(R.id.social_icon_img);
            socialImageName = view.findViewById(R.id.social_image_name);
            /////CampaignItemView
            socailCampaignConatainer = view.findViewById(R.id.social_campaign_container);
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
            socialAlbumIconImg=view.findViewById(R.id.social_album_icon_img);
            socialAlbumName=view.findViewById(R.id.social_album_name);
            socialAlbumPhotosNumber=view.findViewById(R.id.social_album_photos_number);
            socialAlbum1=view.findViewById(R.id.social_album_img_1);
            socialAlbum2=view.findViewById(R.id.social_album_img_2);
            socialAlbum3=view.findViewById(R.id.social_album_img_3);






        }
    }

    public interface OnSocialItemListener {
        void onSocialProfileClick(Entite entite);

        void OnFollowSocialProfileClick(Entite entite);

        void onSocialCampaignClicked(Entite entite);

        void onSocialFollowCampaignClicked(Entite entite);

        void onSocialSlideImageClicked(Entite entite);

        void onSocialBrandClicked(Entite entite);

        void onSocialBrandFollowClicked(Entite entite);
    }

    private void bindProfileEntity(Entite entite, SocialViewHolder socialViewHolder) {

        switch (entite.displayType) {

            case PROFILE_DISPLAY_TYPE_3:
                socialViewHolder.socialProfileType3.setVisibility(View.VISIBLE);
                GlideApp.with(context)
                        .load(entite.thumbnail)
                        .placeholder(R.drawable.default_user_pic)
                        .error(R.drawable.default_user_pic)
                        .apply(RequestOptions.circleCropTransform())
                        .into(socialViewHolder.socialProfileIcon);
                GlideApp.with(context)
                        .load(entite.imgs.get(0))
                        .centerCrop()
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .apply(new RequestOptions().centerCrop())
                        .into(socialViewHolder.socialProfileImg_1);
                GlideApp.with(context)
                        .load(entite.imgs.get(1))
                        .placeholder(R.drawable.default_photographer_profile)
                        .centerCrop()
                        .error(R.drawable.default_photographer_profile)
                        .apply(new RequestOptions().centerCrop())
                        .into(socialViewHolder.socialProfileImg_2);
                GlideApp.with(context)
                        .load(entite.imgs.get(2))
                        .centerCrop()
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .apply(new RequestOptions().centerCrop())
                        .into(socialViewHolder.socialProfileImg_3);
                GlideApp.with(context)
                        .load(entite.imgs.get(3))
                        .centerCrop()
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .apply(new RequestOptions().centerCrop())
                        .into(socialViewHolder.socialProfileImg_4);

                socialViewHolder.socialProfileFullName.setText(entite.fullName);
                socialViewHolder.socialProfileUserName.setText(entite.userName);

                if (onSocialItemListener != null) {

                    socialViewHolder.socialProfileContainer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onSocialItemListener.onSocialProfileClick(entite);
                        }
                    });
                    socialViewHolder.followSocialProfile.setOnClickListener(v -> {
                        onSocialItemListener.OnFollowSocialProfileClick(entite);

                    });
                }
        }
    }

    private void bindImageSlider(Entite entite, String title,SocialViewHolder socialViewHolder) {
        switch (entite.displayType) {

            case IMGS_DISPLAY_TYPE_5: {
                GlideApp.with(context)
                        .load(entite.imgs.get(0))
                        .apply(RequestOptions.circleCropTransform())
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .into(socialViewHolder.socialItemSliderIcon);

                socialViewHolder.socialImageName.setText(title);

                socialViewHolder.socialImageSliderType5.setVisibility(View.VISIBLE);
                SocialImagesAdapter socialImagesAdapter = new SocialImagesAdapter(entite);///todo img should be obj

                socialViewHolder.socialImgSlideRv.setAdapter(socialImagesAdapter);

                if (onSocialItemListener != null) {
                    socialImagesAdapter.onSocialSliderImgClick = img -> {
                        onSocialItemListener.onSocialSlideImageClicked(entite);///todo img should be obj

                    };
                }
            }

        }
    }

    private void bindCampaignEntity(Entite entite, SocialViewHolder socialViewHolder) {
        switch (entite.displayType) {

            case CAMPAIGN_DISPLAY_TYPE_1:
                socialViewHolder.socialCampaignType1.setVisibility(View.VISIBLE);

                GlideApp.with(context)
                        .load(entite.thumbnail)
                        .placeholder(R.drawable.default_user_pic)
                        .error(R.drawable.default_user_pic)
                        .apply(RequestOptions.circleCropTransform())
                        .into(socialViewHolder.socialCampaignIcon);

                GlideApp.with(context)
                        .load(entite.imgs.get(0))
                        .centerCrop()
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .into(socialViewHolder.socialCampaignImg);

                socialViewHolder.socialCampaignTitle.setText(entite.nameEn);
                socialViewHolder.socialCampaignDayLeft.setText("day left here");
                socialViewHolder.socialCampaignName.setText(entite.nameEn);

                if (onSocialItemListener != null) {
                    socialViewHolder.socialJoinCampaignBtn.setOnClickListener(v -> {
                        onSocialItemListener.onSocialFollowCampaignClicked(entite);
                    });
                    socialViewHolder.socailCampaignConatainer.setOnClickListener(v -> onSocialItemListener.onSocialCampaignClicked(entite));

                }

        }

    }

    private void bindBrandEntity(Entite entite, SocialViewHolder socialViewHolder) {
        switch (entite.displayType) {

            case BRAND_DISPLAY_TYPE_1: {
                socialViewHolder.socialBrandType1.setVisibility(View.VISIBLE);

                GlideApp.with(context)
                        .load(entite.thumbnail)
                        .placeholder(R.drawable.default_user_pic)
                        .error(R.drawable.default_user_pic)
                        .apply(RequestOptions.circleCropTransform())
                        .into(socialViewHolder.socialBrandIconImg);

                GlideApp.with(context)
                        .load(entite.brandCoverimg)
                        .centerCrop()
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .into(socialViewHolder.socialBrandImg);

                socialViewHolder.socialBrandName.setText(entite.nameEn);
                socialViewHolder.socialBrandFollowing.setText(entite.numberOfFollowers);

                if (onSocialItemListener != null) {
                    socialViewHolder.socialBrandImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onSocialItemListener.onSocialBrandClicked(entite);
                        }
                    });
                }
                if (onSocialItemListener != null) {
                    socialViewHolder.followBrandBtn.setOnClickListener(v -> {
                        onSocialItemListener.onSocialBrandFollowClicked(entite);
                    });
                }
                break;
            }
        }
    }

    private void bindAlbumEntity(Entite entite,String AlbumName, SocialViewHolder socialViewHolder) {
        switch (entite.displayType) {

            case ALBUM_DISPLAY_TYPE_4: {
                socialViewHolder.socialAlbumType4.setVisibility(View.VISIBLE);

                GlideApp.with(context)
                        .load(entite.imgs.get(0))
                        .placeholder(R.drawable.default_user_pic)
                        .error(R.drawable.default_user_pic)
                        .apply(RequestOptions.circleCropTransform())
                        .into(socialViewHolder.socialAlbumIconImg);

                GlideApp.with(context)
                        .load(entite.imgs.get(0))
                        .centerCrop()
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .into(socialViewHolder.socialAlbum1);

                GlideApp.with(context)
                        .load(entite.imgs.get(1))
                        .centerCrop()
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .into(socialViewHolder.socialAlbum2);

                GlideApp.with(context)
                        .load(entite.imgs.get(2))
                        .centerCrop()
                        .placeholder(R.drawable.default_photographer_profile)
                        .error(R.drawable.default_photographer_profile)
                        .into(socialViewHolder.socialAlbum3);

                socialViewHolder.socialAlbumName.setText(AlbumName);
                socialViewHolder.socialAlbumPhotosNumber.setText(new StringBuilder().append(entite.imgs.size()).append(" ").append("photo").toString());
                break;
            }
        }
    }

}
