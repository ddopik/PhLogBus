package com.example.ddopik.phlogbusiness.ui.setupbrand.fragment.stoptwo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseDialogFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectFromStringListDialogFragment<T extends SelectFromStringListDialogFragment.Model> extends BaseDialogFragment {

    public static final String TAG = SelectFromStringListDialogFragment.class.getSimpleName();

    private List<T> list;
    private RecyclerView recyclerView;

    private Consumer<Model> consumer;

    public SelectFromStringListDialogFragment() {
        // Required empty public constructor
    }

    public static <T> SelectFromStringListDialogFragment newInstance(List<T> list) {
        SelectFromStringListDialogFragment fragment = new SelectFromStringListDialogFragment<>();
        fragment.list = list;
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(true);
    }

    public void setConsumer(Consumer<Model> consumer) {
        this.consumer = consumer;
    }

    @Override
    protected void setViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new Adapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void setListeners() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_industry_dialog, container, false);
    }

    public interface Model {
        String getStringForShowingInList();
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.item_simple_model_layout, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Model m = list.get(i);
            viewHolder.text.setText(m.getStringForShowingInList());
            viewHolder.itemView.setOnClickListener(v -> {
                if (consumer != null)
                    consumer.accept(m);
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView text;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.text);
            }
        }
    }
}
