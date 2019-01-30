package com.example.ddopik.phlogbusiness.ui.lightboxphotos.view;

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
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.ui.lightboxphotos.view.LightboxPhotosAdapter.PhotoActionListener.ActionType;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;

import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public class LightboxPhotosAdapter extends RecyclerView.Adapter<LightboxPhotosAdapter.AlbumImgViewHolder> {

    private List<BaseImage> list;
    private Context context;
    public PhotoActionListener photoActionListener;

    public LightboxPhotosAdapter(List<BaseImage> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public AlbumImgViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.view_holder_lightbox_img, viewGroup, false);
        this.context = viewGroup.getContext();
        return new AlbumImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumImgViewHolder holder, int i) {
        Context context = holder.itemView.getContext();
        BaseImage image = list.get(i);
        GlideApp.with(context)
                .load(image.photographer.imageProfile)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .into(holder.photographerAvatar);
        GlideApp.with(context)
                .load(image.url)
                .centerCrop()
                .error(R.drawable.default_error_img)
                .placeholder(R.drawable.default_place_holder)
                .into(holder.photo);
        if (image.photographer != null) {
            if (image.photographer.fullName != null)
                holder.photographerName.setText(image.photographer.fullName);
            if (image.photographer.userName != null)
                holder.photographerUsername.setText(String.format("@%1$s", image.photographer.userName));
            if (image.photographer.isFollow)
                holder.followButton.setText(R.string.unfollow);
            else holder.followButton.setText(R.string.follow);
        }
        if (image.likesCount != null)
            holder.imgLikeVal.setText(context.getString(R.string.likes_count, image.likesCount));
        if (image.commentsCount != null)
            holder.imgCommentVal.setText(context.getString(R.string.comments_count, image.likesCount));
        if (image.isLiked) {
            holder.likeButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_like_on));
        } else {
            holder.likeButton.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_like_off_gray));
        }
        if (photoActionListener != null) {
            holder.photo.setOnClickListener(v -> photoActionListener.onAction(ActionType.VIEW, image));
            holder.likeButton.setOnClickListener(v -> photoActionListener.onAction(ActionType.LIKE, image));
            holder.commentButton.setOnClickListener(v -> photoActionListener.onAction(ActionType.COMMENT, image));
            holder.followButton.setOnClickListener(v -> photoActionListener.onAction(ActionType.FOLLOW, image));
            holder.deleteButton.setOnClickListener(v -> photoActionListener.onAction(ActionType.DELETE, image));
            holder.addToCartButton.setOnClickListener(v -> photoActionListener.onAction(ActionType.ADD_TO_CART, image));
        }
        if (image.isCart)
            holder.addToCartButton.setVisibility(View.GONE);
        else holder.addToCartButton.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AlbumImgViewHolder extends RecyclerView.ViewHolder {

        ImageView photographerAvatar, photo;
        TextView photographerName, photographerUsername, imgLikeVal, imgCommentVal;
        ImageButton likeButton, commentButton, deleteButton;
        Button followButton;
        View addToCartButton;

        AlbumImgViewHolder(View view) {
            super(view);
            photographerAvatar = view.findViewById(R.id.photographer_avatar);
            photo = view.findViewById(R.id.image);
            photographerName = view.findViewById(R.id.photographer_name);
            photographerUsername = view.findViewById(R.id.photographer_username);
            imgLikeVal = view.findViewById(R.id.img_likes_count);
            imgCommentVal = view.findViewById(R.id.img_comments_count);
            likeButton = view.findViewById(R.id.img_like_btn);
            commentButton = view.findViewById(R.id.img_comment_btn);
            deleteButton = view.findViewById(R.id.delete_button);
            followButton = view.findViewById(R.id.follow_button);
            addToCartButton = view.findViewById(R.id.img_add_to_cart);
        }
    }

    public interface PhotoActionListener {

        void onAction(ActionType type, BaseImage image);

        enum ActionType {
            FOLLOW, VIEW, COMMENT, LIKE, ADD_TO_CART, DELETE
        }
    }
}
