package com.example.ddopik.phlogbusiness.ui.social.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.bumptech.glide.request.RequestOptions;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.MainActivity;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity.USER_ID;


public class SocialAdapterProfileViewController {

    private String TAG = SocialAdapterProfileViewController.class.getSimpleName();
    private Context context;

    public SocialAdapterProfileViewController(Context context) {
        this.context = context;
    }


    @SuppressLint("CheckResult")
    public void setProfileType3(Photographer photographer, SocialAdapter.SocialViewHolder socialViewHolder, SocialAdapter.OnSocialItemListener onSocialItemListener) {
        socialViewHolder.socialProfileType3.setVisibility(View.VISIBLE);

        socialViewHolder.socialProfileType3FullName.setText(photographer.fullName);
        socialViewHolder.socialProfileType3UserName.setText(photographer.userName);
        GlideApp.with(context)
                .load(photographer.imageProfile)
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.default_user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(socialViewHolder.socialProfileType3Icon);


        socialViewHolder.socialProfileAlbumType3PhotosContainer.setVisibility(View.INVISIBLE);
        socialViewHolder.socialProfileType3ImgDefaultContainer.setVisibility(View.VISIBLE);

        BaseNetworkApi.getUserProfilePhotos(String.valueOf(photographer.id), 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userPhotosResponse -> {



                            if (userPhotosResponse.data.data != null && userPhotosResponse.data.data.size() >= 3) {

                                socialViewHolder.socialProfileAlbumType3PhotosContainer.setVisibility(View.VISIBLE);
                                socialViewHolder.socialProfileType3ImgDefaultContainer.setVisibility(View.INVISIBLE);


                                GlideApp.with(context)
                                        .load(userPhotosResponse.data.data.get(0).url)
                                        .centerCrop()
                                        .placeholder(R.drawable.default_photographer_profile)
                                        .error(R.drawable.default_photographer_profile)
                                        .into(socialViewHolder.socialProfileType3Img_1);

                                GlideApp.with(context)
                                        .load(userPhotosResponse.data.data.get(1).url)
                                        .placeholder(R.drawable.default_photographer_profile)
                                        .centerCrop()
                                        .error(R.drawable.default_photographer_profile)
                                        .into(socialViewHolder.socialProfileType3Img_2);


                                GlideApp.with(context)
                                        .load(userPhotosResponse.data.data.get(2).url)
                                        .centerCrop()
                                        .placeholder(R.drawable.default_photographer_profile)
                                        .error(R.drawable.default_photographer_profile)
                                        .into(socialViewHolder.socialProfileType3Img_3);


                                GlideApp.with(context)
                                        .load(userPhotosResponse.data.data.get(3).url)
                                        .error(R.drawable.default_photographer_profile)
                                        .apply(new RequestOptions().centerCrop())
                                        .into(socialViewHolder.socialProfileType3Img_4);


                            } else {

                            //      this is acts as default
                                if (photographer.imageCover != null) {

                                    GlideApp.with(context)
                                            .load(photographer.imageCover)
                                            .error(R.drawable.default_photographer_profile)
                                            .into(socialViewHolder.socialProfileType3ImgDefaultContainer);
                                }

                            }


                        }
                        , throwable -> {

                            socialViewHolder.socialProfileAlbumType3PhotosContainer.setVisibility(View.INVISIBLE);
                            socialViewHolder.socialProfileType3ImgDefaultContainer.setVisibility(View.VISIBLE);
                            CustomErrorUtil.Companion.setError(context, TAG, throwable);
                        });


        if (photographer.isFollow) {
            socialViewHolder.followSocialProfileType3Btn.setText(context.getResources().getString(R.string.following));
        } else {
            socialViewHolder.followSocialProfileType3Btn.setText(context.getResources().getString(R.string.follow));
        }

        socialViewHolder.followSocialProfileType3Btn.setOnClickListener(v -> {
            if (photographer.isFollow) {
                unFollowUser(String.valueOf(photographer.id), onSocialItemListener);
            } else {
                followUser(String.valueOf(photographer.id), onSocialItemListener);
            }
        });



        View.OnClickListener onProfileClickListener= v -> {
            if (photographer.id == Integer.parseInt(PrefUtils.getUserId(context))) {

                ((MainActivity) context).navigationManger.navigate(Constants.NavigationHelper.PROFILE);

            } else {


                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra(USER_ID, String.valueOf(photographer.id));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        };

        socialViewHolder.socialProfileAlbumType3PhotosContainer.setOnClickListener(onProfileClickListener);
        socialViewHolder.socialProfileType3ImgDefaultContainer.setOnClickListener(onProfileClickListener);

    }


    @SuppressLint("CheckResult")

    private void followUser(String userId, SocialAdapter.OnSocialItemListener onSocialItemListener) {
        BaseNetworkApi.followUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    onSocialItemListener.onSocialPhotoGrapherFollowed(Integer.parseInt(userId), true);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    private void unFollowUser(String userID, SocialAdapter.OnSocialItemListener onSocialItemListener) {
        BaseNetworkApi.unFollowUser(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    onSocialItemListener.onSocialPhotoGrapherFollowed(Integer.parseInt(userID), false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });

    }


}
