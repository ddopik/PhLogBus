package com.example.ddopik.phlogbusiness.ui.downloads.view;


import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.ui.downloads.model.GroupItem;
import com.example.ddopik.phlogbusiness.ui.downloads.presenter.DownloadsPresenter;
import com.example.ddopik.phlogbusiness.ui.downloads.presenter.DownloadsPresenterImpl;

import java.util.List;

import com.example.ddopik.phlogbusiness.utiltes.downloader.DownloaderService;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadsFragment extends BaseFragment implements DownloadsView, EasyPermissions.PermissionCallbacks {

    public static final String TAG = DownloadsFragment.class.getSimpleName();

    public static final int EXTERNAL_STORAGE_REQUEST_CODE = 5003;

    private DownloadsPresenter presenter;

    private View mainView;
    private TextView itemsNumberTV;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProgressBar loadiing;
    private TextView noDownloadsTV;
    private View noDownloadsIcon;

    private Intent intent;
    private Messenger messenger;
    private boolean bound;
    private boolean started;

    private boolean shouldDownloadOnBind;
    private Message pendingMessage;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            bound = true;
            if (shouldDownloadOnBind) {
                shouldDownloadOnBind = false;
                sendMessageToService(pendingMessage);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    public DownloadsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return mainView = inflater.inflate(R.layout.fragment_downloads, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setListeners();
        initPresenter();
        intent = new Intent(getContext(), DownloaderService.class);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bound)
            unbindService();
    }

    @Override
    protected void initPresenter() {
        presenter = new DownloadsPresenterImpl();
        presenter.setView(this);
        presenter.getDownloads(getContext());
    }

    @Override
    protected void initViews() {
        itemsNumberTV = mainView.findViewById(R.id.items_number_text_view);
        tabLayout = mainView.findViewById(R.id.tab_layout);
        viewPager = mainView.findViewById(R.id.view_pager);
        tabLayout.setupWithViewPager(viewPager);
        loadiing = mainView.findViewById(R.id.loading);
        noDownloadsTV = mainView.findViewById(R.id.no_downloads_tv);
        noDownloadsIcon = mainView.findViewById(R.id.no_dowloads_icon);
    }

    private void setListeners() {
    }

    @Override
    public void setDownloads(List<GroupItem> data) {
        int count = 0;
        for (GroupItem item : data) {
            count += item.getPhotos().size();
        }
        itemsNumberTV.setText(getString(R.string.purchase_count, count));
        if (count > 0) {
            PagerAdapter adapter = new DownloadsPagerAdpter(getChildFragmentManager(), data, childFragmentActionListener);
            viewPager.setAdapter(adapter);
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_list);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_grid);
        } else {
            viewPager.setVisibility(View.INVISIBLE);
            noDownloadsIcon.setVisibility(View.VISIBLE);
            noDownloadsTV.setVisibility(View.VISIBLE);
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

    private ChildFragmentActionListener childFragmentActionListener = (type, image) -> {
        switch (type) {
            case DOWnLOAD:
                if (getContext() == null)
                    return;
                if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Message message = new Message();
                    message.what = DownloaderService.DOWNLOAD_FILE;
                    message.obj = image;
                    if (!started) {
                        started = true;
                        shouldDownloadOnBind = true;
                        pendingMessage = message;
                        getActivity().startService(intent);
                        if (!bound)
                            getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
                    } else
                        sendMessageToService(message);
                } else {
                    EasyPermissions.requestPermissions(this
                            , getString(R.string.external_storage_write_permission_rationale)
                            , EXTERNAL_STORAGE_REQUEST_CODE
                            , Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                break;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void setLoading(boolean loading) {
        if (loading)
            this.loadiing.setVisibility(View.VISIBLE);
        else this.loadiing.setVisibility(View.GONE);
    }

    public interface ChildFragmentActionListener {

        void onAction(ActionType type, BaseImage image);

        enum ActionType {
            DOWnLOAD
        }
    }
}
