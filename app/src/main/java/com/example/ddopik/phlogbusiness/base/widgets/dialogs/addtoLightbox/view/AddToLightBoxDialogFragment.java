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
import com.example.ddopik.phlogbusiness.utiltes.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddToLightBoxDialogFragment extends DialogFragment implements AddToLightBoxDialogFragmentView {

    private View mainDialogView;
    private AddNewLightBoxDialogFragment.OnDialogAdd onDialogAdd;
    public OnLighBoxImageComplete onLighBoxImageComplete;
    private Button addToLightBoxBtn, cancelBtn;
    private CustomRecyclerView addToLightBoxRv;
    private AddToLightBoxAdapter addToLightBoxAdapter;
    private List<LightBox> lightBoxList = new ArrayList<>();
    private ProgressBar addImgToLightBoxProgress;
    private BaseImage selectedImage;

    private AddNewLightBoxDialogPresenter addNewLightBoxDialogPresenter;


    public static AddToLightBoxDialogFragment getInstance(BaseImage image) {
        AddToLightBoxDialogFragment addToLightBoxDialogFragment = new AddToLightBoxDialogFragment();
        addToLightBoxDialogFragment.selectedImage = image;
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
        addImgToLightBoxProgress = mainDialogView.findViewById(R.id.add_img_to_light_box_progress);
        addToLightBoxRv = mainDialogView.findViewById(R.id.add_to_light_box_rv);
        addToLightBoxBtn = mainDialogView.findViewById(R.id.add_light_to_box_dialog_btn);
        cancelBtn = mainDialogView.findViewById(R.id.cancel_add_to_light_box_dialog_btn);
        addToLightBoxAdapter = new AddToLightBoxAdapter(lightBoxList);
        addToLightBoxRv.setAdapter(addToLightBoxAdapter);
    }

    private void initPresenter() {
        addNewLightBoxDialogPresenter = new AddNewLightBoxDialogPresenterImpl(getContext(), this);
    }

    private void initListeners() {
        cancelBtn.setOnClickListener(v -> {
            dismiss();
        });

        //onAddToLightBoxItemClicked
        addToLightBoxAdapter.onAddToLightBoxClicked = (adapterLightBox, position) -> {



            if (!lightBoxList.get(position).isChecked) {
                lightBoxList.get(position).isChecked = true;
            } else {
                lightBoxList.get(position).isChecked = false;
            }
            addToLightBoxAdapter.notifyDataSetChanged();

            if (isLightBoxChecked()) {
                addToLightBoxBtn.setText(getResources().getString(R.string.save));
            } else {
                addToLightBoxBtn.setText(getResources().getString(R.string.add_new_light_box));
            }
        };


        addToLightBoxBtn.setOnClickListener(v -> {



            /**
             * No LightBox preferred try add newOne
             * */
            if (!isLightBoxChecked()) {
                AddNewLightBoxDialogFragment addNewLightBoxDialogFragment = AddNewLightBoxDialogFragment.getInstance();
                addNewLightBoxDialogFragment.setOnDialogAdd(lightBoxName -> {
                    addNewLightBoxDialogPresenter.addLightBox(lightBoxName, "desc");
                    addNewLightBoxDialogFragment.dismiss();
                });
                addNewLightBoxDialogFragment.show(getChildFragmentManager(), AddToLightBoxDialogFragment.class.getSimpleName());
                return;
            }

            for (int i = 0; i < lightBoxList.size(); i++) {
                /**
                 * send if at least on lightBox checked
                 * */
                if (lightBoxList.get(i).isChecked) {
                    addNewLightBoxDialogPresenter.addImagesToLightBox(lightBoxList, selectedImage.id, this);
                    break;
                }


            }


        });

    }

    @Override
    public void viewLightBoxes(List<LightBox> lightBoxList) {
        this.lightBoxList.clear();
        this.lightBoxList.addAll(lightBoxList);
        addToLightBoxAdapter.notifyDataSetChanged();




    }

    @Override
    public void onImageAddedToLightBox(boolean state) {

        if (onLighBoxImageComplete != null) {
            onLighBoxImageComplete.onImageAdded(state);

        }
    }

    @Override
    public void viewLightBoxProgress(Boolean state) {
        if (state) {
            addImgToLightBoxProgress.setVisibility(View.VISIBLE);
        } else {
            addImgToLightBoxProgress.setVisibility(View.GONE);
        }

    }

    @Override
    public void viewMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }


    public interface OnLighBoxImageComplete {
        void onImageAdded(boolean state);
    }

    private boolean isLightBoxChecked() {
 
        for (LightBox lightBox : lightBoxList) {
            if (lightBox.isChecked) {
                return true;
            }
        }
        return false;
    }

}
