package com.example.ddopik.phlogbusiness.ui.commentimage.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import com.example.ddopik.phlogbusiness.R;

/**
 * Created by abdalla-maged on 10/4/18.
 */
public class ImageRateDialogFragment extends DialogFragment {

    private View mainView;
    private RatingBar imageRateBar;
    private Button submitRateBtn, cancelRateBtn;
    private OnImageRateListener onImageRateClick;
    private float currentRate;


    public static ImageRateDialogFragment getInstance(OnImageRateListener onImageRateClick) {
        ImageRateDialogFragment imageRateDialogFragment = new ImageRateDialogFragment();
        imageRateDialogFragment.onImageRateClick = onImageRateClick;
        return imageRateDialogFragment;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.dialog_fragment_rate, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListeners();
    }

    void initView() {
        imageRateBar = mainView.findViewById(R.id.image_rate);
        submitRateBtn = mainView.findViewById(R.id.rate_img_btn);
        cancelRateBtn = mainView.findViewById(R.id.cancel_add_to_light_box_dialog_btn);
    }

    void initListeners() {
        imageRateBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            currentRate = rating;

        });
        submitRateBtn.setOnClickListener(v -> {
            onImageRateClick.onImageRateSelected(currentRate);
            dismiss();
        });

        cancelRateBtn.setOnClickListener(v -> {
            currentRate = 0;
            dismiss();
        });
    }

    public interface OnImageRateListener {
        void onImageRateSelected(Float rate);
    }


}
