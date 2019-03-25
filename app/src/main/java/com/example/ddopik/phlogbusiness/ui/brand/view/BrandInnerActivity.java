package com.example.ddopik.phlogbusiness.ui.brand.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.ui.brand.model.BrandInnerData;
import com.example.ddopik.phlogbusiness.ui.brand.presenter.BrandInnerDataPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.brand.presenter.BrandInnerPresenter;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import io.reactivex.annotations.NonNull;

/**
 * Created by abdalla_maged on 11/12/2018.
 */
public class BrandInnerActivity extends BaseActivity implements BrandInnerActivityView {

    public static String BRAND_ID = "brand_id";
    private Toolbar brandProfileToolBar;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout brandProfileCollapsingToolbarLayout;
    private FrameLayout brandHeaderImg;
    private ImageView brandIconImg;
    private ImageButton backBtn;
    private TextView brandName, brandNumFollowers, brandType, aboutBrand, brandData, brandWebsite, brandMail, brandCampaign, brandProfileToolbarTitle;
    private BrandInnerPresenter brandInnerPresenter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_brand);
        if (getIntent().getStringExtra(BRAND_ID) != null) {
            initPresenter();
            initView();
            intiListeners();
            brandInnerPresenter.getBrandInnerData(getIntent().getStringExtra(BRAND_ID));
        }
    }


    @Override
    public void initView() {

        mAppBarLayout = findViewById(R.id.brand_profile_appBar);
        brandProfileCollapsingToolbarLayout = findViewById(R.id.brand_profile_collapsing_layout);
        brandProfileToolBar = findViewById(R.id.brand_profile_toolbar);
        brandProfileToolbarTitle = findViewById(R.id.brand_profile_toolbar_title);
         backBtn = findViewById(R.id.back_btn);
        brandHeaderImg = findViewById(R.id.header_background_img);
        brandIconImg = findViewById(R.id.brand_img_icon);
        brandName = findViewById(R.id.brand_name);
        brandNumFollowers = findViewById(R.id.brand_num_followers);
        brandType = findViewById(R.id.brand_type);
        aboutBrand = findViewById(R.id.about_brand);
        brandData = findViewById(R.id.brand_data);
        brandWebsite = findViewById(R.id.brand_website);
        brandMail = findViewById(R.id.brand_mail);
        brandCampaign = findViewById(R.id.brand_campaign);
        progressBar = findViewById(R.id.brand_progress_bar);

    }

    @Override
    public void initPresenter() {
        brandInnerPresenter = new BrandInnerDataPresenterImpl(getBaseContext(), this);
    }

    @Override
    public void viewInnerBrandData(BrandInnerData brandInnerData) {

        GlideApp.with(this)
                .load(brandInnerData.coverImage)
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // log exception
                        Log.e("TAG", "Error loading image", e);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                        brandHeaderImg.setBackground(resource);
                    }
                });

        GlideApp.with(this)
                .load(brandInnerData.coverImage)
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .apply(RequestOptions.circleCropTransform())
                .into(brandIconImg);
        GlideApp.with(this).load(brandInnerData.thumbnail).apply(RequestOptions.circleCropTransform()).into(brandIconImg);


        if (brandInnerData.nameEn != null) {
            brandName.setText(brandInnerData.nameEn);
            brandProfileToolbarTitle.setText(brandInnerData.nameEn);

            aboutBrand.setText(new StringBuilder().append(getResources().getString(R.string.about_brand)).append(" : ").append(brandInnerData.nameEn).toString());
        }
        if (brandInnerData.numberOfFollowers != null) {
            brandNumFollowers.setText(new StringBuilder().append(brandInnerData.numberOfFollowers).append(" ").append(getResources().getString(R.string.followers)).toString());
        }
        if (brandInnerData.industry != null) {
            brandType.setText(brandInnerData.industry.name);
        }
        if (brandInnerData.descrption != null) {
            brandData.setText(brandInnerData.descrption);
        }
        if (brandInnerData.website != null) {
            brandWebsite.setText(brandInnerData.website);
        }
        if (brandInnerData.mail != null) {
            brandWebsite.setText(brandInnerData.mail);

        }

    }

    private void intiListeners() {

        brandProfileCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.black));
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    brandProfileToolBar.setVisibility(View.VISIBLE);
                } else if (isShow) {
                    isShow = false;
                    brandProfileToolBar.setVisibility(View.GONE);
                }
            }
        });
        backBtn.setOnClickListener(v -> onBackPressed());
    }


    @Override
    public void viewInnerBrandProgressBar(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
