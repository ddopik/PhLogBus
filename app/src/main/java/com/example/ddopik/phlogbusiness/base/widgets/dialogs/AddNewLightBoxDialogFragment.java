package com.example.ddopik.phlogbusiness.base.widgets.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ddopik.phlogbusiness.R;

public class AddNewLightBoxDialogFragment extends DialogFragment {


    public static AddNewLightBoxDialogFragment getInstance(){
        AddNewLightBoxDialogFragment addNewLightBoxDialogFragment=new AddNewLightBoxDialogFragment();
        return addNewLightBoxDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View dialogView=inflater.inflate(R.layout.fragment_dialog_add_light_box, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
         return dialogView;
    }



}