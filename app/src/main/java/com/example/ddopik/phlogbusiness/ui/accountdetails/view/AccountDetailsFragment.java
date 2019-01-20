package com.example.ddopik.phlogbusiness.ui.accountdetails.view;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.ui.accountdetails.model.AccountDetailsModel;
import com.example.ddopik.phlogbusiness.ui.accountdetails.presenter.AccountDetailsPresenter;
import com.example.ddopik.phlogbusiness.ui.accountdetails.presenter.AccountDetailsPresenterImpl;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.ddopik.phlogbusiness.utiltes.Constants.REQUEST_CODE_CAMERA;
import static com.example.ddopik.phlogbusiness.utiltes.Constants.REQUEST_CODE_GALLERY;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountDetailsFragment extends BaseFragment implements AccountDetailsView {

    private Business business;

    private AccountDetailsModel model;

    private AccountDetailsPresenter presenter;

    public static final String TAG = AccountDetailsFragment.class.getSimpleName();

    private View mainView;
    private ImageView profileImage, coverImage;
    private EditText firstNameET, lastNameET, phoneET, emailET, passwordET;
    private ProgressBar loading;
    private Button saveButton;

    public AccountDetailsFragment() {
        // Required empty public constructor
    }

    public static AccountDetailsFragment getInstance(Business messageToFragment) {
        AccountDetailsFragment fragment = new AccountDetailsFragment();
        fragment.business = messageToFragment;
        fragment.model = new AccountDetailsModel(messageToFragment);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return mainView = inflater.inflate(R.layout.fragment_account_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setListeners();
        initPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (model.getFirstName() != null)
            firstNameET.setText(model.getFirstName());
        if (model.getLastName() != null)
            lastNameET.setText(model.getLastName());
        if (model.getPhone() != null)
            phoneET.setText(model.getPhone());
        if (model.getEmail() != null)
            emailET.setText(model.getEmail());
        Glide.with(this)
                .load(model.getCoverImage())
                .into(coverImage);
        Glide.with(this)
                .load(model.getProfileImage())
                .apply(RequestOptions.circleCropTransform())
                .into(profileImage);
    }

    @Override
    public void onDestroy() {
        presenter.terminate();
        super.onDestroy();
    }

    @Override
    protected void initPresenter() {
        presenter = new AccountDetailsPresenterImpl();
        presenter.setView(this);
    }

    @Override
    protected void initViews() {
        profileImage = mainView.findViewById(R.id.profile_image_image_view);
        coverImage = mainView.findViewById(R.id.cover_image_image_view);
        firstNameET = mainView.findViewById(R.id.name_edit_text);
        lastNameET = mainView.findViewById(R.id.username_edit_text);
        emailET = mainView.findViewById(R.id.email_edit_text);
        phoneET = mainView.findViewById(R.id.phone_edit_text);
        passwordET = mainView.findViewById(R.id.password_edit_text);
        loading = mainView.findViewById(R.id.loading);
        loading.setVisibility(View.GONE);
        saveButton = mainView.findViewById(R.id.save_button);
    }

    private void setListeners() {
        profileImage.setOnClickListener(v -> {
            whichImage = WhichImage.PROFILE;
            openPickerDialog();
        });
        coverImage.setOnClickListener(v -> {
            whichImage = WhichImage.COVER;
            openPickerDialog();
        });
        saveButton.setOnClickListener(v -> {
            saveButton.setEnabled(false);
            loading.setVisibility(View.VISIBLE);
            model.setFirstName(firstNameET.getText().toString());
            model.setLastName(lastNameET.getText().toString());
            model.setEmail(emailET.getText().toString());
            model.setPassword(passwordET.getText().toString());
            AccountDetailsPresenter.ValidationResult result = presenter.validate(model);
            if (result.valid) {
                presenter.saveProfile(getContext(), model);
            } else {
                if (result.errorMessage != 0)
                    Toast.makeText(getContext(), result.errorMessage, Toast.LENGTH_LONG).show();
            }
        });
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
                    model.setCoverImage(ImagePicker.getFirstImageOrNull(data).getPath());
                    model.setCoverImageChanged(true);
                    GlideApp.with(this)
                            .load(model.getCoverImage())
                            .into(coverImage);
                    break;
                case PROFILE:
                    model.setProfileImage(ImagePicker.getFirstImageOrNull(data).getPath());
                    model.setProfileImageChanged(true);
                    GlideApp.with(this)
                            .load(model.getProfileImage())
                            .apply(RequestOptions.circleCropTransform())
                            .into(profileImage);
                    break;
            }
        }
    }

    @Override
    public void setLoading(boolean b) {
        if (b)
            loading.setVisibility(View.VISIBLE);
        else loading.setVisibility(View.GONE);
    }

    @Override
    public void updateSuccess(boolean b) {
        saveButton.setEnabled(true);
        if (b)
            Toast.makeText(getContext(), R.string.profile_update_success, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getContext(), R.string.profile_update_error, Toast.LENGTH_LONG).show();
    }

    private WhichImage whichImage;

    private enum WhichImage {
        PROFILE, COVER
    }
}
