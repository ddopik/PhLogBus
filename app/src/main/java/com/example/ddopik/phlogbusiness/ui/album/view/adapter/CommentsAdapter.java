package com.example.ddopik.phlogbusiness.ui.album.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.ui.album.model.UserComment;


import java.util.List;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private Context context;
    private List<UserComment> commentList;

    public CommentsAdapter(List<UserComment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new CommentViewHolder(layoutInflater.inflate(R.layout.view_holder_comment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        public CommentViewHolder(View view) {
            super(view);
        }

    }
}
