package com.example.ddopik.phlogbusiness.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by abdalla-maged on 3/27/18.
 */

public  abstract  class BaseActivity extends AppCompatActivity {

//    protected abstract void addFragment(Fragment fragment,String title,String tag);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void initView();
    public abstract void initPresenter();
    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }


    public void addFragment(int containerID, Fragment fragment, String tag, boolean addBackStack) {
        if (addBackStack){
            getSupportFragmentManager().beginTransaction().replace(containerID, fragment, tag).addToBackStack(tag).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(containerID, fragment, tag).commit();

        }



    }

}
