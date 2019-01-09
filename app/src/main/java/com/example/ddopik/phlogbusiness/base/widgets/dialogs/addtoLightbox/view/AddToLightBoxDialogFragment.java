package com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.LightBox;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.AddNewLightBoxDialogFragment;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.presenter.AddNewLightBoxDialogPresenter;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.presenter.AddNewLightBoxDialogPresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class AddToLightBoxDialogFragment extends DialogFragment implements AddToLightBoxDialogFragmentView {

    private View mainDialogView;
    private AddNewLightBoxDialogFragment.OnDialogAdd onDialogAdd;
    private Button addToLightBoxBtn, cancelBtn;
    private CustomRecyclerView addToLightBoxRv;
    private AddToLightBoxAdapter addToLightBoxAdapter;
    private List<LightBox> lightBoxList=new ArrayList<>();
    private ProgressBar addImgToLightBoxProgress;
    private BaseImage selectedImage;

    private AddNewLightBoxDialogPresenter addNewLightBoxDialogPresenter;



    public static AddToLightBoxDialogFragment getInstance(BaseImage image) {
        AddToLightBoxDialogFragment addToLightBoxDialogFragment = new AddToLightBoxDialogFragment();
        addToLightBoxDialogFragment.selectedImage=image;
        return addToLightBoxDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainDialogView = inflater.inflate(R.layout.fragment_add_img_to_light_box, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        return mainDialogView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        initPresenter();
        initView();
        initListeners();
        addNewLightBoxDialogPresenter.getLightBoxes();
    }

    private void initView() {
        addImgToLightBoxProgress=mainDialogView.findViewById(R.id.add_img_to_light_box_progress);
        addToLightBoxRv = mainDialogView.findViewById(R.id.add_to_light_box_rv);
        addToLightBoxBtn = mainDialogView.findViewById(R.id.add_light_to_box_dialog_btn);
        cancelBtn = mainDialogView.findViewById(R.id.cancel_add_to_light_box_dialog_btn);
        addToLightBoxAdapter = new AddToLightBoxAdapter(lightBoxList);
        addToLightBoxRv.setAdapter(addToLightBoxAdapter);
    }

    private void initPresenter() {
        addNewLightBoxDialogPresenter=new AddNewLightBoxDialogPresenterImpl(getContext(),this);
    }

    private void initListeners() {
        cancelBtn.setOnClickListener(v -> {
            dismiss();
        });

        addToLightBoxAdapter.onAddToLightBoxClicked = (adapterLightBox, position) -> {

            if (!lightBoxList.get(position).isChecked) {
                lightBoxList.get(position).isChecked = true;
            } else {
                lightBoxList.get(position).isChecked = false;
            }
            addToLightBoxAdapter.notifyDataSetChanged();

            if (lightBoxList.size() > 0) {
                addToLightBoxBtn.setText(getResources().getString(R.string.save));
            } else {
                addToLightBoxBtn.setText(getResources().getString(R.string.add_to_light_box));
            }
        };





        addToLightBoxBtn.setOnClickListener(v->{
            addNewLightBoxDialogPresenter.addImagesToLightBox(lightBoxList,selectedImage.id,this);
        });

    }

    @Override
    public void viewLightBoxes(List<LightBox> lightBoxList) {
        this.lightBoxList.addAll(lightBoxList);
        addToLightBoxAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewLightBoxProgress(Boolean state) {
        if (state){
            addImgToLightBoxProgress.setVisibility(View.VISIBLE);
        }else {
            addImgToLightBoxProgress.setVisibility(View.GONE);
        }

    }

    @Override
    public void viewMessage(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }
}
