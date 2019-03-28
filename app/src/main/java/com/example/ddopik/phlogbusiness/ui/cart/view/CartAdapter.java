package com.example.ddopik.phlogbusiness.ui.cart.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.ui.cart.model.CartItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final List<BaseImage> items = new ArrayList<>();
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        BaseImage item = items.get(i);
        Context context = holder.itemView.getContext();
        holder.exclusiveCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                holder.exclusiveIcon.setVisibility(View.VISIBLE);
            else
                holder.exclusiveIcon.setVisibility(View.INVISIBLE);
        });
        holder.exclusiveCheck.setChecked(item.exclusive);
        holder.exclusiveCheck.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        actionListener.onAction(ActionListener.Type.EXCLUSIVE, item, success -> {
                            if (success) {
                                item.exclusive = !item.exclusive;
                                holder.exclusiveCheck.setChecked(item.exclusive);
                                if (item.exclusive)
                                    holder.price.setText(context.getString(R.string.price_value, item.priceExclusive));
                                else
                                    holder.price.setText(context.getString(R.string.price_value, item.price));
                            }
                        });
                        return false;
                    default:
                        return true;
                }
            }
        });
        holder.byWhow.setText(item.photographer.fullName);
        holder.likes.setText("" + item.likesCount);
//        holder.price.setText(item.);
        if (item.url != null)
            Glide.with(context)
                    .load(item.url)
                    .into(holder.image);
        if (item.photographer.imageProfile != null)
            Glide.with(context)
                    .load(item.photographer.imageProfile)
                    .apply(RequestOptions.circleCropTransform().error(R.drawable.ic_photographer))
                    .into(holder.byWhoImage);
        if (item.exclusive) {
            holder.price.setText(context.getString(R.string.price_value, item.priceExclusive));
            holder.exclusiveIcon.setVisibility(View.VISIBLE);
        } else {
            holder.price.setText(context.getString(R.string.price_value, item.price));
            holder.exclusiveIcon.setVisibility(View.INVISIBLE);
        }
        holder.remove.setOnClickListener(v -> {
            actionListener.onAction(ActionListener.Type.REMOVE, item, success -> {
                if (success) {
                    items.remove(item);
                    notifyItemRemoved(i);
                }
            });
        });
        holder.rating.setRating(item.rate);
        holder.itemView.setOnClickListener(v -> {
            actionListener.onAction(ActionListener.Type.VIEW, item, null);
        });
        if (!item.canPurchase) {
            holder.blocker.setVisibility(View.VISIBLE);
            holder.canBuyReason.setVisibility(View.VISIBLE);
            holder.canBuyReason.setText(item.canPurchaseReason);
        } else {
            holder.blocker.setVisibility(View.GONE);
            holder.canBuyReason.setVisibility(View.GONE);
        }
    }

    public void setList(List<BaseImage> list) {
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
        TextView price, byWhow, likes, canBuyReason;
        View blocker;
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
            blocker = itemView.findViewById(R.id.blocker);
            canBuyReason = itemView.findViewById(R.id.can_buy_reason);
        }
    }

    public interface ActionListener {
        void onAction(Type type, BaseImage o, Consumer<Boolean> booleanConsumer);

        enum Type {
            REMOVE, VIEW, EXCLUSIVE
        }
    }
}
