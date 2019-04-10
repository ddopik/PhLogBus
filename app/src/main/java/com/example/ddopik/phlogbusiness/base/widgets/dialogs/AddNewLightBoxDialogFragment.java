package com.example.ddopik.phlogbusiness.base.widgets.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;

import java.util.Objects;

public class AddNewLightBoxDialogFragment extends DialogFragment {

    private View mainDialogView;
    private OnDialogAdd onDialogAdd;
    private EditText lightBoxName;
    private Button addBtn, cancelBtn;


    public static AddNewLightBoxDialogFragment getInstance() {
        AddNewLightBoxDialogFragment addNewLightBoxDialogFragment=new AddNewLightBoxDialogFragment();

        return addNewLightBoxDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainDialogView = inflater.inflate(R.layout.fragment_dialog_add_light_box, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        return mainDialogView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addBtn = mainDialogView.findViewById(R.id.add_light_box_dialog_btn);
        cancelBtn = mainDialogView.findViewById(R.id.cancel_add_light_box_dialog_btn);
        lightBoxName = mainDialogView.findViewById(R.id.light_box_name);
        addBtn.setOnClickListener(v -> {
            if (onDialogAdd != null) {
                onDialogAdd.onDialogAddBtn(lightBoxName.getText().toString());
            }
        });

        cancelBtn.setOnClickListener(v -> {
            dismiss();
        });

    }



    public void setOnDialogAdd(OnDialogAdd onDialogAdd) {
        this.onDialogAdd = onDialogAdd;
    }

    public interface OnDialogAdd {
        void onDialogAddBtn(String lightBoxName);

    }




}