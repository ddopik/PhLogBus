package com.example.ddopik.phlogbusiness.ui.notification.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.notification.model.NotificationData;
import com.example.ddopik.phlogbusiness.ui.notification.model.NotificationItem;
import com.example.ddopik.phlogbusiness.ui.notification.presenter.NotificationPresenter;
import com.example.ddopik.phlogbusiness.ui.notification.presenter.NotificationPresenterImp;


import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class NotificationFragment extends BaseFragment implements NotificationFragmentView {
    private View mainView;
    private ProgressBar notificationProgress;
    private CustomRecyclerView notificationRv;
    private NotificationPresenter notificationPresenter;
    private List<NotificationItem> notificationItemList = new ArrayList<>();
    private NotifiationAdapter notifiationAdapter;
    private PagingController pagingController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_notification, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    protected void initPresenter() {

        notificationPresenter = new NotificationPresenterImp(getContext(), this);
    }


    @Override
    protected void initViews() {

        NotificationItem notificationItem=new NotificationItem();
        notificationItem.notificationId="1";
        notificationItem.notificationImg="data:image/png;base64,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";
        notificationItem.notificationTitle="here is your Photo";
        notificationItem.notificationDate="5 min ago";

        notificationItemList.add(notificationItem);
        notificationItemList.add(notificationItem);
        notificationItemList.add(notificationItem);
        notificationItemList.add(notificationItem);
        notificationItemList.add(notificationItem);
        notificationItemList.add(notificationItem);
        notificationItemList.add(notificationItem);
        notificationItemList.add(notificationItem);
        notificationItemList.add(notificationItem);

        notificationRv = mainView.findViewById(R.id.notification_rv);
        notificationProgress = mainView.findViewById(R.id.notification_progress);
        notifiationAdapter=new NotifiationAdapter(notificationItemList);
        notificationRv.setAdapter(notifiationAdapter);

    }

    private void initListener() {
//        pagingController = new PagingController(notificationRv) {
//            @Override
//            public void getPagingControllerCallBack(int page) {
//                notificationPresenter.getNotification(String.valueOf(page));
//            }
//        };
    }

    @Override
    public void viewNotificationList(NotificationData notificationData) {
        notificationItemList.addAll(notificationData.notificationItemList);

    }

    @Override
    public void viewNotificationProgress(boolean state) {
        if (state) {
            notificationProgress.setVisibility(View.VISIBLE);
        } else {
            notificationProgress.setVisibility(View.GONE);
        }
    }
}

