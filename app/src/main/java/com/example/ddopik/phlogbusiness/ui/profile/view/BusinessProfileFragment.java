package com.example.ddopik.phlogbusiness.ui.profile.view;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.ui.MainActivity;
import com.example.ddopik.phlogbusiness.ui.profile.presenter.BrandProfilePresenter;
import com.example.ddopik.phlogbusiness.ui.profile.presenter.BrandProfilePresenterImpl;

import static com.example.ddopik.phlogbusiness.utiltes.Constants.NavigationHelper.LIGHT_BOX;

/**
 * Created by abdalla_maged On Dec,2018
 * <p>
 * Fragment of brand profile (Personal)
 */
public class BusinessProfileFragment extends BaseFragment implements BrandProfileFragmentView {
    private View mainView;
    private CustomTextView brandName, brandWebSite, brandIndustry;
    private ImageView brandImgIcon;
    private FrameLayout bramdProfileCoverImg;
    private LinearLayout accountDetailsBtn, setupBrandBtn, cartBtn, myLightBoxBtn;
    private ProgressBar brandProfileProgress;
    private BrandProfilePresenter brandProfilePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_profile, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListeners();
        brandProfilePresenter.getProfileBrandData();
    }

    @Override
    protected void initPresenter() {
        brandProfilePresenter=new BrandProfilePresenterImpl(getContext(),this);
    }

    @Override
    protected void initViews() {
        brandName = mainView.findViewById(R.id.header_profile_brand_name);
        brandWebSite = mainView.findViewById(R.id.header_profile_brand_website);
        brandIndustry = mainView.findViewById(R.id.brand_profile_industry);
        bramdProfileCoverImg=mainView.findViewById(R.id.bramd_profile_cover_img);
        brandImgIcon = mainView.findViewById(R.id.brand_profile_img_ic);
        accountDetailsBtn = mainView.findViewById(R.id.account_detail_btn);
        setupBrandBtn = mainView.findViewById(R.id.setup_brand_btn);
        cartBtn = mainView.findViewById(R.id.cart_btn);
        myLightBoxBtn = mainView.findViewById(R.id.light_box_btn);

        brandProfileProgress = mainView.findViewById(R.id.brand_profile_progress);
    }

    private void initListeners() {

        accountDetailsBtn.setOnClickListener(v -> {

        });
        setupBrandBtn.setOnClickListener(v -> {

        });
        cartBtn.setOnClickListener(v -> {

        });
        myLightBoxBtn.setOnClickListener(v -> {
            MainActivity.navigationManger.navigate(LIGHT_BOX);
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void viewBrandProfileData(Business business) {


        if (business.firstName != null)
            brandName.setText(business.firstName +""+business.firstName);
        if (business.website != null)
            brandWebSite.setText(business.website);
        if (business.industry != null)
            brandIndustry.setText(business.industry.nameEn);




        GlideApp.with(this)
                .load(business.thumbnail)
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .into(brandImgIcon);

        GlideApp.with(this)
                .load(business.imageCover)
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // log exception
                        bramdProfileCoverImg.setBackground(getResources().getDrawable(R.drawable.default_error_img));
                        return false; // important to return false so the error placeholder can be placed
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        bramdProfileCoverImg.setBackground(resource);
                        return false; }});

    }

    @Override
    public void viewMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void viewBrandProfileProgress(Boolean state) {
        if (state) {
            brandProfileProgress.setVisibility(View.VISIBLE);
        } else {
            brandProfileProgress.setVisibility(View.GONE);
        }
    }
}
