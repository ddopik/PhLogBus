package com.example.ddopik.phlogbusiness.ui.userprofile.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

import io.reactivex.annotations.NonNull;

import java.util.List;

/**
 * Created by abdalla_maged on 10/11/2018.
 */
public class UserProfilePhotosAdapter extends RecyclerView.Adapter<UserProfilePhotosAdapter.PhotosViewHolder> {


    private List<BaseImage> userPhotoList;
    private Context context;
    private UserProfilePhotosAdapter.PhotosViewHolder photosViewHolder;
    public PhotoAction photoAction;


    public UserProfilePhotosAdapter(Context context, List<BaseImage> userPhotoList) {
        this.context = context;
        this.userPhotoList = userPhotoList;
    }

    @NonNull
    @Override
    public UserProfilePhotosAdapter.PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new PhotosViewHolder(layoutInflater.inflate(R.layout.view_holder_photo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfilePhotosAdapter.PhotosViewHolder campaignsViewHolder, int i) {


        GlideApp.with(context)
                .load(userPhotoList.get(i).url)
                .centerCrop()
//                .override(450,450)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(campaignsViewHolder.photographerPhoto);
        if (photoAction != null)
            campaignsViewHolder.photographerPhoto.setOnClickListener(view -> {
                photoAction.onPhotoClicked(userPhotoList.get(i));
            });
    }

    @Override
    public int getItemCount() {
        return userPhotoList.size();
    }


    class PhotosViewHolder extends RecyclerView.ViewHolder {

        ImageView photographerPhoto;

        public PhotosViewHolder(View view) {
            super(view);
            photographerPhoto = view.findViewById(R.id.photographer_photo);
        }
    }

    public interface PhotoAction {
        void onPhotoClicked(BaseImage baseImage);
    }
}