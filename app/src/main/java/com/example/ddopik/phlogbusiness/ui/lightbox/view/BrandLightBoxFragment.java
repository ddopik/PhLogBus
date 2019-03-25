package com.example.ddopik.phlogbusiness.ui.lightbox.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.LightBox;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.AddNewLightBoxDialogFragment;
import com.example.ddopik.phlogbusiness.ui.MainActivity;
import com.example.ddopik.phlogbusiness.ui.lightbox.presenter.BrandLightBoxPresenter;
import com.example.ddopik.phlogbusiness.ui.lightbox.presenter.BrandLightBoxPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.lightbox.view.adapter.LightBoxAdapter;
import com.example.ddopik.phlogbusiness.utiltes.Constants;

import java.util.ArrayList;
import java.util.List;

public class BrandLightBoxFragment extends BaseFragment implements BrandLightBoxFragmentView {

    public static String TAG = BrandLightBoxFragment.class.getSimpleName();

    private View mainView;
    private BrandLightBoxPresenter brandLightBoxPresenter;
    private CustomRecyclerView lightBoxRv;
    private LightBoxAdapter lightBoxAdapter;
    private PagingController pagingController;
    private List<LightBox> lightBoxList = new ArrayList<LightBox>();
    private ProgressBar lightBoxProgressBar;
    private ImageButton lightBoxBackBtn, addLightBoxBtn;


    public static BrandLightBoxFragment getInstance() {
        BrandLightBoxFragment brandLightBoxFragment = new BrandLightBoxFragment();
        return brandLightBoxFragment;
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
        brandLightBoxPresenter.getLightBoxes(0, true);
    }


    @Override
    protected void initPresenter() {
        brandLightBoxPresenter = new BrandLightBoxPresenterImpl(this, getContext());
    }

    @Override
    protected void initViews() {

        lightBoxRv = mainView.findViewById(R.id.light_box_rv);
        lightBoxProgressBar = mainView.findViewById(R.id.lightbox_progress);
        addLightBoxBtn = mainView.findViewById(R.id.add_light_box_btn);
        lightBoxBackBtn = mainView.findViewById(R.id.lightbox_back_btn);
        lightBoxAdapter = new LightBoxAdapter(lightBoxList);
        lightBoxRv.setAdapter(lightBoxAdapter);

    }

    private void initListeners() {
//        pagingController = new PagingController(lightBoxRv) {
//            @Override
//            public void getPagingControllerCallBack(int page) {
//                brandLightBoxPresenter.getLightBoxes(page,false);
//            }
//        };

        lightBoxAdapter.onLightBoxClickListener = new LightBoxAdapter.OnLightBoxClickListener() {
            @Override
            public void onLightBoxClick(LightBox lightBox) {
                if (!lightBox.photos.isEmpty()) {
                    MainActivity.navigationManger.setMessageToFragment(lightBox);
                    MainActivity.navigationManger.navigate(Constants.NavigationHelper.LIGHT_BOX_PHOTOS);
                }
            }

            @Override
            public void onSliderContainerClicked(LightBox lightBox) {

            }

            @Override
            public void onDeleteLightBoxClicked(LightBox lightBox) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.delete_confirmation)
                        .setItems(new CharSequence[]{getString(R.string.yes), getString(R.string.no)}
                                , (dialog, which) -> {
                                    switch (which) {
                                        case 0:
                                            brandLightBoxPresenter.deleteLightBox(lightBox.id);
                                            break;
                                    }
                                    dialog.dismiss();
                                }).show();
            }
        };

        addLightBoxBtn.setOnClickListener(v -> {
            AddNewLightBoxDialogFragment addNewLightBoxDialogFragment = AddNewLightBoxDialogFragment.getInstance();
            addNewLightBoxDialogFragment.setOnDialogAdd(lightBoxName -> {
                brandLightBoxPresenter.addLightBox(lightBoxName, "desc");
                addNewLightBoxDialogFragment.dismiss();
            });
            addNewLightBoxDialogFragment.show(getChildFragmentManager(), AddNewLightBoxDialogFragment.class.getSimpleName());
        });
        lightBoxBackBtn.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
    }

    @Override
    public void viewLightBoxes(List<LightBox> lightBoxes, boolean forceRefreash) {
        if (forceRefreash) {
            this.lightBoxList.clear();
        }
        this.lightBoxList.addAll(lightBoxes);
        lightBoxAdapter.notifyDataSetChanged();


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

    @Override
    public void onLightBoxLightDeleted() {
        brandLightBoxPresenter.getLightBoxes(0, true);
    }

    @Override
    public void onLightBoxLightAdded() {
        brandLightBoxPresenter.getLightBoxes(0, true);
    }
}
