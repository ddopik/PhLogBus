package com.example.ddopik.phlogbusiness.ui.downloads.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.ui.downloads.view.DownloadsFragment;

import java.util.List;

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
                return new ListViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        BaseImage image = list.get(i);
        switch (type) {
            case LIST:
                break;
            case GRID:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        RecyclerView recyclerView;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
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
