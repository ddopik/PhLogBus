package com.example.ddopik.phlogbusiness.ui.campaigns.inner.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class CampaignInnerPhotosAdapter extends RecyclerView.Adapter<CampaignInnerPhotosAdapter.PhotosViewHolder> {


    private List<BaseImage> photoGrapherPhotosList;
    private Context context;
    public PhotoAction photoAction;

    public CampaignInnerPhotosAdapter(Context context, List<BaseImage> photoGrapherPhotosList) {
        this.context = context;
        this.photoGrapherPhotosList = photoGrapherPhotosList;
    }

    @NonNull
    @Override
    public CampaignInnerPhotosAdapter.PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new PhotosViewHolder(layoutInflater.inflate(R.layout.view_holder_photo_square_badge, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CampaignInnerPhotosAdapter.PhotosViewHolder viewHolder, int i) {


        GlideApp.with(context)
                .load(photoGrapherPhotosList.get(i).url)
//                .override(450,450)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(viewHolder.photographerPhoto);
        if (photoAction != null)
            viewHolder.photographerPhoto.setOnClickListener(view -> {
                photoAction.onPhotoClicked(photoGrapherPhotosList.get(i));
            });

        if (photoGrapherPhotosList.get(i).isWon) viewHolder.isWonBadge.setVisibility(View.VISIBLE);
        else viewHolder.isWonBadge.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return photoGrapherPhotosList.size();
    }


    class PhotosViewHolder extends RecyclerView.ViewHolder {

        ImageView photographerPhoto, isWonBadge;

        public PhotosViewHolder(View view) {
            super(view);
            photographerPhoto = view.findViewById(R.id.photographer_photo);
            isWonBadge = view.findViewById(R.id.is_won_badge);
        }
    }

    public interface PhotoAction {
        void onPhotoClicked(BaseImage baseImage);
    }
}