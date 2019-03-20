package com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.LightBox;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;

import java.util.List;
import java.util.logging.Handler;

public class AddToLightBoxAdapter extends RecyclerView.Adapter<AddToLightBoxAdapter.AddToLightBoxViewHolder> {

    private Context context;
     private List<LightBox> lightBoxList;
    public OnAddToLightBoxClicked onAddToLightBoxClicked;


    public AddToLightBoxAdapter(List<LightBox> lightBoxList) {
        this.lightBoxList = lightBoxList;

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
        addToLightBoxViewHolder.addImgToLightBoxRadioBtn.setChecked(lightBoxList.get(i).isChecked);



        if (onAddToLightBoxClicked != null) {
            //case ALL container pressed
            addToLightBoxViewHolder.addToLightBoxContainer.setOnClickListener(v -> {
                onAddToLightBoxClicked.onAddToLightBoxItemClicked(lightBoxList.get(i),i);
                if(addToLightBoxViewHolder.addImgToLightBoxRadioBtn.isChecked()){
                    addToLightBoxViewHolder.addImgToLightBoxRadioBtn.setChecked(false);
            }else {
                addToLightBoxViewHolder.addImgToLightBoxRadioBtn.setChecked(true);
            }
            });
//            case only RadioBtn pressed
            addToLightBoxViewHolder.addImgToLightBoxRadioBtn.setOnClickListener(v-> {

                if(addToLightBoxViewHolder.addImgToLightBoxRadioBtn.isChecked()){
                    addToLightBoxViewHolder.addImgToLightBoxRadioBtn.setChecked(false);
                }else {
                    addToLightBoxViewHolder.addImgToLightBoxRadioBtn.setChecked(true);
                }

                onAddToLightBoxClicked.onAddToLightBoxItemClicked(lightBoxList.get(i),i);
        });

        }


    }

    @Override
    public int getItemCount() {
        return lightBoxList.size();
    }

    class AddToLightBoxViewHolder extends RecyclerView.ViewHolder {

        CustomTextView lightBoxName, lightBoxPhotosCount;
        RadioButton addImgToLightBoxRadioBtn;
        FrameLayout addToLightBoxContainer;

        AddToLightBoxViewHolder(View view) {
            super(view);
            lightBoxName = view.findViewById(R.id.light_box_name);
            lightBoxPhotosCount = view.findViewById(R.id.light_box_photos_count);
            addToLightBoxContainer = view.findViewById(R.id.add_to_light_box_container);
            addImgToLightBoxRadioBtn = view.findViewById(R.id.add_img_to_light_box_rb);
        }
    }



    public interface OnAddToLightBoxClicked {
        void onAddToLightBoxItemClicked(LightBox lightBox,int position);
    }


}
