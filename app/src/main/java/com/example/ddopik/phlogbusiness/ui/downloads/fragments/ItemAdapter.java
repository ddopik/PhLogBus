package com.example.ddopik.phlogbusiness.ui.downloads.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.ui.downloads.view.DownloadsFragment;

import java.util.List;

import static com.example.ddopik.phlogbusiness.ui.downloads.view.DownloadsFragment.ChildFragmentActionListener.ActionType.DOWnLOAD;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<BaseImage> list;
    private final DownloadsFragment.ChildFragmentActionListener actionListener;

    public ItemAdapter(List<BaseImage> list, DownloadsFragment.ChildFragmentActionListener actionListener) {
        this.list = list;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (type) {
            case LIST:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.downloads_list_item_layout, viewGroup, false);
                return new ListViewHolder(view);
            case GRID:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.downloads_grid_item_layout, viewGroup, false);
                return new GridViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        BaseImage image = list.get(i);
        Context context = viewHolder.itemView.getContext();
        switch (type) {
            case LIST:
                ListViewHolder holder = (ListViewHolder) viewHolder;
                holder.byWhow.setText(image.photographer.fullName);
                holder.exclusiveIcon.setVisibility(Math.random() * 10 > 5 ? View.VISIBLE : View.GONE);
                holder.likes.setText("" + image.likesCount);
                holder.price.setText(context.getString(R.string.price_value, Math.random() * 100));
                Glide.with(context)
                        .load(image.url)
                        .into(holder.image);
                Glide.with(context)
                        .load(image.photographer.imageProfile)
                        .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_photographer))
                        .into(holder.byWhoImage);
                holder.rating.setRating(image.rate);
                holder.download.setOnClickListener(v -> {
                    actionListener.onAction(DOWnLOAD, image);
                });
                break;
            case GRID:
                GridViewHolder _holder = (GridViewHolder) viewHolder;
                _holder.exclusiveIcon.setVisibility(Math.random() * 10 > 5 ? View.VISIBLE : View.GONE);
                Glide.with(context)
                        .load(image.url)
                        .into(_holder.image);
                _holder.download.setOnClickListener(v -> {
                    actionListener.onAction(DOWnLOAD, image);
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView image, byWhoImage, exclusiveIcon;
        TextView price, byWhow, likes;
        ImageButton remove;
        Button download;
        RatingBar rating;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cart_item_image);
            price = itemView.findViewById(R.id.cart_item_price);
            byWhow = itemView.findViewById(R.id.cart_item_by_who);
            likes = itemView.findViewById(R.id.cart_item_likes);
            remove = itemView.findViewById(R.id.cart_items_remove_button);
            byWhoImage = itemView.findViewById(R.id.by_who_image);
            exclusiveIcon = itemView.findViewById(R.id.exclusive_icon);
            rating = itemView.findViewById(R.id.rating);
            download = itemView.findViewById(R.id.download_button);
        }
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView image, exclusiveIcon;
        Button download;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cart_item_image);
            exclusiveIcon = itemView.findViewById(R.id.exclusive_icon);
            download = itemView.findViewById(R.id.download_button);
        }
    }

    private Type type;

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        LIST, GRID
    }
}
