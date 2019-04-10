package com.example.ddopik.phlogbusiness.ui.commentimage.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ddopik.phlogbusiness.R;

/**
 * Created by abdalla-maged on 10/4/18.
 */
public class ImageRateDialogFragment extends DialogFragment {

    private View mainView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.dialog_fragment_rate, container, false);


        return mainView;
    }

    void initView() {

    }

    void initListeneres() {

    }


}
