package com.example.ddopik.phlogbusiness.ui.setupbrand.fragment.stepthree;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.Doc;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.SetupBrandModel;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandActivity;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandActivity.SubViewActionConsumer.ActionType;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandActivity.SubViewActionConsumer.SubViewAction;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandView;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import com.example.ddopik.phlogbusiness.utiltes.uploader.UploaderService;

import java.util.List;

import io.reactivex.functions.Consumer;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.ddopik.phlogbusiness.utiltes.Constants.REQUEST_CODE_CAMERA;
import static com.example.ddopik.phlogbusiness.utiltes.Constants.REQUEST_CODE_GALLERY;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepThreeFragment extends BaseFragment {

    private static final String TAG = StepThreeFragment.class.getSimpleName();

    private SetupBrandActivity.SubViewActionConsumer consumer;
    private SetupBrandModel model = new SetupBrandModel();

    private Intent intent;
    private Messenger messenger;
    private boolean uploading;
    private boolean bound;
    private boolean started;

    private View mainView;
    private RecyclerView docsRecyclerView;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            bound = true;
            Message message = new Message();
            message.what = UploaderService.ADD_COMMUNICATOR;
            message.obj = communicator;
            sendMessageToService(message);
            if (shouldUploadOnBind) {
                shouldUploadOnBind = false;
                sendMessageToService(pendingMessage);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    private SetupBrandView.Communicator communicator = (type, objects) -> {
        switch (type) {
            case PROGRESS:
            case DONE:
                if (objects != null && objects.length != 0) {
                    if (objects[0] instanceof Doc) {
                        Doc doc = (Doc) objects[0];
                        if (docsRecyclerView.getAdapter() != null)
                            ((DocsAdapter) docsRecyclerView.getAdapter()).updateView(doc, type);
                    }
                }
                break;
            case ALL_UPLOADING_DONE:
//                if (getContext() != null)
//                    PrefUtils.setIsUploading(getContext(), false);
                // TODO: can submit?
                break;
            case ERROR:
                if (objects != null && objects.length != 0) {
                    if (objects[0] instanceof Doc) {
                        Doc doc = (Doc) objects[0];
                        if (docsRecyclerView.getAdapter() != null)
                            ((DocsAdapter) docsRecyclerView.getAdapter()).updateView(doc, type);
                    }
                }
                break;
        }
    };

    private Consumer<String> selectedFilePathConsumer;
    private Doc currentDoc;

    private boolean shouldUploadOnBind;
    private Message pendingMessage;

    private DocsAdapter.ActionListener actionListener = (type, objects) -> {
        switch (type) {
            case UPLOAD:if (objects != null && objects.length != 0) {
                if (objects[0] instanceof Doc) {
                    Message message = new Message();
                    message.what = UploaderService.UPLOAD_FILE;
                    message.obj = objects[0];
                    if (!started) {
                        shouldUploadOnBind = true;
                        pendingMessage = message;
                        getActivity().startService(intent);
                        getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
                    } else
                        sendMessageToService(message);
                }
            }
                break;
            case SELECT:
                if (objects != null && objects.length >= 1) {
                    if (objects[0] instanceof Consumer && objects[1] instanceof Doc) {
                        selectedFilePathConsumer = (Consumer<String>) objects[0];
                        currentDoc = (Doc) objects[1];
                        openPickerDialog();
                    }
                }
                break;
        }
    };

    private Consumer<List<Doc>> listConsumer = docs -> {
        if (docsRecyclerView.getAdapter() == null) {
            docsRecyclerView.setAdapter(new DocsAdapter(actionListener));
        }
        DocsAdapter adapter = (DocsAdapter) docsRecyclerView.getAdapter();
        adapter.setList(docs);
    };

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
        initPresenter();
        uploading = PrefUtils.getIsUploading(getContext());
        intent = new Intent(getContext(), UploaderService.class);
        if (uploading) {
//            getActivity().startService(intent);
            started = true;
            getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onDestroy() {
        Message message = new Message();
        message.what = UploaderService.REMOVE_COMMUNICATOR;
        message.obj = communicator;
        sendMessageToService(message);
        if (bound)
            unbindService();
        super.onDestroy();
    }

    @Override
    protected void initPresenter() {
        consumer.accept(new SubViewAction(ActionType.GET_DOCUMENT_LIST, listConsumer));
    }

    @Override
    protected void initViews() {
        docsRecyclerView = mainView.findViewById(R.id.recycler_view);
        docsRecyclerView.setAdapter(new DocsAdapter(actionListener));
    }

    private void initListeners() {

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
            String path = ImagePicker.getFirstImageOrNull(data).getPath();
            currentDoc.path = path;
            try {
                selectedFilePathConsumer.accept(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessageToService(Message message) {
        try {
            if (messenger != null)
                messenger.send(message);
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void unbindService() {
        try {
            getActivity().unbindService(connection);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
