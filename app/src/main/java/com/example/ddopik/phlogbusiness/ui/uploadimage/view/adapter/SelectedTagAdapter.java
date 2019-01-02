package com.example.ddopik.phlogbusiness.ui.uploadimage.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Tag;

import java.util.List;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public class SelectedTagAdapter extends RecyclerView.Adapter<SelectedTagAdapter.TagViewHolder> {
    private List<Tag> tagList;
    public OnSelectedItemClicked onSelectedItemClicked;

    public SelectedTagAdapter(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.view_holder_tag, viewGroup, false);
        return new TagViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder tagViewHolder, int i) {

        tagViewHolder.tagName.setText(tagList.get(i).name);

        if (onSelectedItemClicked != null) {
            tagViewHolder.delete.setOnClickListener((view) -> onSelectedItemClicked.onItemDeleted(tagList.get(i)));
         }

    }

    @Override
    public int getItemCount() {

        return tagList.size();
    }


    class TagViewHolder extends RecyclerView.ViewHolder {
        TextView tagName;
        ImageButton delete;

        TagViewHolder(View view) {
            super(view);
            tagName = view.findViewById(R.id.tag_name);
            delete= view.findViewById(R.id.delete_tag);
        }

    }

    public interface OnSelectedItemClicked {
        void onItemDeleted(Tag tag);
     }
}
