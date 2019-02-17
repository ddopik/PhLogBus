package com.example.ddopik.phlogbusiness.ui.social.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;


import java.util.List;

/**
 * Created by abdalla_maged on 11/13/2018.
 */
public class SocialImagesAdapter extends RecyclerView.Adapter<SocialImagesAdapter.SocialImageViewHolder> {

    private Context context;
    private List<BaseImage> imageList;
    public OnSocialSliderImgClick onSocialSliderImgClick;
    public SocialImagesAdapter(List<BaseImage> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public SocialImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        return new SocialImageViewHolder(layoutInflater.inflate(R.layout.view_holder_social_image, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SocialImageViewHolder socialImageViewHolder, int i) {
        if (imageList.get(i).url !=null && !imageList.get(i).url.equals("")){
        GlideApp.with(context)
                .load(imageList.get(i).url)
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .into(socialImageViewHolder.sliderImage);
        if (onSocialSliderImgClick != null) {
            socialImageViewHolder.sliderImage.setOnClickListener((View.OnClickListener) v -> {
                onSocialSliderImgClick.onSocialSliderImfClicked(imageList.get(i));

            });
        }
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class SocialImageViewHolder extends RecyclerView.ViewHolder {
        ImageView sliderImage;

        SocialImageViewHolder(View view) {
            super(view);
            sliderImage = view.findViewById(R.id.social_slider_img);
        }
    }

    interface OnSocialSliderImgClick {
        void onSocialSliderImfClicked(BaseImage img);
    }
}
