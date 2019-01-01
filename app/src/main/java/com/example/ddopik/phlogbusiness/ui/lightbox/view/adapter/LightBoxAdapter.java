package com.example.ddopik.phlogbusiness.ui.lightbox.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.base.commonmodel.LightBox;

import java.util.List;

public class LightBoxAdapter extends RecyclerView.Adapter<LightBoxAdapter.LightBoxViewHolder> {


    private List<LightBox> lightBoxList;
    private Context context;
    public OnLightBoxClickListener onLightBoxClickListener;


    public LightBoxAdapter(List<LightBox> lightBoxList) {
        this.lightBoxList=lightBoxList;
    }

    @NonNull
    @Override
    public LightBoxViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        this.context = viewGroup.getContext();
        View view=layoutInflater.inflate(R.layout.view_holder_light_box,viewGroup,false);
        return new LightBoxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LightBoxViewHolder lightBoxViewHolder, int i) {

        lightBoxViewHolder.lightBoxName.setText(lightBoxList.get(i).name);
        lightBoxViewHolder.lightBoxPhotosCount.setText(new StringBuilder().append(lightBoxList.get(i).photosCount).append(" ").append(context.getResources().getString(R.string.photos)).toString());

        LightBoxSliderAdapter lightBoxSliderAdapter = new LightBoxSliderAdapter(lightBoxList.get(i).photos);
        lightBoxViewHolder.lightBoxSliderRv.setAdapter(lightBoxSliderAdapter);
        if (onLightBoxClickListener != null) {

            lightBoxViewHolder.lightBoxContainer.setOnClickListener(v->{
                onLightBoxClickListener.onLightBoxClick(lightBoxList.get(i));
            });

            lightBoxViewHolder.lightBoxSliderRv.setOnClickListener(v->{
                onLightBoxClickListener.onSliderContainerClicked(lightBoxList.get(i));

            });



        }

        lightBoxViewHolder.LightBoxMenuBrn.setOnClickListener(v->{



            PopupMenu popupMenu = new PopupMenu(context, lightBoxViewHolder.LightBoxMenuBrn);
            popupMenu.setOnMenuItemClickListener(menuItem -> {

                switch (menuItem.getItemId()){

                    case R.id.delete_light_box:{
                        onLightBoxClickListener.onDeleteLightBoxClicked(lightBoxList.get(i));
                        break;
                    }
                }

                return false;
            });

            popupMenu.setGravity(Gravity.END);
            popupMenu.inflate(R.menu.menu_light_box);
            popupMenu.show();
        });

    }

    @Override
    public int getItemCount() {
        return lightBoxList.size();
    }

    public interface OnLightBoxClickListener {
        void onLightBoxClick(LightBox lightBox);

        void onSliderContainerClicked(LightBox lightBox);

        void onDeleteLightBoxClicked(LightBox lightBox);
    }

    class LightBoxViewHolder extends RecyclerView.ViewHolder {
        CustomTextView lightBoxName, lightBoxPhotosCount;
        CustomRecyclerView lightBoxSliderRv;
        LinearLayout lightBoxContainer;
        ImageButton LightBoxMenuBrn;

        LightBoxViewHolder(View view) {
            super(view);
            lightBoxName = view.findViewById(R.id.lightbox_name);
            lightBoxPhotosCount = view.findViewById(R.id.lightbox_photos_count);
            lightBoxSliderRv = view.findViewById(R.id.lightbox_slider_rv);
            lightBoxContainer = view.findViewById(R.id.lightbox_container);
            LightBoxMenuBrn = view.findViewById(R.id.add_lightbox_menu_btn);

        }


    }
}
