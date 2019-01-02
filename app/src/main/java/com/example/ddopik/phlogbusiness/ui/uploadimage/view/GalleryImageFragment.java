package com.example.ddopik.phlogbusiness.ui.uploadimage.view;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.esafirm.imagepicker.features.ImagePicker;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.ui.uploadimage.view.adapter.GalleryImageAdapter;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import java.util.ArrayList;

import static com.example.ddopik.phlogbusiness.utiltes.Constants.REQUEST_CODE_CAMERA;
import static com.example.ddopik.phlogbusiness.utiltes.Constants.REQUEST_CODE_GALLERY;


/**
 * Created by abdalla_maged on 10/22/2018.
 */
public class GalleryImageFragment extends BaseFragment {

    private View mainView;
    private GalleryImageAdapter galleryImageAdapter;
    private CustomRecyclerView galleryRv;
    private ImageButton openCameraBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mainView = inflater.inflate(R.layout.fragment_gallery_image, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestGalleryPermutations();

    }

    @AfterPermissionGranted(REQUEST_CODE_GALLERY)
    private void requestGalleryPermutations() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(getContext(), perms)) {

            galleryImageAdapter = new GalleryImageAdapter(getContext(), getAllShownImagesPath());
            initViews();
            initListeners();

        }
        // Already have permission

        else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    REQUEST_CODE_GALLERY, perms);
        }

    }
    @Override
    public void initViews() {
        galleryRv = mainView.findViewById(R.id.gallery_img_rv);
        galleryRv.setAdapter(galleryImageAdapter);
        openCameraBtn = mainView.findViewById(R.id.open_camera_btn);


    }

    @Override
    public void initPresenter() {
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void initListeners() {
        galleryImageAdapter.onGalleryImageClicked = imagePath -> {
            Intent intent = new Intent(getContext(), ImageFilterActivity.class);
            intent.putExtra(ImageFilterActivity.ImageFilter, imagePath);
            startActivity(intent);
        };
        openCameraBtn.setOnClickListener((view) -> {

            RequestCameraPermutations();
        });


    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }


    private ArrayList<String> getAllShownImagesPath() {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = getContext().getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

            String image = ImagePicker.getFirstImageOrNull(data).getPath();
            Intent intent = new Intent(getContext(), ImageFilterActivity.class);
            intent.putExtra("image_uri", image);
            startActivity(intent);

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
