package com.example.ddopik.phlogbusiness.ui.setupbrand.fragment.stepthree;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.SetupBrandModel;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandActivity;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.ddopik.phlogbusiness.utiltes.Constants.REQUEST_CODE_CAMERA;
import static com.example.ddopik.phlogbusiness.utiltes.Constants.REQUEST_CODE_GALLERY;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepThreeFragment extends BaseFragment {

    private SetupBrandActivity.SubViewActionConsumer consumer;
    private SetupBrandModel model = new SetupBrandModel();

    private View mainView;
    private View commercialView, taxesView;
    private ImageView commercialImage, commerialCheckImage, taxesImage, taxesCheckImage;
    private TextView commercialTitleTV, taxesTitleTV;
    private EditText descET;

    public StepThreeFragment() {
        // Required empty public constructor
    }


    public static StepThreeFragment newInstance(SetupBrandActivity.SubViewActionConsumer consumer) {
        StepThreeFragment fragment = new StepThreeFragment();
        fragment.consumer = consumer;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return mainView = inflater.inflate(R.layout.fragment_step_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (model.commercialRecord != null)
            setViewsAsChecked(WhichImage.COMMERCIAL_RECORD);
        if (model.taxesRecord != null)
            setViewsAsChecked(WhichImage.TAXES_RECORD);

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        commercialView = mainView.findViewById(R.id.commercial_record_view);
        commercialTitleTV = mainView.findViewById(R.id.commercial_record_title);
        commercialImage = mainView.findViewById(R.id.commercial_record_image);
        commerialCheckImage = mainView.findViewById(R.id.commercial_record_check_image);

        taxesView = mainView.findViewById(R.id.taxes_record_view);
        taxesTitleTV = mainView.findViewById(R.id.taxes_record_title);
        taxesImage = mainView.findViewById(R.id.taxes_record_image);
        taxesCheckImage = mainView.findViewById(R.id.taxes_record_check_image);

        descET = mainView.findViewById(R.id.desc_edit_text);
    }

    private void initListeners() {
        commercialView.setOnClickListener(v -> {
            whichImage = WhichImage.COMMERCIAL_RECORD;
            openPickerDialog();
        });
        taxesView.setOnClickListener(v -> {
            whichImage = WhichImage.TAXES_RECORD;
            openPickerDialog();
        });
        TextWatcher descWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                model.desc = descET.getText().toString();
                consumer.accept(new SetupBrandActivity.SubViewActionConsumer.SubViewAction(SetupBrandActivity.SubViewActionConsumer.ActionType.DESC, model.desc));
            }
        };
        descET.addTextChangedListener(descWatcher);
    }

    private void openPickerDialog() {
        CharSequence photoChooserOptions[] = new CharSequence[]{getResources().getString(R.string.general_photo_chooser_camera), getResources().getString(R.string.general_photo_chooser_gallery)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getResources().getString(R.string.general_photo_chooser_title));
        builder.setItems(photoChooserOptions, (dialog, option) -> {
            if (option == 0) {
                RequestCameraPermutations();
            } else if (option == 1) {
                requestGalleryPermutations();
            }
        }).show();
    }

    @AfterPermissionGranted(REQUEST_CODE_CAMERA)
    private void RequestCameraPermutations() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {

            ImagePicker.cameraOnly().start(this);


        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    REQUEST_CODE_CAMERA, perms);
        }

    }

    @AfterPermissionGranted(REQUEST_CODE_GALLERY)
    private void requestGalleryPermutations() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            ImagePicker.create(this)
                    .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                    .single() // single mode
                    .showCamera(false)
                    .theme(R.style.AppTheme)
                    .start();
        }
        // Already have permission

        else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    REQUEST_CODE_GALLERY, perms);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            switch (whichImage) {
                case COMMERCIAL_RECORD:
                    model.commercialRecord = ImagePicker.getFirstImageOrNull(data).getPath();
                    setViewsAsChecked(whichImage);
                    consumer.accept(new SetupBrandActivity.SubViewActionConsumer.SubViewAction(SetupBrandActivity.SubViewActionConsumer.ActionType.COMMERCIAL_RECORD, model.commercialRecord));
                    break;
                case TAXES_RECORD:
                    model.taxesRecord = ImagePicker.getFirstImageOrNull(data).getPath();
                    setViewsAsChecked(whichImage);
                    consumer.accept(new SetupBrandActivity.SubViewActionConsumer.SubViewAction(SetupBrandActivity.SubViewActionConsumer.ActionType.TAXES_RECORD, model.taxesRecord));
                    break;
            }
        }
    }

    private void setViewsAsChecked(WhichImage whichImage) {
        switch (whichImage) {
            case COMMERCIAL_RECORD:
                GlideApp.with(this)
                        .load(model.commercialRecord)
                        .into(commercialImage);
                commercialTitleTV.setTextColor(getResources().getColor(R.color.black));
                commerialCheckImage.setBackgroundResource(R.drawable.ic_check_active);
                break;
            case TAXES_RECORD:
                GlideApp.with(this)
                        .load(model.taxesRecord)
                        .into(taxesImage);
                taxesTitleTV.setTextColor(getResources().getColor(R.color.black));
                taxesCheckImage.setBackgroundResource(R.drawable.ic_check_active);
                break;
        }
    }

    private WhichImage whichImage;

    enum WhichImage {
        COMMERCIAL_RECORD, TAXES_RECORD
    }
}
