package com.example.ddopik.phlogbusiness.base;

import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.fgm.model.FirebaseNotificationData;
import com.example.ddopik.phlogbusiness.ui.dialog.popup.view.PopupDialogFragment;
import com.example.ddopik.phlogbusiness.ui.notification.model.NotificationList;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.notification.NotificationFactory;
import com.example.ddopik.phlogbusiness.utiltes.rxeventbus.RxEventBus;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by abdalla-maged on 3/27/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

//    protected abstract void addFragment(Fragment fragment,String title,String tag);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disposable disposable = RxEventBus.getInstance().getSubject().subscribe(event -> {
            switch (event.getType()) {
                case POPUP:
                    NotificationList data = (NotificationList) event.getObject();
                    if (data != null && data.popup != Constants.PopupType.NONE)
                        showPopup(data);
                    break;
                case CONNECTIVITY:
                    boolean connected = (Boolean) event.getObject();
                    if (!connected && !snackBarShowing) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content)
                                , R.string.connection_problem_check_network
                                , Snackbar.LENGTH_LONG);
                        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                snackBarShowing = false;
                                super.onDismissed(transientBottomBar, event);
                            }

                            @Override
                            public void onShown(Snackbar transientBottomBar) {
                                snackBarShowing = true;
                                super.onShown(transientBottomBar);
                            }
                        });
                        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorRed));
                        snackbar.show();
                    }
                    break;
            }
        }, throwable -> {
            Log.e("MainActivity-Noti", throwable.getMessage());
        });
        disposables.add(disposable);
    }

    public abstract void initView();

    public abstract void initPresenter();

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    public void addFragment(int containerID, Fragment fragment, String tag, boolean addBackStack) {
        if (addBackStack) {
            try {
                getSupportFragmentManager().beginTransaction().replace(containerID, fragment, tag).addToBackStack(tag).commit();
            } catch (IllegalStateException e) {
                getSupportFragmentManager().beginTransaction().replace(containerID, fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
            }
        } else {
            try {
                getSupportFragmentManager().beginTransaction().replace(containerID, fragment, tag).commit();
            } catch (IllegalStateException e) {
                getSupportFragmentManager().beginTransaction().replace(containerID, fragment, tag).commitAllowingStateLoss();
            }

        }
    }

    private boolean snackBarShowing;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }

    protected void showPopup(NotificationList data) {
        PopupDialogFragment.newInstance(data, () -> {
            switch (data.popup) {
                case Constants.PopupType.CHOOSE_WINNER:
//                    navigationManger.navigate(PROFILE);
                    break;
                case Constants.PopupType.DOCUMENTS_APPROVED:
//                    Intent i2 = new Intent(this, CampaignInnerActivity.class);
//                    i2.putExtra(CampaignInnerActivity.CAMPAIGN_ID, String.valueOf(data.notification.campaign.id));
//                    i2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    startActivity(i2);
                    break;
            }
        }).show(getSupportFragmentManager(), null);
    }
}
