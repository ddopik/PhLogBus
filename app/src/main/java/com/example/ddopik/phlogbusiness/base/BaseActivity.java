package com.example.ddopik.phlogbusiness.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.fgm.model.FirebaseNotificationData;
import com.example.ddopik.phlogbusiness.ui.dialog.popup.view.PopupDialogFragment;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
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
    }

    public abstract void initView();

    public abstract void initPresenter();

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    public void addFragment(int containerID, Fragment fragment, String tag, boolean addBackStack) {
        if (addBackStack) {
            getSupportFragmentManager().beginTransaction().replace(containerID, fragment, tag).addToBackStack(tag).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(containerID, fragment, tag).commit();

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Disposable disposable = RxEventBus.getInstance().getSubject().subscribe(event -> {
            switch (event.getType()) {
                case POPUP:
                    FirebaseNotificationData data = (FirebaseNotificationData) event.getObject();
                    if (data.notification.popup != Constants.PopupType.NONE)
                        showPopup(data);
                    break;
                case CONNECTIVITY:
                    boolean connected = (Boolean) event.getObject();
                    if (!connected) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content)
                                , R.string.connection_problem_check_network
                                , Snackbar.LENGTH_LONG);
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

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }

    protected void showPopup(FirebaseNotificationData data) {
        PopupDialogFragment.newInstance(data, () -> {
//            switch (data.notification.popup) {
//                case Constants.PopupType.LEVEL_UP:
//                    navigationManger.navigate(PROFILE);
//                    break;
//                case Constants.PopupType.WON_CAMPAIGN:
//                    Intent i2 = new Intent(this, CampaignInnerActivity.class);
//                    i2.putExtra(CampaignInnerActivity.CAMPAIGN_ID, String.valueOf(data.notification.campaign.id));
//                    i2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    startActivity(i2);
//                    break;
//            }
        }).show(getSupportFragmentManager(), null);
    }
}
