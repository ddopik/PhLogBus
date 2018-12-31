package com.example.ddopik.phlogbusiness.ui.lightbox.view;

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
import com.example.ddopik.phlogbusiness.ui.lightbox.model.LightBox;
import com.example.ddopik.phlogbusiness.ui.lightbox.presenter.BrandLightBoxPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.lightbox.presenter.BrandLightBoxPresnter;

import java.util.ArrayList;
import java.util.List;

public class BrandLightBoxFragment extends BaseFragment implements BrandLightBoxFragmentView {


    private View mainView;
    private BrandLightBoxPresnter brandLightBoxPresnter;
    private CustomRecyclerView lightBoxRv;
    private LighBoxAdapter lighBoxAdapter;
    private PagingController pagingController;
    private List<LightBox> lightBoxList = new ArrayList<LightBox>();
    private ProgressBar lightBoxProgressBar;

    public static BrandLightBoxFragment getInstance(){
        BrandLightBoxFragment brandLightBoxFragment=new BrandLightBoxFragment();
        return  brandLightBoxFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_light_box_fragment, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListeners();
        brandLightBoxPresnter.getLightBoxes(0);
    }


    @Override
    protected void initPresenter() {
        brandLightBoxPresnter = new BrandLightBoxPresenterImpl(this, getContext());
    }

    @Override
    protected void initViews() {

        lightBoxRv = mainView.findViewById(R.id.light_box_rv);
        lightBoxProgressBar = mainView.findViewById(R.id.lightbox_progress);
        lighBoxAdapter = new LighBoxAdapter(lightBoxList);
        lightBoxRv.setAdapter(lighBoxAdapter);

    }

    private void initListeners() {
        pagingController = new PagingController(lightBoxRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                brandLightBoxPresnter.getLightBoxes(page);
            }
        };
    }

    @Override
    public void viewLightBoxes(List<LightBox> lightBoxes) {
        this.lightBoxList.addAll(lightBoxes);
        lighBoxAdapter.notifyDataSetChanged();

    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void viewLightBoxProgress(Boolean state) {
        if (state) {
            lightBoxProgressBar.setVisibility(View.VISIBLE);
        } else {
            lightBoxProgressBar.setVisibility(View.GONE);
        }

    }
}
