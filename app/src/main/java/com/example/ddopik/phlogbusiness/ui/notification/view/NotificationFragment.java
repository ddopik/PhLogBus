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
import com.example.ddopik.phlogbusiness.base.PagingController;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.ui.notification.model.NotificationData;
import com.example.ddopik.phlogbusiness.ui.notification.model.NotificationList;
import com.example.ddopik.phlogbusiness.ui.notification.presenter.NotificationPresenter;
import com.example.ddopik.phlogbusiness.ui.notification.presenter.NotificationPresenterImp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class NotificationFragment extends BaseFragment implements NotificationFragmentView {
    private View mainView;
    private ProgressBar notificationProgress;
    private CustomRecyclerView notificationRv;
    private NotificationPresenter notificationPresenter;
    private List<NotificationList> notificationItemList = new ArrayList<>();
    private NotifiationAdapter notifiationAdapter;
    private PagingController pagingController;
    private String nextPageUrl = "1";
    private boolean isLoading;

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
        notificationPresenter.getNotification(nextPageUrl);
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
        notificationRv = mainView.findViewById(R.id.notification_rv);
        notificationProgress = mainView.findViewById(R.id.notification_progress);
        NotificationList notificationList = new NotificationList();
        notificationList.message = getContext().getResources().getString(R.string.earlier);
        notificationList.entityId = NotifiationAdapter.itemType_NOTIFICATION_HEAD;
        notificationItemList.add(notificationList);
        notifiationAdapter = new NotifiationAdapter(notificationItemList);
        notificationRv.setAdapter(notifiationAdapter);

    }

    private void initListener() {


        pagingController = new PagingController(notificationRv) {


            @Override
            protected void loadMoreItems() {
                notificationPresenter.getNotification(nextPageUrl);
            }

            @Override
            public boolean isLastPage() {

                if (nextPageUrl == null) {
                    return true;
                } else {
                    return false;
                }

            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }


        };
    }


    @Override
    public void viewNotificationList(NotificationData notificationData) {
        notificationItemList.addAll(notificationData.notificationList);
        notifiationAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewNotificationProgress(boolean state) {
        isLoading = state;

        if (state) {
            notificationProgress.setVisibility(View.VISIBLE);
        } else {
            notificationProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNextPageUrl(String page) {
        this.nextPageUrl = page;
    }

}

