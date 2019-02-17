package com.example.ddopik.phlogbusiness.ui.social.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity;
import com.example.ddopik.phlogbusiness.ui.social.model.SocialData;
import com.example.ddopik.phlogbusiness.utiltes.Constants;


import java.util.ArrayList;

import static com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity.ALL_ALBUM_IMAGES;
import static com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity.LIST_TYPE;
import static com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity.SELECTED_IMG_ID;

public class SocialAdapterPhotosViewController {


    private Context context;

    public SocialAdapterPhotosViewController(Context context) {
        this.context = context;
    }


    public void setPhotosViewType5(SocialAdapter.SocialViewHolder socialViewHolder, SocialData socialData, SocialAdapter.OnSocialItemListener onSocialItemListener) {


        socialViewHolder.socialImageName.setText(socialData.title);

        socialViewHolder.socialImageSliderType5.setVisibility(View.VISIBLE);
        SocialImagesAdapter socialImagesAdapter = new SocialImagesAdapter(socialData.photos);

        socialViewHolder.socialImgSlideRv.setAdapter(socialImagesAdapter);


            socialImagesAdapter.onSocialSliderImgClick = img -> {
                Intent intent = new Intent(context, AllAlbumImgActivity.class);

                intent.putParcelableArrayListExtra(ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) socialData.photos);
                intent.putExtra(SELECTED_IMG_ID,socialData.photos.get(0).id);
                intent.putExtra(LIST_TYPE, Constants.PhotosListType.SOCIAL_LIST);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);

            };

    }


}
