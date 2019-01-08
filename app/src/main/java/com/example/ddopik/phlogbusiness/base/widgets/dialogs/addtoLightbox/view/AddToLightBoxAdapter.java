package com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.LightBox;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;

import java.util.List;

public class AddToLightBoxAdapter extends RecyclerView.Adapter<AddToLightBoxAdapter.AddToLightBoxViewHolder> {

    private Context context;
    private OnAddToLightBoxClicked onAddToLightBoxClicked;
    private List<LightBox> lightBoxList;


    public AddToLightBoxAdapter(List<LightBox> lightBoxList, OnAddToLightBoxClicked onAddToLightBoxClicked) {
        this.lightBoxList = lightBoxList;
        this.onAddToLightBoxClicked = onAddToLightBoxClicked;
    }

    @NonNull
    @Override
    public AddToLightBoxViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();

        return new AddToLightBoxViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_add_to_light_box, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddToLightBoxViewHolder addToLightBoxViewHolder, int i) {

        addToLightBoxViewHolder.lightBoxName.setText(lightBoxList.get(i).name);
        addToLightBoxViewHolder.lightBoxPhotosCount.setText(new StringBuilder().append(lightBoxList.get(i).photos.size()).append(" ").append(context.getResources().getString(R.string.photos)).toString());

        if (onAddToLightBoxClicked != null) {

            addToLightBoxViewHolder.addToLightBoxContainer.setOnClickListener(v -> {
                onAddToLightBoxClicked.onAddToLightBoxItemClicked(lightBoxList.get(i), addToLightBoxViewHolder.addImgToLighrBoxRadioBtn.isChecked());
            });

            addToLightBoxViewHolder.addImgToLighrBoxRadioBtn.setOnClickListener(v -> {
                onAddToLightBoxClicked.onAddToLightBoxItemClicked(lightBoxList.get(i), addToLightBoxViewHolder.addImgToLighrBoxRadioBtn.isChecked());
            });
        }

    }

    @Override
    public int getItemCount() {
        return lightBoxList.size();
    }

    class AddToLightBoxViewHolder extends RecyclerView.ViewHolder {

        CustomTextView lightBoxName, lightBoxPhotosCount;
        RadioButton addImgToLighrBoxRadioBtn;
        FrameLayout addToLightBoxContainer;

        AddToLightBoxViewHolder(View view) {
            super(view);
            lightBoxName = view.findViewById(R.id.light_box_name);
            lightBoxPhotosCount = view.findViewById(R.id.light_box_photos_count);
            addToLightBoxContainer = view.findViewById(R.id.add_to_light_box_container);
            addImgToLighrBoxRadioBtn = view.findViewById(R.id.add_img_to_light_box_rb);
        }
    }


}
