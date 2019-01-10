package com.example.ddopik.phlogbusiness.ui.setupbrand.fragment.stepone;


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

import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.SetupBrandModel;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandActivity;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandActivity.SubViewActionConsumer.*;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;

import java.io.File;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.ddopik.phlogbusiness.utiltes.Constants.REQUEST_CODE_CAMERA;
import static com.example.ddopik.phlogbusiness.utiltes.Constants.REQUEST_CODE_GALLERY;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepOneFragment extends BaseFragment {

    private SetupBrandActivity.SubViewActionConsumer consumer;
    private SetupBrandModel model = new SetupBrandModel();

    private View mainView;
    private ImageView thumbnail, thumbnailPlaceHolder, cover, coverPlaceHolder;
    private EditText arabicName, englishName;

    public StepOneFragment() {
        // Required empty public constructor
    }

    public static StepOneFragment newInstance(SetupBrandActivity.SubViewActionConsumer consumer) {
        StepOneFragment fragment = new StepOneFragment();
        fragment.consumer = consumer;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return mainView = inflater.inflate(R.layout.fragment_step_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (model == null)
            return;
        if (model.cover != null)
            GlideApp.with(this)
                    .load(model.cover)
                    .into(cover);
        if (model.thumbnail != null)
            GlideApp.with(this)
                    .load(model.thumbnail)
                    .apply(RequestOptions.circleCropTransform())
                    .into(thumbnail);
        if (model.arabicBrandName != null)
            arabicName.setText(model.arabicBrandName);
        if (model.englishBrandName != null)
            englishName.setText(model.englishBrandName);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        thumbnail = mainView.findViewById(R.id.thumbnail_image_view);
        thumbnailPlaceHolder = mainView.findViewById(R.id.thumbnail_place_holder);
        cover = mainView.findViewById(R.id.cover_image_view);
        coverPlaceHolder = mainView.findViewById(R.id.cover_place_holder);
        arabicName = mainView.findViewById(R.id.arabic_name);
        englishName = mainView.findViewById(R.id.english_name);

        setListeners();
    }

    private void setListeners() {
        thumbnail.setOnClickListener(v -> {
            whichImage = WhichImage.THUMBNAIL;
            openPickerDialog();
        });
        cover.setOnClickListener(v -> {
            whichImage = WhichImage.COVER;
            openPickerDialog();
        });
        TextWatcher arabicTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                model.arabicBrandName = s.toString();
                consumer.accept(new SubViewAction(ActionType.ARABIC_NAME, model.arabicBrandName));
            }
        };
        arabicName.addTextChangedListener(arabicTextWatcher);
        TextWatcher englishTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                model.englishBrandName = s.toString();
                consumer.accept(new SubViewAction(ActionType.ENGLISH_NAME, model.englishBrandName));
            }
        };
        englishName.addTextChangedListener(englishTextWatcher);
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
                case COVER:
                    coverPlaceHolder.setVisibility(View.INVISIBLE);
                    model.cover = ImagePicker.getFirstImageOrNull(data).getPath();
                    GlideApp.with(this)
                            .load(model.cover)
                            .into(cover);
                    consumer.accept(new SubViewAction(ActionType.COVER_PHOTO, model.cover));
                    break;
                case THUMBNAIL:
                    thumbnailPlaceHolder.setVisibility(View.INVISIBLE);
                    model.thumbnail = ImagePicker.getFirstImageOrNull(data).getPath();
                    GlideApp.with(this)
                            .load(model.thumbnail)
                            .apply(RequestOptions.circleCropTransform())
                            .into(thumbnail);
                    consumer.accept(new SubViewAction(ActionType.THUMBNAIL_PHOTO, model.thumbnail));
                    break;
            }
        }
    }

    private WhichImage whichImage;

    private enum WhichImage {
        THUMBNAIL, COVER
    }
}
