package com.example.ddopik.phlogbusiness.ui.album.view.adapter;

import android.content.Context;
 import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Tag;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;


import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public class AllAlbumImgAdapter extends RecyclerView.Adapter<AllAlbumImgAdapter.AlbumImgViewHolder> {

    private List<BaseImage> albumImgList;
    private Context context;
    private Constants.PhotosListType photosListType;
    public OnAlbumImgClicked onAlbumImgClicked;

    public AllAlbumImgAdapter(List<BaseImage> albumImgList, Constants.PhotosListType photosListType) {
        this.albumImgList = albumImgList;
        this.photosListType = photosListType;
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
                .load(albumImgList.get(i).thumbnailUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(R.drawable.default_user_pic)
                .error(R.drawable.phlog_logo)
                .into(albumImgViewHolder.albumIcon);


        GlideApp.with(context)
                .load(albumImgList.get(i).url)
                .centerCrop()
                .error(R.drawable.default_error_img)
                .placeholder(R.drawable.default_user_pic)
                .into(albumImgViewHolder.albumImg);

        String tagS = "";
        if (albumImgList.get(i).tags != null) {
            for (Tag tag : albumImgList.get(i).tags) {
                tagS = tagS + " #" + tag.name;
            }
        }
        albumImgViewHolder.imageCommentTagVal.setText(tagS);
        if (albumImgList.get(i).thumbnailUrl != null)
            albumImgViewHolder.albumName.setText(albumImgList.get(i).thumbnailUrl);
        if (albumImgList.get(i).photographer != null)
            albumImgViewHolder.albumName.setText(albumImgList.get(i).photographer.fullName);
        if (albumImgList.get(i).photographer != null)
            albumImgViewHolder.albumAuthor.setText(new StringBuilder().append("@").append(albumImgList.get(i).photographer.userName).toString());
        if (albumImgList.get(i).likesCount != null)
            albumImgViewHolder.albumImgLikeVal.setText(new StringBuilder().append(albumImgList.get(i).likesCount).append(" Likes").toString());
        if (albumImgList.get(i).commentsCount != null)
            albumImgViewHolder.albumImgCommentVal.setText(new StringBuilder().append(albumImgList.get(i).commentsCount).append(" Comments").toString());
        if (!albumImgList.get(i).isLiked) {
            albumImgViewHolder.albumImgLike.setImageResource(R.drawable.ic_like_off_gray);
        } else {
            albumImgViewHolder.albumImgLike.setImageResource(R.drawable.ic_like_on);
        }

        //case this list is for current user and already saved to his profile
        if (albumImgList.get(i).photographer.id != Integer.parseInt(PrefUtils.getBrandId(context))) {



            if (albumImgList.get(i).photographer.id == Integer.parseInt(PrefUtils.getBrandId(context))) {
                 albumImgViewHolder.albumImgDeleteBtn.setVisibility(View.VISIBLE);
                albumImgViewHolder.albumImgSaveBtn.setVisibility(View.INVISIBLE);

            } else {
                albumImgViewHolder.albumImgDeleteBtn.setVisibility(View.INVISIBLE);
                albumImgViewHolder.albumImgSaveBtn.setVisibility(View.VISIBLE);

            }


            if (onAlbumImgClicked != null) {
                albumImgViewHolder.albumImgSaveBtn.setOnClickListener(v -> {
                    onAlbumImgClicked.onAlbumImgSaveClick(albumImgList.get(i));
                });
                albumImgViewHolder.albumImgDeleteBtn.setOnClickListener(v -> {
                    onAlbumImgClicked.onAlbumImgDeleteClick(albumImgList.get(i));
                });

            }


        } else {
            albumImgViewHolder.albumImgSaveBtn.setVisibility(View.GONE);
        }

        //case this list is specified for multiple users not for only one
//        if (photosListType != null && photosListType == Constants.PhotosListType.SOCIAL_LIST && albumImgList.get(i).photographer.id != Integer.parseInt(PrefUtils.getBrandId(context)) && !albumImgList.get(i).photographer.isFollow) {
        if (photosListType != null && photosListType == Constants.PhotosListType.SOCIAL_LIST  ) {
            albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.GONE);
            albumImgViewHolder.albumImgSaveBtn.setVisibility(View.GONE);
//            if (onAlbumImgClicked != null)
//                albumImgViewHolder.followPhotoGrapherBtn.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgFollowClick(albumImgList.get(i)));
        }
        else {
            albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.GONE);
            albumImgViewHolder.albumImgDeleteBtn.setVisibility(View.GONE);
        }


        if (onAlbumImgClicked != null) {
            albumImgViewHolder.albumImg.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgLike.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgLikeClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgComment.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgCommentClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgLikeVal.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgLikeClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgCommentVal.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgCommentClick(albumImgList.get(i)));
            albumImgViewHolder.albumIcon.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgPhotoGrapherIconClick(albumImgList.get(i)));
        }

    }

    @Override
    public int getItemCount() {
        return albumImgList.size();
    }

    public class AlbumImgViewHolder extends RecyclerView.ViewHolder {

        ImageView albumIcon, albumImg;
        TextView albumName, albumAuthor, imageCommentTagVal, albumImgLikeVal, albumImgCommentVal;
        ImageButton albumImgLike, albumImgComment, albumImgSaveBtn;
        Button followPhotoGrapherBtn,albumImgDeleteBtn;

        AlbumImgViewHolder(View view) {
            super(view);
            albumIcon = view.findViewById(R.id.album_icon);
            albumImg = view.findViewById(R.id.album_img);
            albumName = view.findViewById(R.id.album_name);
            imageCommentTagVal = view.findViewById(R.id.image_comment_tag_val);
            albumAuthor = view.findViewById(R.id.album_author);
            albumImgLikeVal = view.findViewById(R.id.album_img_like_count);
            albumImgCommentVal = view.findViewById(R.id.album_img_comment_count);
            albumImgLike = view.findViewById(R.id.album_img_like_btn);
            albumImgComment = view.findViewById(R.id.album_img_comment);
            albumImgSaveBtn = view.findViewById(R.id.album_img_save_btn);
            albumImgDeleteBtn = view.findViewById(R.id.album_img_delete_btn);
            followPhotoGrapherBtn = view.findViewById(R.id.follow_photographer);
        }
    }

    public interface OnAlbumImgClicked {
        void onAlbumImgClick(BaseImage albumImg);

        void onAlbumImgDeleteClick(BaseImage albumImg);

        void onAlbumImgLikeClick(BaseImage albumImg);

        void onAlbumImgCommentClick(BaseImage albumImg);

        void onAlbumImgSaveClick(BaseImage albumImg);

        void onAlbumImgFollowClick(BaseImage albumImg);

        void onAlbumImgPhotoGrapherIconClick(BaseImage albumImg);


    }
}
