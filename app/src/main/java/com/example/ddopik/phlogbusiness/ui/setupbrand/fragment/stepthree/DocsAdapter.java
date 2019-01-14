package com.example.ddopik.phlogbusiness.ui.setupbrand.fragment.stepthree;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.AdapterModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class DocsAdapter<T extends AdapterModel> extends RecyclerView.Adapter<DocsAdapter.ViewHolder> {

    private final List<T> list = new ArrayList<>();
    private final ActionListener actionListener;

    public DocsAdapter(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doc_card_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        AdapterModel model = list.get(i);
        Context context = holder.itemView.getContext();
        if (model.getDisplayName() != null)
            holder.title.setText(model.getDisplayName());
        else holder.title.setText("");
        if (model.getImageFallback() != null)
            Glide.with(context)
            .load(model.getImageFallback())
            .into(holder.image);
        else if (model.getImage() != null)
            Glide.with(context)
                    .load(model.getImage())
                    .into(holder.image);
        holder.upload.setVisibility(View.GONE);
        holder.check.setVisibility(View.GONE);
        Consumer<String> consumer = s -> {
            Glide.with(context)
                    .load(s)
                    .into(holder.image);
            holder.upload.setVisibility(View.VISIBLE);
        };
        holder.itemView.setOnClickListener(v -> {
            actionListener.accept(ActionListener.Type.SELECT, consumer);
        });
        holder.upload.setOnClickListener(v -> {
            actionListener.accept(ActionListener.Type.UPLOAD, model);
        });
        if (model.progress > 0)
            holder.upload.setVisibility(View.GONE);
        holder.progress.setProgress(model.progress);
}

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<T> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setProgress(AdapterModel model) {
        String id = model.getId();
        for (AdapterModel m : list) {
            if (m.getId().equals(id)) {
                m.progress = model.progress;
                int index = list.indexOf(m);
                if (index != -1)
                    notifyItemChanged(index);
                break;
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image, check;
        ImageButton upload;
        ProgressBar progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            check = itemView.findViewById(R.id.check);
            upload = itemView.findViewById(R.id.upload);
            progress = itemView.findViewById(R.id.progress);
        }
    }

    public interface ActionListener {

        void accept(Type type, Object... objects);

        enum Type {
            SELECT, UPLOAD
        }
    }
}
