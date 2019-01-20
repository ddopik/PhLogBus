package com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.base.commonmodel.LightBox;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.view.AddToLightBoxDialogFragment;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.view.AddToLightBoxDialogFragmentView;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddNewLightBoxDialogPresenterImpl implements AddNewLightBoxDialogPresenter{

    private String TAG=AddNewLightBoxDialogPresenterImpl.class.getSimpleName();
    private Context context;
    private AddToLightBoxDialogFragmentView addToLightBoxDialogFragmentView;

    public AddNewLightBoxDialogPresenterImpl(Context context,AddToLightBoxDialogFragmentView addToLightBoxDialogFragmentView){
        this.context=context;
        this.addToLightBoxDialogFragmentView=addToLightBoxDialogFragmentView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getLightBoxes() {
        addToLightBoxDialogFragmentView.viewLightBoxProgress(true);
        BaseNetworkApi.getLightBoxes().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addToLightBoxResponse -> {
                    addToLightBoxDialogFragmentView.viewLightBoxes(addToLightBoxResponse.data);
                    addToLightBoxDialogFragmentView.viewLightBoxProgress(false);
                }, throwable -> {

                    CustomErrorUtil.Companion.setError(context,TAG,throwable);
                    addToLightBoxDialogFragmentView.viewLightBoxProgress(false);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void addImagesToLightBox(List<LightBox> lightBoxId, int imageID,AddToLightBoxDialogFragment addToLightBoxDialogFragment) {


        Map<String,String> data=new HashMap<String, String>();

        for ( int i=0;i<lightBoxId.size();i++){
            if (lightBoxId.get(i).isChecked)
            data.put("lightbox_id["+i+"]",lightBoxId.get(i).id.toString());
        }

        data.put("photo_id",String.valueOf(imageID));

        addToLightBoxDialogFragmentView.viewLightBoxProgress(true);
        BaseNetworkApi.addImageToLightBox(data).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addToLightBoxResponse -> {
                    addToLightBoxDialogFragmentView.viewMessage(addToLightBoxResponse.msg);
                    addToLightBoxDialogFragmentView.onImageAddedToLightBox(true);
                    addToLightBoxDialogFragmentView.viewLightBoxProgress(false);
                    addToLightBoxDialogFragment.dismiss();

                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context,TAG,throwable);
                    addToLightBoxDialogFragmentView.onImageAddedToLightBox(false);
                    addToLightBoxDialogFragmentView.viewLightBoxProgress(false);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void addLightBox(String name,String description) {

        addToLightBoxDialogFragmentView.viewLightBoxProgress(true);
        HashMap<String,String> data=new HashMap<String, String>();
        data.put("name",name);
        data.put("description",description);
        BaseNetworkApi.addLightBox(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addLightBoxResponse -> {
                    addToLightBoxDialogFragmentView.viewLightBoxProgress(false);
                    addToLightBoxDialogFragmentView.viewMessage(addLightBoxResponse.msg);
                    getLightBoxes();

                },throwable -> {
                    addToLightBoxDialogFragmentView.viewLightBoxProgress(false);
                    CustomErrorUtil.Companion.setError(context,TAG,throwable);
                });
    }
}
