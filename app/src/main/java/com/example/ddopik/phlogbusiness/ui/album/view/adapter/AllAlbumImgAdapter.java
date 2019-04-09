package com.example.ddopik.phlogbusiness.ui.album.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Tag;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;

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
        if (albumImgList.get(i).isLiked !=null && !albumImgList.get(i).isLiked) {
            albumImgViewHolder.albumImgLike.setImageResource(R.drawable.ic_like_off_gray);
        } else {
            albumImgViewHolder.albumImgLike.setImageResource(R.drawable.ic_like_on);
        }
        if (albumImgList.get(i).photographer.isFollow) {
            albumImgViewHolder.followPhotoGrapherBtn.setText(context.getResources().getString(R.string.un_follow));
        } else {
            albumImgViewHolder.followPhotoGrapherBtn.setText(context.getResources().getString(R.string.follow));
        }


        if (onAlbumImgClicked != null) {
            albumImgViewHolder.albumImgAddToCartBtn.setOnClickListener(v -> {
                onAlbumImgClicked.onAlbumImgAddToCartClick(albumImgList.get(i));
            });


        }


        if (onAlbumImgClicked != null) {
            albumImgViewHolder.albumImg.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgLike.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgLikeClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgComment.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgCommentClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgLikeVal.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgLikeClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgCommentVal.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgCommentClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgHeader.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgHeaderClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgAddToCartBtn.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgAddToCartClick((albumImgList.get(i))));
            albumImgViewHolder.albumImgAddToLightBox.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgLightBoxClick(albumImgList.get(i)));
            albumImgViewHolder.followPhotoGrapherBtn.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgFollowClick(albumImgList.get(i)));
        }

        albumImgViewHolder.albumImgAddToCartBtn.setVisibility(View.INVISIBLE);
        albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.INVISIBLE);
        albumImgViewHolder.albumImgAddToCartBtn.setVisibility(View.INVISIBLE);
        albumImgViewHolder.albumImgAddToLightBox.setVisibility(View.INVISIBLE);
        switch (photosListType) {
            case SOCIAL_LIST: {
                albumImgViewHolder.albumImgAddToCartBtn.setVisibility(View.INVISIBLE);
                albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.INVISIBLE);
                albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.INVISIBLE);

                break;
            }
//            case CURRENT_PHOTOGRAPHER_SAVED_LIST: {
//                albumImgViewHolder.albumImgAddToCartBtn.setVisibility(View.VISIBLE);
//                albumImgViewHolder.albumImgDeleteBtn.setVisibility(View.INVISIBLE);
//                albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.INVISIBLE);
//                break;
//            }
//            case CURRENT_PHOTOGRAPHER_PHOTOS_LIST: {
//                albumImgViewHolder.albumImgAddToCartBtn.setVisibility(View.INVISIBLE);
//                albumImgViewHolder.albumImgDeleteBtn.setVisibility(View.VISIBLE);
//                albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.INVISIBLE);
//                break;
//            }
            case USER_PROFILE_PHOTOS_LIST: {
                albumImgViewHolder.albumImgAddToCartBtn.setVisibility(View.VISIBLE);
                albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.INVISIBLE);
                 if (albumImgList.get(i).isSaved) {
                    albumImgViewHolder.albumImgAddToLightBox.setVisibility(View.INVISIBLE);
                } else {
                    albumImgViewHolder.albumImgAddToLightBox.setVisibility(View.VISIBLE);
                }

                break;
            }
//            currently not available
//            ca/se ALBUM_PREVIEW_LIST: {
//                albumImgViewHolder.albumImgAddToCartBtn.setVisibility(View.VISIBLE);
//                albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.VISIBLE);
//                break;
//            }
//            currently not available
//            case CAMPAIGN_IMAGES_LIST: {
//                albumImgViewHolder.albumImgAddToCartBtn.setVisibility(View.VISIBLE);
//                albumImgViewHolder.albumImgDeleteBtn.setVisibility(View.INVISIBLE);
//                albumImgViewHolder.followPhotoGrapherBtn.setVisibility(View.VISIBLE);
//                break;
//            }
        }
    }

    @Override
    public int getItemCount() {
        return albumImgList.size();
    }

    public class AlbumImgViewHolder extends RecyclerView.ViewHolder {

        LinearLayout albumImgHeader;
        ImageView albumIcon, albumImg;
        TextView albumName, albumAuthor, imageCommentTagVal, albumImgLikeVal, albumImgCommentVal;
        ImageButton albumImgLike, albumImgComment, albumImgAddToLightBox;
        Button followPhotoGrapherBtn;
        FrameLayout albumImgAddToCartBtn;

        AlbumImgViewHolder(View view) {
            super(view);
            albumImgHeader = view.findViewById(R.id.album_img_header);
            albumIcon = view.findViewById(R.id.album_icon);
            albumImg = view.findViewById(R.id.album_img);
            albumName = view.findViewById(R.id.album_name);
            imageCommentTagVal = view.findViewById(R.id.image_comment_tag_val);
            albumAuthor = view.findViewById(R.id.album_author);
            albumImgLikeVal = view.findViewById(R.id.album_img_like_count);
            albumImgCommentVal = view.findViewById(R.id.album_img_comment_count);
            albumImgLike = view.findViewById(R.id.album_img_like_btn);
            albumImgComment = view.findViewById(R.id.album_img_comment);
            albumImgAddToCartBtn = view.findViewById(R.id.album_img_add_to_cart);
            albumImgAddToLightBox = view.findViewById(R.id.add_album_img_to_light_box_btn);
            followPhotoGrapherBtn = view.findViewById(R.id.follow_photographer);
        }
    }

    public interface OnAlbumImgClicked {
        void onAlbumImgClick(BaseImage albumImg);


        void onAlbumImgLikeClick(BaseImage albumImg);

        void onAlbumImgCommentClick(BaseImage albumImg);

        void onAlbumImgAddToCartClick(BaseImage albumImg);

        void onAlbumImgLightBoxClick(BaseImage albumImg);

        void onAlbumImgFollowClick(BaseImage albumImg);

        void onAlbumImgHeaderClick(BaseImage albumImg);


    }
}
