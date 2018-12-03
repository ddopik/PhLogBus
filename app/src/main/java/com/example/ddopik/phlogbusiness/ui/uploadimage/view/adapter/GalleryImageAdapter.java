package com.example.ddopik.phlogbusiness.ui.uploadimage.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.GlideApp;


import java.util.List;

/**
 * Created by abdalla_maged on 10/22/2018.
 */
public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.GalleryImageViewHolder> {

    private List<String> imageList;
    private Context context;
    public OnGalleryImageClicked onGalleryImageClicked;

    public GalleryImageAdapter(Context context, List<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public GalleryImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new GalleryImageAdapter.GalleryImageViewHolder(layoutInflater.inflate(R.layout.view_holder_gallery_photo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryImageViewHolder galleryImageViewHolder, int i) {


        GlideApp.with(context).load(imageList.get(i))
                .placeholder(R.drawable.phlog_logo).centerCrop()
                .into(galleryImageViewHolder.galleryImageImg);

        if (onGalleryImageClicked != null) {
            galleryImageViewHolder.galleryImageImg.setOnClickListener(v -> onGalleryImageClicked.onImageClick(imageList.get(i)));
        }


    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


    class GalleryImageViewHolder extends RecyclerView.ViewHolder {
        ImageView galleryImageImg;

        public GalleryImageViewHolder(View view) {
            super(view);
            galleryImageImg = view.findViewById(R.id.gallery_image_img);
        }
    }

    public interface OnGalleryImageClicked {
        void onImageClick(String imagePath);
    }
}
