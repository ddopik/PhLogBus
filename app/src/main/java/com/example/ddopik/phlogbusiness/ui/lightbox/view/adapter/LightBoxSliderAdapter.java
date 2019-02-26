package com.example.ddopik.phlogbusiness.ui.lightbox.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.LightBox;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;

import java.util.List;

public class LightBoxSliderAdapter extends RecyclerView.Adapter<LightBoxSliderAdapter.LightBoxSliderViewHolder> {


    public OnLightBoxImgSliderClick OnLightBoxImgSliderClick;
    private Context context;
    private List<BaseImage> baseImageList;

    public LightBoxSliderAdapter(List<BaseImage> baseImageList) {
        this.baseImageList = baseImageList;
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
                .load(baseImageList.get(i).url)
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .into(lightBoxSliderViewHolder.lightBoxSliderImg);

        if (OnLightBoxImgSliderClick != null) {
            lightBoxSliderViewHolder.lightBoxSliderImg.setOnClickListener(v -> {
                OnLightBoxImgSliderClick.onImgClick(baseImageList.get(i));
            });
        }

    }

    @Override
    public int getItemCount() {
        return baseImageList.size();
    }

    public interface OnLightBoxImgSliderClick {
        void onImgClick(BaseImage baseImage);
    }

    class LightBoxSliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView lightBoxSliderImg;

        LightBoxSliderViewHolder(View view) {
            super(view);
            lightBoxSliderImg = view.findViewById(R.id.light_box_slider_img);

        }

    }
}
