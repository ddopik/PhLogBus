package com.example.ddopik.phlogbusiness.ui.notification.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.PagingController;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.ui.notification.model.NotificationData;
import com.example.ddopik.phlogbusiness.ui.notification.model.NotificationList;
import com.example.ddopik.phlogbusiness.ui.notification.presenter.NotificationPresenter;
import com.example.ddopik.phlogbusiness.ui.notification.presenter.NotificationPresenterImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class NotificationFragment extends BaseFragment implements NotificationFragmentView {
    private View mainView;
    private ProgressBar notificationProgress;
    private CustomTextView toolbarTitle;
    private ImageButton backBtn;
    private CustomRecyclerView notificationRv;
    private NotificationPresenter notificationPresenter;
    private List<NotificationList> notificationItemList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
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
        toolbarTitle = mainView.findViewById(R.id.toolbar_title);
        backBtn = mainView.findViewById(R.id.back_btn);
        notificationRv = mainView.findViewById(R.id.notification_rv);
        notificationProgress = mainView.findViewById(R.id.notification_progress);
        toolbarTitle.setText(getResources().getString(R.string.notification));
        NotificationList notificationList = new NotificationList();
        notificationList.message = getContext().getResources().getString(R.string.earlier);
//        notificationList.entityId = NotificationAdapter.itemType_NOTIFICATION_HEAD;
//        notificationItemList.add(notificationList);
        notificationAdapter = new NotificationAdapter(notificationItemList);
        notificationRv.setAdapter(notificationAdapter);

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

        backBtn.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).onBackPressed();
        });
    }


    @Override
    public void viewNotificationList(NotificationData notificationData) {
        notificationItemList.addAll(notificationData.notificationList);
        notificationAdapter.notifyDataSetChanged();
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

