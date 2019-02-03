package com.example.ddopik.phlogbusiness.ui.profile.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.ui.cart.view.CartActivity;
import com.example.ddopik.phlogbusiness.ui.setupbrand.view.SetupBrandActivity;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.ui.MainActivity;
import com.example.ddopik.phlogbusiness.ui.profile.presenter.BrandProfilePresenter;
import com.example.ddopik.phlogbusiness.ui.profile.presenter.BrandProfilePresenterImpl;

import org.w3c.dom.Text;

import static com.example.ddopik.phlogbusiness.utiltes.Constants.NavigationHelper.ACCOUNT_DETAILS;
import static com.example.ddopik.phlogbusiness.utiltes.Constants.NavigationHelper.LIGHT_BOX;

/**
 * Created by abdalla_maged On Dec,2018
 * <p>
 * Fragment of brand profile (Personal)
 */
public class BusinessProfileFragment extends BaseFragment implements BrandProfileFragmentView, PopupMenu.OnMenuItemClickListener {

    private View mainView;
    private TextView brandName, brandWebSite, brandIndustry;
    private ImageView brandImgIcon;
    private ImageView brandProfileCoverImg;
    private LinearLayout accountDetailsBtn, setupBrandBtn, cartBtn, myLightBoxBtn;
    private ProgressBar brandProfileProgress;
    private ImageButton menuButton;

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
        brandProfileCoverImg=mainView.findViewById(R.id.brand_profile_cover_img);
        brandImgIcon = mainView.findViewById(R.id.brand_profile_img_ic);
        accountDetailsBtn = mainView.findViewById(R.id.account_detail_btn);
        setupBrandBtn = mainView.findViewById(R.id.setup_brand_btn);
        cartBtn = mainView.findViewById(R.id.cart_btn);
        myLightBoxBtn = mainView.findViewById(R.id.light_box_btn);

        brandProfileProgress = mainView.findViewById(R.id.brand_profile_progress);

        menuButton = mainView.findViewById(R.id.menu_button);
    }

    private void initListeners() {

        accountDetailsBtn.setOnClickListener(v -> {
            MainActivity.navigationManger.setMessageToFragment(business);
            MainActivity.navigationManger.navigate(ACCOUNT_DETAILS);
        });
        setupBrandBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SetupBrandActivity.class);
            intent.putExtra("business", business);
            startActivity(intent);
        });
        cartBtn.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), CartActivity.class));
        });
        myLightBoxBtn.setOnClickListener(v -> {
            MainActivity.navigationManger.navigate(LIGHT_BOX);
        });
        menuButton.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getContext(), v);
            // This activity implements OnMenuItemClickListener
            popup.setOnMenuItemClickListener(this);
            popup.inflate(R.menu.profile_fragment_menu);
            popup.show();
        });
    }

    private Business business;

    @SuppressLint("CheckResult")
    @Override
    public void viewBrandProfileData(Business business) {
        this.business = business;

        if (business.firstName != null)
            brandName.setText(String.format("%1$s %2$s", business.firstName, business.lastName));
        if (business.website != null)
            brandWebSite.setText(business.website);
        if (business.industry != null)
            brandIndustry.setText(business.industry.name);


        GlideApp.with(this)
                .load(business.thumbnail)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .into(brandImgIcon);

        GlideApp.with(this)
                .load(business.imageCover)
                .into(brandProfileCoverImg);

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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_edit:
                accountDetailsBtn.performClick();
                return true;
            case R.id.item_logout:
                return true;
        }
        return false;
    }
}
