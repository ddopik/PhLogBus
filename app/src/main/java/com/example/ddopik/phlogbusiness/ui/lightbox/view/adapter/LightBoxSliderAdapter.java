package com.example.ddopik.phlogbusiness.ui.lightbox.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.commonmodel.ImageObj;

import java.util.List;

public class LightBoxSliderAdapter extends RecyclerView.Adapter<LightBoxSliderAdapter.LightBoxSliderViewHolder> {


    public OnLightBoxImgSliderClick OnLightBoxImgSliderClick;
    private Context context;
    private List<ImageObj> imageObjList;

    public LightBoxSliderAdapter(List<ImageObj> imageObjList) {
        this.imageObjList = imageObjList;
    }

    @NonNull
    @Override
    public LightBoxSliderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new LightBoxSliderViewHolder(layoutInflater.inflate(R.layout.view_holder_light_box_slider, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LightBoxSliderViewHolder lightBoxSliderViewHolder, int i) {

        GlideApp.with(context)
                .load(imageObjList.get(i).url)
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .into(lightBoxSliderViewHolder.lightBoxSliderImg);

        if (OnLightBoxImgSliderClick != null) {
            lightBoxSliderViewHolder.lightBoxSliderImg.setOnClickListener(v -> {
                OnLightBoxImgSliderClick.onImgClick(imageObjList.get(i));
            });
        }

    }

    @Override
    public int getItemCount() {
        return imageObjList.size();
    }

    public interface OnLightBoxImgSliderClick {
        void onImgClick(ImageObj imageObj);
    }

    class LightBoxSliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView lightBoxSliderImg;

        LightBoxSliderViewHolder(View view) {
            super(view);
            lightBoxSliderImg = view.findViewById(R.id.light_box_slider_img);

        }

    }
}
