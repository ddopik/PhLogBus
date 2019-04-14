package com.example.ddopik.phlogbusiness.ui.social.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.ui.album.view.AlbumPreviewActivity;
import com.example.ddopik.phlogbusiness.ui.social.model.SocialData;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;

import static com.example.ddopik.phlogbusiness.ui.album.view.AlbumPreviewActivity.ALBUM_PREVIEW_ID;


public class SocialAdapterAlbumViewController {
    Context context;

    public SocialAdapterAlbumViewController(Context context) {
        this.context = context;
    }


    @SuppressLint("CheckResult")
    public void setAlbumViewType4(SocialAdapter.SocialViewHolder socialViewHolder, SocialData socialAlbumData, SocialAdapter.OnSocialItemListener onSocialItemListener) {
        socialViewHolder.storyTitle.setText(socialAlbumData.title);
        socialViewHolder.socialAlbumType4.setVisibility(View.VISIBLE);

        socialViewHolder.socialAlbumImgGroupContainer.setVisibility(View.VISIBLE);
        socialViewHolder.socialDefaultAlbumImg.setVisibility(View.VISIBLE);


        if (socialAlbumData.albums.get(0).photos.size() >= 3) {
            //hide default view if exist
            socialViewHolder.socialAlbumImgGroupContainer.setVisibility(View.VISIBLE);
            socialViewHolder.socialDefaultAlbumImg.setVisibility(View.GONE);


            GlideApp.with(context)
                    .load(socialAlbumData.albums.get(0).photos.get(0))
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(socialViewHolder.socialAlbum1);


            GlideApp.with(context)
                    .load(socialAlbumData.albums.get(0).photos.get(1))
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(socialViewHolder.socialAlbum2);


            GlideApp.with(context)
                    .load(socialAlbumData.albums.get(0).photos.get(2))
                    .placeholder(R.drawable.default_photographer_profile)
                    .error(R.drawable.default_photographer_profile)
                    .into(socialViewHolder.socialAlbum3);
        } else {
            // handle if Album has photos less than 3 or has no photos
            socialViewHolder.socialAlbumImgGroupContainer.setVisibility(View.VISIBLE);
            socialViewHolder.socialDefaultAlbumImg.setVisibility(View.VISIBLE);
            GlideApp.with(context)
                    .load(socialAlbumData.albums.get(0).photos.get(0))
                    .error(R.drawable.default_photographer_profile)
                    .apply(new RequestOptions().centerCrop())
                    .into(socialViewHolder.socialDefaultAlbumImg);
        }

        socialViewHolder.socialAlbumOverLayListener.setOnClickListener(v -> {
            Intent intent = new Intent(context, AlbumPreviewActivity.class);
            intent.putExtra(ALBUM_PREVIEW_ID, socialAlbumData.albums.get(0).id);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        });

        ///////////
        GlideApp.with(context)
                .load(socialAlbumData.albums.get(0).photos.get(0))
                .error(R.drawable.default_photographer_profile)
                .apply(new RequestOptions().centerCrop())
                .into(socialViewHolder.socialAlbumIconImg);
        socialViewHolder.socialAlbumName.setText(socialAlbumData.title);
//        if (socialAlbumData.albums != null) {
//            socialViewHolder.socialAlbumPhotosNumber.setText(new StringBuilder().append(socialAlbumData.albums.size()).append(" ").append("photo").toString());
//        } else {
//            socialViewHolder.socialAlbumPhotosNumber.setText(new StringBuilder().append("0").append(" ").append("photo").toString());
//        }
////////////////////
    }


}

