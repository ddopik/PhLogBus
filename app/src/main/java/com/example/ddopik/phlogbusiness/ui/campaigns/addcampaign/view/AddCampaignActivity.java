package com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.model.AddCampaignRequestModel;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import java.io.File;

import static com.example.ddopik.phlogbusiness.Utiltes.Constants.REQUEST_CODE_CAMERA;
import static com.example.ddopik.phlogbusiness.Utiltes.Constants.REQUEST_CODE_GALLERY;
import static com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view.AddCampaignStepTwoActivity.CAMPAIGN_DATA;

public class AddCampaignActivity extends BaseActivity {

    private ImageView campaignPicCoverBtn;
    private CustomTextView uploadCampaignImgLapel;
    private TextInputLayout campaignNameInput;
    private EditText campaignName;
    private Button campaignNextBtn;
    private AddCampaignRequestModel addCampaignRequestModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_campaign);
        initView();
        initListener();
    }

    @Override
    public void initView() {
        uploadCampaignImgLapel = findViewById(R.id.upload_campaign_img_lapel);
        addCampaignRequestModel=new AddCampaignRequestModel();
        campaignPicCoverBtn = findViewById(R.id.campaign_pic_cover_btn);
        campaignNameInput=findViewById(R.id.campaign_name_input);
        campaignName = findViewById(R.id.campaign_name);
        campaignNextBtn = findViewById(R.id.add_campaign_step_one_next_btn);
    }

    @Override
    public void initPresenter() {

    }

    private void initListener() {

        campaignPicCoverBtn.setOnClickListener(v -> {
            openPickerDialog();
        });
        campaignNextBtn.setOnClickListener(v -> {
            if (validInputs()) {
                addCampaignRequestModel.campaignName = campaignName.getText().toString();
                Intent intent = new Intent(this, AddCampaignStepTwoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra(CAMPAIGN_DATA, addCampaignRequestModel);
                startActivity(intent);
            }
        });
    }


    private Boolean validInputs() {
        boolean inputStats = true;
        if (campaignName.getText().length() == 0) {
            campaignNameInput.setError(getResources().getString(R.string.campaign_name_required));
            inputStats = false;
        } else {
            campaignNameInput.setErrorEnabled(false);
        }

        if (addCampaignRequestModel.campaignCoverPhoto == null) {
//            campaignNameInput.setEnabled(true);
            uploadCampaignImgLapel.setText(getResources().getString(R.string.campaign_image_cover_required));
            uploadCampaignImgLapel.setTextColor(getResources().getColor(R.color.colorRed));
            inputStats = false;
        } else {
            uploadCampaignImgLapel.setText(getResources().getString(R.string.choose_cover_image));
            uploadCampaignImgLapel.setTextColor(getResources().getColor(R.color.gray677078));
        }

        return inputStats;
    }

    private void openPickerDialog() {
        CharSequence photoChooserOptions[] = new CharSequence[]{getResources().getString(R.string.general_photo_chooser_camera), getResources().getString(R.string.general_photo_chooser_gallery)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        if (EasyPermissions.hasPermissions(this, perms)) {

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

        if (EasyPermissions.hasPermissions(this, perms)) {
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
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            //Header Img
            GlideApp.with(this)
                    .load(ImagePicker.getFirstImageOrNull(data).getPath())
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_error_img)
                    .into(campaignPicCoverBtn);
            addCampaignRequestModel.campaignCoverPhoto=new File(ImagePicker.getFirstImageOrNull(data).getPath());
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
