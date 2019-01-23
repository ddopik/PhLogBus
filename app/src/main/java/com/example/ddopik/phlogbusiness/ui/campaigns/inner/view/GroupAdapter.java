package com.example.ddopik.phlogbusiness.ui.campaigns.inner.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.model.DataItem;
import com.example.ddopik.phlogbusiness.ui.downloads.fragments.ItemAdapter;
import com.example.ddopik.phlogbusiness.ui.downloads.view.DownloadsFragment;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<DataItem> items;
    private final DownloadsFragment.ChildFragmentActionListener actionListener;
    private CampaignInnerPhotosAdapter.PhotoAction photoAction;

    public void setPhotoAction(CampaignInnerPhotosAdapter.PhotoAction photoAction) {
        this.photoAction = photoAction;
    }

    public GroupAdapter(List<DataItem> items, DownloadsFragment.ChildFragmentActionListener actionListener) {
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
        DataItem item = items.get(i);
        Context context = viewHolder.itemView.getContext();
        switch (type) {
            case LIST:
                break;
            case GRID:
                GridViewHolder __holder = (GridViewHolder) viewHolder;
                __holder.date.setText(item.getHummanDate());
                CampaignInnerPhotosAdapter adapter = new CampaignInnerPhotosAdapter(context, item.getPhotos());
                adapter.photoAction = photoAction;
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
