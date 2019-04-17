package com.example.ddopik.phlogbusiness.ui.search.mainSearchView.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.ui.search.album.view.AlbumSearchFragment;
import com.example.ddopik.phlogbusiness.ui.search.images.view.ImagesSearchFragment;
import com.example.ddopik.phlogbusiness.ui.search.profile.view.ProfileSearchFragment;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;


/**
 * Created by abdalla_maged on 10/29/2018.
 */
public class SearchActivity extends BaseActivity   {

    private EditText searchView;
    private CustomTextView toolBarTitle;
    private ImageButton backBtn;

    private CustomTextView imagesTab, profileTab, albumTab, filterTab, searchResult,clearFilterResultBtn;
    private ImageView filterIcon;
    private FrameLayout searchContainer;
    private OnFilterClicked onFilterClicked;
    private OnSearchTabSelected onSearchTabSelected;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initPresenter();
        initView();
        initListener();

        /////default tap
        setTapSelected(R.id.tab_images);

    }


    @Override
    public void initView() {
        backBtn = findViewById(R.id.back_btn);
        toolBarTitle = findViewById(R.id.toolbar_title);
        toolBarTitle.setText(getResources().getString(R.string.search));
        searchView = findViewById(R.id.search_view);
        imagesTab = findViewById(R.id.tab_images);
        profileTab = findViewById(R.id.tab_profile);
        albumTab = findViewById(R.id.tab_album);
        searchContainer = findViewById(R.id.search_container);
        filterTab = findViewById(R.id.filter_ic);
        searchResult = findViewById(R.id.search_result);
        filterIcon = findViewById(R.id.filter_icon);
        clearFilterResultBtn=findViewById(R.id.clear_filter_result_btn);

    }

    @Override
    public void initPresenter() {

    }

    private void initListener() {

        onSearchTabSelected = new OnSearchTabSelected() {
            @Override
            public EditText getSearchView() {
                return searchView;
            }

            @Override
            public TextView getSearchResultCountView() {
                return searchResult;
            }


        };

        imagesTab.setOnClickListener((view) -> {
            setTapSelected(R.id.tab_images);


        });
        profileTab.setOnClickListener((view) -> {
            setTapSelected(R.id.tab_profile);

        });

        albumTab.setOnClickListener((view) -> {
            setTapSelected(R.id.tab_album);

        });

        filterTab.setOnClickListener(v -> {
            if (onFilterClicked != null) {
                onFilterClicked.onFilterIconClicked(filterTab,clearFilterResultBtn);
            }
        });

        clearFilterResultBtn.setOnClickListener(v->{
            if (onFilterClicked != null) {
                onFilterClicked.onFilterCleared(clearFilterResultBtn,true);
            }
        });

        backBtn.setOnClickListener(v -> {
            Utilities.hideKeyboard(this);
            onBackPressed();
        });

    }


    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    private void setTapSelected(int tabId) {
        imagesTab.setTextColor(getResources().getColor(R.color.text_input_color));
        profileTab.setTextColor(getResources().getColor(R.color.text_input_color));
        albumTab.setTextColor(getResources().getColor(R.color.text_input_color));

        imagesTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange));
        profileTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange));
        albumTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange));

        switch (tabId) {
            case R.id.tab_images:
                imagesTab.setTextColor(getResources().getColor(R.color.white));
                imagesTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange_fill));
                clearFilterResultBtn.setVisibility(View.INVISIBLE);
//                filterTab.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
//                filterTab.setText(getResources().getString(R.string.filters));
//                filterTab.setVisibility(View.VISIBLE);
                ImagesSearchFragment imagesSearchFragment = ImagesSearchFragment.getInstance();
                onFilterClicked = imagesSearchFragment;
                imagesSearchFragment.setImagesSearchView(onSearchTabSelected);
                addFragment(R.id.search_container, imagesSearchFragment, ImagesSearchFragment.class.getSimpleName(), false);

                break;

            case R.id.tab_profile:
                profileTab.setTextColor(getResources().getColor(R.color.white));
                profileTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange_fill));

//                setTapSelected(R.id.tab_profile);
                filterTab.setVisibility(View.GONE);
                ProfileSearchFragment profileSearchFragment = ProfileSearchFragment.getInstance();
                profileSearchFragment.setOnSearchProfile(onSearchTabSelected);
                addFragment(R.id.search_container, profileSearchFragment, ProfileSearchFragment.class.getSimpleName(), false);

                break;

            case R.id.tab_album:
                albumTab.setTextColor(getResources().getColor(R.color.white));
                albumTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange_fill));
                clearFilterResultBtn.setVisibility(View.INVISIBLE);
//                filterTab.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
//                filterTab.setText(getResources().getString(R.string.filters));
//                filterTab.setVisibility(View.VISIBLE);
                AlbumSearchFragment albumSearchFragment = AlbumSearchFragment.getInstance();
                onFilterClicked = albumSearchFragment;
                albumSearchFragment.setAlbumSearchView(onSearchTabSelected);
                addFragment(R.id.search_container, albumSearchFragment, AlbumSearchFragment.class.getSimpleName(), false);

                break;

        }

    }





    public interface OnFilterClicked {
        void onFilterIconClicked(CustomTextView filterIcon,CustomTextView clearFilterBtn);
        void onFilterCleared(CustomTextView clearResultBtn,boolean state);
    }


}