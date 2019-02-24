package com.example.ddopik.phlogbusiness.ui.dialog.popup.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseDialogFragment;
import com.example.ddopik.phlogbusiness.fgm.model.FirebaseNotificationData;

public class PopupDialogFragment extends BaseDialogFragment {

    private ImageView image;
    private TextView text;
    private Button close;

    private FirebaseNotificationData data;
    private Action navigation;

    public static PopupDialogFragment newInstance(FirebaseNotificationData data, Action navigation) {
        PopupDialogFragment fragment = new PopupDialogFragment();
        fragment.data = data;
        fragment.navigation = navigation;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cancelable = true;
        return inflater.inflate(R.layout.fragment_popup_dialog, container, false);
    }

    @Override
    protected void setViews(View view) {
        image = view.findViewById(R.id.image);
        text = view.findViewById(R.id.text);
        close = view.findViewById(R.id.close);
        Glide.with(this)
                .load(data.notification.popupImage)
                .into(image);
        if (data.notification.message != null)
            text.setText(data.notification.message);
    }

    @Override
    protected void setListeners() {
//        image.setOnClickListener(v -> {
//            navigation.run();
//            dismiss();
//        });
        close.setOnClickListener(v -> {
            dismiss();
        });
    }
}
