package com.example.ddopik.phlogbusiness.ui.downloads.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.ui.downloads.model.GroupItem;
import com.example.ddopik.phlogbusiness.ui.downloads.view.DownloadsFragment;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<GroupItem> items;
    private final DownloadsFragment.ChildFragmentActionListener actionListener;

    public GroupAdapter(List<GroupItem> items, DownloadsFragment.ChildFragmentActionListener actionListener) {
        this.items = items;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (type) {
            case LIST:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.downloads_list_group_layout, viewGroup, false);
                return new ListViewHolder(view);
            case GRID:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.downloads_grid_group_layout, viewGroup, false);
                return new GridViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        GroupItem item = items.get(i);
        ItemAdapter adapter = new ItemAdapter(item.getPhotos(), actionListener);
        switch (type) {
            case LIST:
                ListViewHolder _holder = (ListViewHolder) viewHolder;
                _holder.date.setText(item.getHummanDate());
                adapter.setType(ItemAdapter.Type.LIST);
                _holder.recyclerView.setAdapter(adapter);
                break;
            case GRID:
                GridViewHolder __holder = (GridViewHolder) viewHolder;
                __holder.date.setText(item.getHummanDate());
                adapter.setType(ItemAdapter.Type.GRID);
                __holder.recyclerView.setAdapter(adapter);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        RecyclerView recyclerView;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_text_view);
            recyclerView = itemView.findViewById(R.id.recycler_view);
        }
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        RecyclerView recyclerView;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_text_view);
            recyclerView = itemView.findViewById(R.id.recycler_view);
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
