package com.example.ddopik.phlogbusiness.ui.campaigns.inner.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.model.ImageObj;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class CampaignInnerPhotosAdapter extends RecyclerView.Adapter<CampaignInnerPhotosAdapter.PhotosViewHolder> {


    private List<ImageObj> photoGrapherPhotosList;
    private Context context;
    private CampaignInnerPhotosAdapter.PhotosViewHolder photosViewHolder;
    public PhotoAction photoAction;

    public CampaignInnerPhotosAdapter(Context context, List<ImageObj> photoGrapherPhotosList) {
        this.context = context;
        this.photoGrapherPhotosList = photoGrapherPhotosList;
    }

    @NonNull
    @Override
    public CampaignInnerPhotosAdapter.PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new PhotosViewHolder(layoutInflater.inflate(R.layout.view_holder_photo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CampaignInnerPhotosAdapter.PhotosViewHolder campaignsViewHolder, int i) {


        GlideApp.with(context)
                .load(photoGrapherPhotosList.get(i).image)
                .centerCrop()
//                .override(450,450)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(campaignsViewHolder.photographerPhoto);
        if (photoAction != null)
            campaignsViewHolder.photographerPhoto.setOnClickListener(view -> {
                photoAction.onPhotoClicked(photoGrapherPhotosList.get(i));
            });
    }

    @Override
    public int getItemCount() {
        return photoGrapherPhotosList.size();
    }


    class PhotosViewHolder extends RecyclerView.ViewHolder {

        ImageView photographerPhoto;

        public PhotosViewHolder(View view) {
            super(view);
            photographerPhoto = view.findViewById(R.id.photographer_photo);
        }
    }

    public interface PhotoAction {
        void onPhotoClicked(ImageObj imageObj);
    }
}