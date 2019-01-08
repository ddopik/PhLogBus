package com.example.ddopik.phlogbusiness.ui.album.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;

import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public class AllAlbumImgAdapter extends RecyclerView.Adapter<AllAlbumImgAdapter.AlbumImgViewHolder> {

    private List<BaseImage> albumImgList;
    private Context context;
    public OnAlbumImgClicked onAlbumImgClicked;

    public AllAlbumImgAdapter(List<BaseImage> albumImgList) {
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
                .load(albumImgList.get(i).thumbnailUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .into(albumImgViewHolder.albumIcon);


        GlideApp.with(context)
                .load(albumImgList.get(i).url)
                .centerCrop()
                .error(R.drawable.default_error_img)
                .placeholder(R.drawable.default_place_holder)
                .into(albumImgViewHolder.albumImg);



        if(albumImgList.get(i).thumbnailUrl !=null)
            albumImgViewHolder.albumName.setText(albumImgList.get(i).thumbnailUrl);
        if(albumImgList.get(i).photographer !=null)
            albumImgViewHolder.albumAuthor.setText(albumImgList.get(i).photographer.fullName);
        if(albumImgList.get(i).likesCount !=null)
            albumImgViewHolder.albumImgLikeVal.setText(new StringBuilder().append(albumImgList.get(i).likesCount).append(" Likes").toString());
        if(albumImgList.get(i).commentsCount !=null)
            albumImgViewHolder.albumImgCommentVal.setText(new StringBuilder().append(albumImgList.get(i).commentsCount).append("Comments").toString());

        if (onAlbumImgClicked != null) {
            albumImgViewHolder.albumImg.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgLike.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgLikeClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgComment.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgCommentClick(albumImgList.get(i)));
            albumImgViewHolder.addLightBoxImgBtn.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgAddLightBoxClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgAddToCartBtn.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgToCartClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgLikeVal.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgLikeClick(albumImgList.get(i)));
            albumImgViewHolder.albumImgCommentVal.setOnClickListener(v -> onAlbumImgClicked.onAlbumImgCommentClick(albumImgList.get(i)));
        }

    }

    @Override
    public int getItemCount() {
        return albumImgList.size();
    }

    public class AlbumImgViewHolder extends RecyclerView.ViewHolder {

        ImageView albumIcon, albumImg;
        ImageView addLightBoxImgBtn;
        CustomTextView albumName, albumAuthor, albumImgLikeVal, albumImgCommentVal;
        ImageButton albumImgLike, albumImgComment;
        FrameLayout albumImgAddToCartBtn;

        AlbumImgViewHolder(View view) {
            super(view);
            albumIcon = view.findViewById(R.id.album_icon);
            albumImg = view.findViewById(R.id.album_img);
            addLightBoxImgBtn=view.findViewById(R.id.add_lightBox_img_btn);
            albumName = view.findViewById(R.id.album_name);
             albumAuthor = view.findViewById(R.id.album_author);
            albumImgLikeVal = view.findViewById(R.id.album_img_like_count);
            albumImgCommentVal = view.findViewById(R.id.album_img_comment_count);
            albumImgLike = view.findViewById(R.id.album_img_like_btn);
            albumImgComment = view.findViewById(R.id.album_img_comment);
            albumImgAddToCartBtn = view.findViewById(R.id.album_img_add_to_cart);
        }
    }

    public interface OnAlbumImgClicked {
        void onAlbumImgClick(BaseImage albumImg);

        void onAlbumImgLikeClick(BaseImage albumImg);

        void onAlbumImgCommentClick(BaseImage albumImg);

        void onAlbumImgAddLightBoxClick(BaseImage albumImg);

        void onAlbumImgToCartClick(BaseImage albumImg);


    }
}
