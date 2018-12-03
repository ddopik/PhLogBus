package com.example.ddopik.phlogbusiness.ui.album.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.ui.album.model.AlbumImg;


import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public class AllAlbumImgAdapter extends RecyclerView.Adapter<AllAlbumImgAdapter.AlbumImgViewHolder> {

    private List<AlbumImg> albumImgList;
    private Context context;
    public OnAlbumImgClicked onAlbumImgClicked;

    public AllAlbumImgAdapter(List<AlbumImg> albumImgList) {
        this.albumImgList = albumImgList;
    }


    @NonNull
    @Override
    public AlbumImgViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.view_holder_album_img, viewGroup, false);
        this.context = viewGroup.getContext();
        return new AlbumImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumImgViewHolder albumImgViewHolder, int i) {

        GlideApp.with(context)
                .load(albumImgList.get(i).AlbumIcon)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.phlog_logo)
                .into(albumImgViewHolder.albumIcon);


        GlideApp.with(context)
                .load(albumImgList.get(i).AlbumImg)
                .centerCrop()
                .error(R.drawable.splash_screen_background)
                .placeholder(R.drawable.default_user_pic)
                .into(albumImgViewHolder.albumImg);

        albumImgViewHolder.albumName.setText(albumImgList.get(i).albumName);
        albumImgViewHolder.albumAuthor.setText(albumImgList.get(i).albumAuthorName);
        albumImgViewHolder.albumImgLikeVal.setText(new StringBuilder().append(albumImgList.get(i).AlbumImgLikesCount).append(" Likes").toString());
        albumImgViewHolder.albumImgCommentVal.setText(new StringBuilder().append(albumImgList.get(i).AlbumImgCommentsCount).append("Comments").toString());

        if (onAlbumImgClicked !=null){
            albumImgViewHolder.albumImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAlbumImgClicked.onAlbumImgClick(albumImgList.get(i));
                }
            });
            albumImgViewHolder.albumImgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAlbumImgClicked.onAlbumImgLikeClick(albumImgList.get(i));

                }
            });
            albumImgViewHolder.albumImgComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAlbumImgClicked.onAlbumImgCommentClick(albumImgList.get(i));

                }
            });
            albumImgViewHolder.albumImgDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAlbumImgClicked.onAlbumImgDownloadClick(albumImgList.get(i));
                }
            });
            albumImgViewHolder.albumImgLikeVal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAlbumImgClicked.onAlbumImgLikeClick(albumImgList.get(i));
                }
            });
            albumImgViewHolder.albumImgCommentVal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAlbumImgClicked.onAlbumImgCommentClick(albumImgList.get(i));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return albumImgList.size();
    }

    public class AlbumImgViewHolder extends RecyclerView.ViewHolder {

        ImageView albumIcon, albumImg;
        TextView albumName, albumAuthor, albumImgLikeVal, albumImgCommentVal;
        ImageButton albumImgLike, albumImgComment, albumImgDownload;

        AlbumImgViewHolder(View view) {
            super(view);
            albumIcon = view.findViewById(R.id.album_icon);
            albumImg = view.findViewById(R.id.album_img);
            albumName = view.findViewById(R.id.album_name);
            albumAuthor = view.findViewById(R.id.album_author);
            albumImgLikeVal = view.findViewById(R.id.album_img_like_val);
            albumImgCommentVal = view.findViewById(R.id.album_img_comment_val);
            albumImgLike = view.findViewById(R.id.album_img_like);
            albumImgComment = view.findViewById(R.id.album_img_comment);
            albumImgDownload = view.findViewById(R.id.album_img_download);
        }
    }

    public interface OnAlbumImgClicked {
        void onAlbumImgClick(AlbumImg albumImg);
        void onAlbumImgLikeClick(AlbumImg albumImg);
        void onAlbumImgCommentClick(AlbumImg albumImg);
        void onAlbumImgDownloadClick(AlbumImg albumImg);


    }
}
