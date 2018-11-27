package com.example.ddopik.phlogbusiness;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.example.ddopik.phlogbusiness.base.BaseActivity;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void addFragment(int containerID, Fragment fragment, String tag, boolean addBackStack) {
        super.addFragment(containerID, fragment, tag, addBackStack);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }
}
