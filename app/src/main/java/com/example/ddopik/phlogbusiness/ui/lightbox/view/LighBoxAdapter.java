package com.example.ddopik.phlogbusiness.ui.lightbox.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.ui.lightbox.model.LightBox;

import java.util.List;

public class LighBoxAdapter extends RecyclerView.Adapter<LighBoxAdapter.LightBoxViewHolder> {

    private List<LightBox> lightBoxList;
    private Context context;


    public LighBoxAdapter (List<LightBox> lightBoxList){
        this.lightBoxList=lightBoxList;
    }

    @NonNull
    @Override
    public LightBoxViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());

        View view=layoutInflater.inflate(R.layout.view_holder_light_box,viewGroup,false);
        return new LightBoxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LightBoxViewHolder lightBoxViewHolder, int i) {

        lightBoxViewHolder.lightBoxName.setText(lightBoxList.get(i).name);
        lightBoxViewHolder.lightBoxName.setText(new StringBuilder().append(lightBoxList.get(i).photosCount).append(" ").append(context.getResources().getString(R.string.photos)).toString());

    }

    @Override
    public int getItemCount() {
        return lightBoxList.size();
    }

     class LightBoxViewHolder extends RecyclerView.ViewHolder {
        CustomTextView lightBoxName,lighBoxPotosCount;

         LightBoxViewHolder(View view) {
            super(view);
             lightBoxName=view.findViewById(R.id.lightbox_name);
             lightBoxName=view.findViewById(R.id.lightbox_photos_count);

        }

    }
}
