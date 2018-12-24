package com.example.ddopik.phlogbusiness.ui.album.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.ui.album.model.AlbumGroup;
import com.example.ddopik.phlogbusiness.ui.album.model.AlbumImg;


import java.util.List;

/**
 * Created by abdalla_maged on 11/4/2018.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumFlexItem> {

    private Context context;
    private List<AlbumGroup> albumGroupList;
    public OnAlbumImageClicked onAlbumImageClicked;

    public AlbumAdapter(Context context, List<AlbumGroup> albumGroupList) {

        this.context = context;
        this.albumGroupList = albumGroupList;
    }

    @NonNull
    @Override
    public AlbumFlexItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(context).inflate(R.layout.view_holder_flex_album, viewGroup, false);
        AlbumFlexItem albumFlexItem = new AlbumFlexItem(view);
        return albumFlexItem;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumFlexItem albumFlexItem, int position) {


        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));

        if (albumGroupList.get(position).albumGroupList.size() >= 1){
            GlideApp.with(context)
                    .load(albumGroupList.get(position).albumGroupList.get(0).AlbumImg)
                    .placeholder(R.drawable.default_user_pic)
                    .apply(requestOptions)
                    .error(R.drawable.default_photographer_profile)
                    .into(albumFlexItem.albumImg1);

            if (onAlbumImageClicked !=null)
                albumFlexItem.albumImg1.setOnClickListener((v)->onAlbumImageClicked.onImageClicked( albumGroupList.get(position).albumGroupList.get(0)));
        }

        if (albumGroupList.get(position).albumGroupList.size() >= 2) {
            GlideApp.with(context)
                    .load(albumGroupList.get(position).albumGroupList.get(1).AlbumImg)
                    .placeholder(R.drawable.default_user_pic)
                    .apply(requestOptions)
                    .error(R.drawable.default_photographer_profile)
                    .into(albumFlexItem.albumImg2);
            if (onAlbumImageClicked !=null)
                albumFlexItem.albumImg2.setOnClickListener((v)->onAlbumImageClicked.onImageClicked( albumGroupList.get(position).albumGroupList.get(1)));
        }
        if (albumGroupList.get(position).albumGroupList.size() >= 3) {
            GlideApp.with(context)
                    .load(albumGroupList.get(position).albumGroupList.get(2).AlbumImg)
                    .placeholder(R.drawable.default_user_pic)
                    .apply(requestOptions)
                    .error(R.drawable.default_photographer_profile)
                    .into(albumFlexItem.albumImg3);
            if (onAlbumImageClicked !=null)
                albumFlexItem.albumImg3.setOnClickListener((v)->onAlbumImageClicked.onImageClicked( albumGroupList.get(position).albumGroupList.get(2)));
        }
        if (albumGroupList.get(position).albumGroupList.size() >= 4) {
            GlideApp.with(context)
                    .load(albumGroupList.get(position).albumGroupList.get(3).AlbumImg)
                    .placeholder(R.drawable.default_user_pic)
                    .apply(requestOptions)
                    .error(R.drawable.default_photographer_profile)
                    .into(albumFlexItem.albumImg4);
            if (onAlbumImageClicked !=null)
                albumFlexItem.albumImg4.setOnClickListener((v)->onAlbumImageClicked.onImageClicked( albumGroupList.get(position).albumGroupList.get(3)));
        }



    }

    @Override
    public int getItemCount() {
        return albumGroupList.size();
    }

    public class AlbumFlexItem extends RecyclerView.ViewHolder {

        ImageView albumImg1;
        ImageView albumImg2;
        ImageView albumImg3;
        ImageView albumImg4;

        AlbumFlexItem(View view) {
            super(view);
            albumImg1 = view.findViewById(R.id.flex_album_img_1);
            albumImg2 = view.findViewById(R.id.flex_album_img_2);
            albumImg3 = view.findViewById(R.id.flex_album_img_3);
            albumImg4 = view.findViewById(R.id.flex_album_img_4);
        }
    }


    public interface OnAlbumImageClicked{
       void  onImageClicked(AlbumImg albumImg);
    }
}
