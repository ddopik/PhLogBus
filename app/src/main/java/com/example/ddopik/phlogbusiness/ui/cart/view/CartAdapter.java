package com.example.ddopik.phlogbusiness.ui.cart.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final List<Object> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_card_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    public void setList(List<Object> list) {
        items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView price, byWhow, likes, rate;
        ImageButton remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cart_item_image);
            price = itemView.findViewById(R.id.cart_item_price);
            byWhow = itemView.findViewById(R.id.cart_item_by_who);
            likes = itemView.findViewById(R.id.cart_item_likes);
            rate = itemView.findViewById(R.id.cart_item_rate);
            remove = itemView.findViewById(R.id.cart_items_remove_button);
        }
    }

    public interface ActionListener {
        void onAction(Type type, Object o);

        enum Type {
            REMOVE
        }
    }
}
