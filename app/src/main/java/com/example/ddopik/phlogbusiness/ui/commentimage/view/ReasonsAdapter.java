package com.example.ddopik.phlogbusiness.ui.commentimage.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ReportReason;

import java.util.List;

public class ReasonsAdapter extends RecyclerView.Adapter<ReasonsAdapter.ViewHolder> {

    private final List<ReportReason> reasons;
    private OnReasonClickListener onReasonClickListener;

    public ReasonsAdapter(List<ReportReason> reasons) {
        this.reasons = reasons;
    }

    public void setOnReasonClickListener(OnReasonClickListener onReasonClickListener) {
        this.onReasonClickListener = onReasonClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_holder_reason, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ReportReason reason = reasons.get(i);
        Context context = holder.itemView.getContext();
        if (reason.getSystemName() != null)
            holder.text.setText(reason.getSystemName());
        if (reason.selected)
            holder.text.setTextColor(context.getResources().getColor(R.color.text_input_color));
        else
            holder.text.setTextColor(context.getResources().getColor(R.color.black));
        holder.itemView.setOnClickListener(v -> {
            if (onReasonClickListener != null)
                onReasonClickListener.onReasonClick(reason);
        });
    }

    @Override
    public int getItemCount() {
        return reasons.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(@NonNull View view) {
            super(view);
            text = view.findViewById(R.id.text);
        }
    }

    public interface OnReasonClickListener {
        void onReasonClick(ReportReason reason);
    }
}
