package com.example.ddopik.phlogbusiness.ui.signup.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.ui.MainActivity;
import com.example.ddopik.phlogbusiness.ui.signup.presenter.UploadSignUpPhotoPresenter;
import com.example.ddopik.phlogbusiness.ui.signup.presenter.UploadSignUpPhotoPresenterImpl;

import java.io.File;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.ddopik.phlogbusiness.utiltes.Constants.REQUEST_CODE_CAMERA;
import static com.example.ddopik.phlogbusiness.utiltes.Constants.REQUEST_CODE_GALLERY;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class UploadSignUpPhotoActivity extends BaseActivity implements UploadSignUpPhotoView {


    private String TAG = UploadSignUpPhotoActivity.class.getSimpleName();
    private FrameLayout pickImage;
    private LinearLayout pickImageIcon;
    private Button uploadProfileBtn;
    private Button skipUploadProfileBtn;
    private ProgressBar uploadProfileImgProgress;
    private UploadSignUpPhotoPresenter uploadSignUpPhotoPresenter;
    private File imgPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_upload_profile);
        initPresenter();
        initView();
        initListener();
    }

    @Override
    public void initView() {
        pickImage = findViewById(R.id.pick_image);
        uploadProfileBtn = findViewById(R.id.upload_img_profile_btn);
        skipUploadProfileBtn = findViewById(R.id.skip_img_upload_btn);
        uploadProfileImgProgress = findViewById(R.id.upload_profile_img_progress);
        pickImageIcon=findViewById(R.id.pick_image_icon);
    }

    @Override
    public void initPresenter() {
        uploadSignUpPhotoPresenter=new UploadSignUpPhotoPresenterImpl(this,this);
    }

    private void initListener() {
        pickImage.setOnClickListener(view -> {
            openPickerDialog();
        });
        uploadProfileBtn.setOnClickListener(view -> {
            if (imgPath != null)
                uploadSignUpPhotoPresenter.uploadProfilePhoto(imgPath);

        });
        skipUploadProfileBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

    @Override
    public void viewUploadProfileImgProgress(boolean state) {
        if (state) {
            uploadProfileImgProgress.setVisibility(View.VISIBLE);
        } else {
            uploadProfileImgProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


    private void openPickerDialog() {
        CharSequence photoChooserOptions[] = new CharSequence[]{getResources().getString(R.string.general_photo_chooser_camera), getResources().getString(R.string.general_photo_chooser_gallery)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.general_photo_chooser_title));
        builder.setItems(photoChooserOptions, (dialog, option) -> {
            if (option == 0) {
                RequestCameraPermutations();
//                EasyImage.openCamera(this, 0);
            } else if (option == 1) {
                requestGalleryPermutations();
//                EasyImage.openGallery(this, 0);
            }
        }).show();
    }


    @AfterPermissionGranted(REQUEST_CODE_CAMERA)
    private void RequestCameraPermutations() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(getBaseContext(), perms)) {

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

        if (EasyPermissions.hasPermissions(getBaseContext(), perms)) {
            ImagePicker.create(this)
                    .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                    .single() // single mode
                    .folderMode(true)
                    .toolbarFolderTitle("Choose Image")
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
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            this.imgPath = new File(ImagePicker.getFirstImageOrNull(data).getPath());
            //Header Img


            GlideApp.with(getBaseContext())
                    .load(ImagePicker.getFirstImageOrNull(data).getPath())
                    .error(R.drawable.default_error_img)
                    .apply(RequestOptions.circleCropTransform())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                            pickImageIcon.setVisibility(View.GONE);
                            pickImage.setBackground(resource);
                        }
                    });

        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}

