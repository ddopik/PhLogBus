package com.example.ddopik.phlogbusiness.ui.cart.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.ui.cart.model.CartItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final List<CartItem> items = new ArrayList<>();
    private final ActionListener actionListener;

    public CartAdapter(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_card_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        CartItem item = items.get(i);
        Context context = holder.itemView.getContext();
        holder.exclusiveIcon.setVisibility(View.INVISIBLE);
        holder.exclusiveCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                holder.exclusiveIcon.setVisibility(View.VISIBLE);
            else
                holder.exclusiveIcon.setVisibility(View.INVISIBLE);
        });
        holder.byWhow.setText(item.getPhotographer().userName);
        holder.likes.setText("" + item.getLikesCount());
//        holder.price.setText(item.);
        if (item.getUrl() != null)
            Glide.with(context)
                    .load(item.getUrl())
                    .into(holder.image);
        if (item.getPhotographer().imageProfile != null)
            Glide.with(context)
                    .load(item.getPhotographer().imageProfile)
                    .apply(RequestOptions.circleCropTransform().error(R.drawable.ic_photographer))
                    .into(holder.byWhoImage);
        holder.price.setText(context.getString(R.string.price_value, 3.4));
        holder.remove.setOnClickListener(v -> {
            actionListener.onAction(ActionListener.Type.REMOVE, item, success -> {
                if (success) {
                    items.remove(item);
                    notifyItemRemoved(i);
                }
            });
        });
        holder.rating.setRating(item.getRate());
    }

    public void setList(List<CartItem> list) {
        items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, byWhoImage, exclusiveIcon;
        TextView price, byWhow, likes;
        ImageButton remove;
        CheckBox exclusiveCheck;
        RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cart_item_image);
            price = itemView.findViewById(R.id.cart_item_price);
            byWhow = itemView.findViewById(R.id.cart_item_by_who);
            likes = itemView.findViewById(R.id.cart_item_likes);
            remove = itemView.findViewById(R.id.cart_items_remove_button);
            byWhoImage = itemView.findViewById(R.id.by_who_image);
            exclusiveIcon = itemView.findViewById(R.id.exclusive_icon);
            exclusiveCheck = itemView.findViewById(R.id.exclusive_check_box);
            rating = itemView.findViewById(R.id.rating);
        }
    }

    public interface ActionListener {
        void onAction(Type type, CartItem o, Consumer<Boolean> booleanConsumer);

        enum Type {
            REMOVE
        }
    }
}
